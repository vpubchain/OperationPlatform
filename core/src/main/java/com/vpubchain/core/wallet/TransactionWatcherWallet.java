package com.vpubchain.core.wallet;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.Value;
import com.vpubchain.core.exceptions.TransactionBroadcastException;
import com.vpubchain.core.network.AddressStatus;
import com.vpubchain.core.network.BlockHeader;
import com.vpubchain.core.network.ServerClient.HistoryTx;
import com.vpubchain.core.network.ServerClient.UnspentTx;
import com.vpubchain.core.network.interfaces.BlockchainConnection;
import com.vpubchain.core.network.interfaces.TransactionEventListener;
import com.vpubchain.core.util.BitAddressUtils;
import com.vpubchain.core.wallet.families.bitcoin.BitAddress;
import com.vpubchain.core.wallet.families.bitcoin.BitBlockchainConnection;
import com.vpubchain.core.wallet.families.bitcoin.BitTransaction;
import com.vpubchain.core.wallet.families.bitcoin.BitTransactionEventListener;
import com.vpubchain.core.wallet.families.bitcoin.BitWalletTransaction;
import com.vpubchain.core.wallet.families.bitcoin.OutPointOutput;
import com.vpubchain.core.wallet.families.bitcoin.TrimmedOutPoint;
import com.vpubchain.core.wallet.families.bitcoin.TrimmedTransaction;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.math.LongMath;

import org.bitcoinj.core.ScriptException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionConfidence.Source;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.Script;
import org.bitcoinj.utils.ListenerRegistration;
import org.bitcoinj.utils.Threading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

import static com.vpubchain.core.Preconditions.checkNotNull;
import static com.vpubchain.core.Preconditions.checkState;
import static org.bitcoinj.core.TransactionConfidence.ConfidenceType.BUILDING;
import static org.bitcoinj.core.TransactionConfidence.ConfidenceType.PENDING;
import static org.bitcoinj.core.TransactionConfidence.ConfidenceType.UNKNOWN;

/**
 * @author John L. Jegutanis
 */

//钱包的基类
abstract public class TransactionWatcherWallet extends AbstractWallet<BitTransaction, BitAddress>
        implements TransactionBag, BitTransactionEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionWatcherWallet.class);

    private final static int TX_DEPTH_SAVE_THRESHOLD = 4;

    boolean DISABLE_TX_TRIMMING = false;

    @Nullable private Sha256Hash lastBlockSeenHash;
    private int lastBlockSeenHeight = -1;
    private long lastBlockSeenTimeSecs = 0;

    @VisibleForTesting
    final Map<TrimmedOutPoint, OutPointOutput> unspentOutputs;

    // Holds the status of every address we are watching. When connecting to the server, if we get a
    // different status for a particular address this means that there are new transactions for that
    // address and we have to fetch them. The status String could be null when an address is unused.
    /**
     * 保留我们正在查看的每个地址的状态。当连接到服务器时，如果
     *
     * //特定地址的不同状态这意味着有新的事务处理
     *
     * //地址，我们必须把它们取出来。当地址未使用时，状态字符串可以为空。
     */
    @VisibleForTesting
    final Map<AbstractAddress, String> addressesStatus;

    @VisibleForTesting final transient ArrayList<AbstractAddress> addressesSubscribed;
    @VisibleForTesting final transient ArrayList<AbstractAddress> addressesPendingSubscription;
    @VisibleForTesting final transient Map<AbstractAddress, AddressStatus> statusPendingUpdates;
    @VisibleForTesting final transient Map<Sha256Hash, Integer> fetchingTransactions;
    @VisibleForTesting final transient Map<Integer, Long> blockTimes;
    @VisibleForTesting final transient Map<Integer, Set<Sha256Hash>> missingTimestamps;
    // Transactions that are waiting to be added once transactions that they depend on are added
    //在添加它们所依赖的事务后等待添加的事务
    final transient Map<Sha256Hash, Map.Entry<BitTransaction, Set<Sha256Hash>>> outOfOrderTransactions;

    // The various pools below give quick access to wallet-relevant transactions by the state they're in:
    //
    // Pending:  Transactions that didn't make it into the best chain yet.
    // Confirmed:Transactions that appeared in the best chain.
    /**
     * //下面的各种池可以按其所在州快速访问与钱包相关的交易：
     * //挂起：尚未进入最佳链的事务。
     * //已确认：出现在最佳链中的事务。
     */

    @VisibleForTesting final Map<Sha256Hash, BitTransaction> pending;
    @VisibleForTesting final Map<Sha256Hash, BitTransaction> confirmed;

    // All transactions together. 所有交易
    final Map<Sha256Hash, BitTransaction> rawTransactions;
    // 一个接口
    private BitBlockchainConnection blockchainConnection;
    //监听者
    private List<ListenerRegistration<WalletAccountEventListener>> listeners;

    // Wallet that this account belongs 这个账户所属的钱包
    @Nullable private transient Wallet wallet = null;

    @VisibleForTesting transient Value lastBalance;
    //连接状态
    transient WalletConnectivityStatus lastConnectivity = WalletConnectivityStatus.DISCONNECTED;

    //保存wallet
    private Runnable saveLaterRunnable = new Runnable() {
        @Override
        public void run() {
            if (wallet != null) wallet.saveLater();
        }
    };

    private Runnable saveNowRunnable = new Runnable() {
        @Override
        public void run() {
            if (wallet != null) wallet.saveNow();
        }
    };

    // Constructor
    public TransactionWatcherWallet(CoinType coinType, String id) {
        super(coinType, id);
        unspentOutputs = new HashMap<>();
        addressesStatus = new HashMap<>();
        addressesSubscribed = new ArrayList<>();
        addressesPendingSubscription = new ArrayList<>();
        statusPendingUpdates = new HashMap<>();
        fetchingTransactions = new HashMap<>();
        blockTimes = new HashMap<>();
        missingTimestamps = new HashMap<>();
        confirmed = new HashMap<>();
        pending = new HashMap<>();
        rawTransactions = new HashMap<>();
        outOfOrderTransactions = new HashMap<>();
        listeners = new CopyOnWriteArrayList<>();
        lastBalance = type.value(0);
    }


    @Override
    public CoinType getCoinType() {
        return type;
    }

    @Override
    public boolean isNew() {
        return rawTransactions.size() == 0;
    }

    @Override
    public void setWallet(@Nullable Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    @Nullable
    public Wallet getWallet() {
        return wallet;
    }

    // Util
    @Override
    public void walletSaveLater() {
        // Save in another thread to avoid cyclic locking of Wallet and WalletPocket
        Threading.USER_THREAD.execute(saveLaterRunnable);
    }

    @Override
    public void walletSaveNow() {
        // Save in another thread to avoid cyclic locking of Wallet and WalletPocket
        Threading.USER_THREAD.execute(saveNowRunnable);
    }

    /**
     * Returns a set of all WalletTransactions in the wallet.
     * 返回钱包中所有WalletTransactions的集合。包括已经确认的和未确认的
     */
    public Iterable<BitWalletTransaction> getWalletTransactions() {
        lock.lock();
        try {
            Set<BitWalletTransaction> all = new HashSet<>();
            addWalletTransactionsToSet(all, WalletTransaction.Pool.CONFIRMED, confirmed.values());
            addWalletTransactionsToSet(all, WalletTransaction.Pool.PENDING, pending.values());
            return all;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 添加交易历史
     */
    private void simpleAddTransaction(WalletTransaction.Pool pool, BitTransaction tx) {
        lock.lock();
        try {
            if (rawTransactions.containsKey(tx.getHash())) return;
            rawTransactions.put(tx.getHash(), tx);
            switch (pool) {
                    //已经确认的交易
                case CONFIRMED:
                    checkState(confirmed.put(tx.getHash(), tx) == null);
                    break;
                    //未确认的
                case PENDING:
                    checkState(pending.put(tx.getHash(), tx) == null);
                    break;
                default:
                    throw new RuntimeException("Unknown wallet transaction type " + pool);
            }
        } finally {
            lock.unlock();
        }
    }

    //添加一个交易记录
    private static void addWalletTransactionsToSet(Set<BitWalletTransaction> txs,
                                                   WalletTransaction.Pool poolType, Collection<BitTransaction> pool) {
        for (BitTransaction tx : pool) {
            txs.add(new BitWalletTransaction(poolType, tx));
        }
    }

    /**
     * Adds a transaction that has been associated with a particular wallet pool. This is intended for usage by
     * deserialization code, such as the {@link WalletPocketProtobufSerializer} class. It isn't normally useful for
     * applications. It does not trigger auto saving.
     */
//    public void addWalletTransaction(BitWalletTransaction wtx) {
//        lock.lock();
//        try {
//            addWalletTransaction(wtx.getPool(), wtx.getTransaction(), true);
//        } finally {
//            lock.unlock();
//        }
//    }

    //修剪transaction
    boolean trimTransactionIfNeeded(Sha256Hash hash) {
        lock.lock();
        try {
            return trimTransaction(hash);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove irrelevant inputs and outputs. Returns true if transaction trimmed.
     * 删除不相关的输入和输出。如果事务被剪裁，则返回true。
     * 此处会调用simpleAddTransaction
     * 待看
     */
    private boolean trimTransaction(Sha256Hash hash) {
        if (DISABLE_TX_TRIMMING) return false;

        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");

        BitTransaction transaction = rawTransactions.get(hash);

        if (transaction == null || transaction.isTrimmed()) return false;

        for (TransactionInput input : transaction.getInputs()) {
            // If this transaction depends on a previous transaction that is yet fetched
            //如果此事务依赖于尚未提取的上一个事务
            if (isInputMine(input) && !rawTransactions.containsKey(input.getOutpoint().getHash())) {
                log.warn("Tried to trim transaction with unmet dependencies. Tx {} depends on {}.",
                        hash, input.getOutpoint().getHash());
                return false;
            }
        }

        final Value valueSent = transaction.getValueSent(this);
        final Value valueReceived = transaction.getValueReceived(this);
        boolean isReceiving = valueReceived.compareTo(valueSent) > 0;

        // Remove fee when receiving
        final Value fee = isReceiving ? null : transaction.getRawTxFee(this);

        WalletTransaction.Pool txPool;
        if (confirmed.containsKey(hash)) {
            txPool = WalletTransaction.Pool.CONFIRMED;
        } else if (pending.containsKey(hash)) {
            txPool = WalletTransaction.Pool.PENDING;
        } else {
            throw new RuntimeException("Transaction is not found in any pool");
        }

        // Do not trim pending sending transactions as we need their inputs to correctly calculate
        // the UTXO set
        if (txPool == WalletTransaction.Pool.PENDING && !isReceiving) {
            return false;
        }

        Transaction txFull = transaction.getRawTransaction();
        List<TransactionOutput> outputs = txFull.getOutputs();

        TrimmedTransaction tx = new TrimmedTransaction(type, hash, outputs.size());

        // Copy confidence
        TransactionConfidence fullTxConf = txFull.getConfidence();
        TransactionConfidence txConf = tx.getConfidence();
        txConf.setSource(fullTxConf.getSource());
        txConf.setConfidenceType(fullTxConf.getConfidenceType());
        if (txConf.getConfidenceType() == BUILDING) {
            txConf.setAppearedAtChainHeight(fullTxConf.getAppearedAtChainHeight());
            txConf.setDepthInBlocks(fullTxConf.getDepthInBlocks());
        }
        // Copy other fields
        tx.setTime(txFull.getTime());
        tx.setTokenId(txFull.getTokenId());
        tx.setExtraBytes(txFull.getExtraBytes());
        tx.setUpdateTime(txFull.getUpdateTime());
        tx.setLockTime(txFull.getLockTime());

        if (txFull.getAppearsInHashes() != null) {
            for (Map.Entry<Sha256Hash, Integer> appears : txFull.getAppearsInHashes().entrySet()) {
                tx.addBlockAppearance(appears.getKey(), appears.getValue());
            }
        }

        tx.setPurpose(txFull.getPurpose());

        // Remove unrelated outputs when receiving coins
        if (isReceiving) {
            int outputIndex = 0;
            for (TransactionOutput output : outputs) {
                if (output.isMineOrWatched(this)) {
                    tx.addOutput(outputIndex, output);
                }
                outputIndex++;
            }
        } else {
            // When sending keep all outputs
            tx.addAllOutputs(outputs);
        }

        // Replace with trimmed transaction
        removeTransaction(hash);

        simpleAddTransaction(txPool,
                BitTransaction.fromTrimmed(hash, tx, valueSent, valueReceived, fee));
        return true;
    }

    //移除三个集合中的transaction
    private void removeTransaction(Sha256Hash hash) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        rawTransactions.remove(hash);
        confirmed.remove(hash);
        pending.remove(hash);
    }

    /**
     * Adds the given transaction to the given pools and registers a confidence change listener on it.
     * 此处会调用simpleAddTransaction
     */
    private void addWalletTransaction(@Nullable WalletTransaction.Pool pool, BitTransaction tx,
                                      boolean save) {
        lock.lock();
        try {
            if (pool == null) {
                switch (tx.getConfidenceType()) {
                    case BUILDING:
                        pool = WalletTransaction.Pool.CONFIRMED;
                        break;
                    case PENDING:
                        pool = WalletTransaction.Pool.PENDING;
                        break;
                    case DEAD:
                    case UNKNOWN:
                    default:
                        throw new RuntimeException("Unsupported confidence type: " +
                                tx.getConfidenceType().name());
                }
            }

            guessSource(tx);
            simpleAddTransaction(pool, tx);
            trimTransaction(tx.getHash());
            if (tx.getSource() == Source.SELF) queueOnNewBalance();
        } finally {
            lock.unlock();
        }


        // This is safe even if the listener has been added before, as TransactionConfidence ignores duplicate
        // registration requests. That makes the code in the wallet simpler.
        // TODO add txConfidenceListener
//        tx.getConfidence().addEventListener(txConfidenceListener, Threading.SAME_THREAD);
        if (save) walletSaveLater();
    }
    //获取交易来源   就是流入还是流出
    private void guessSource(BitTransaction tx) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        if (tx.getSource() == Source.UNKNOWN) {
            boolean isReceiving = tx.getValue(this).isPositive();

            if (isReceiving) {
                tx.setSource(Source.NETWORK);
            } else {
                tx.setSource(Source.SELF);
            }
        }
    }

    /**
     * Returns a transaction object given its hash, if it exists in this wallet, or null otherwise.
     */
    @Nullable
    public Transaction getRawTransaction(Sha256Hash hash) {
        lock.lock();
        try {
            BitTransaction tx = rawTransactions.get(hash);
            if (tx != null) {
                return tx.getRawTransaction();
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns transactions that match the hashes, some transactions could be missing.
     * 返回与哈希匹配的事务，某些事务可能丢失。
     */
    public HashMap<Sha256Hash, BitTransaction> getTransactions(Set<Sha256Hash> hashes) {
        lock.lock();
        try {
            HashMap<Sha256Hash, BitTransaction> txs = new HashMap<>();
            for (Sha256Hash hash : hashes) {
                if (rawTransactions.containsKey(hash)) {
                    txs.put(hash, rawTransactions.get(hash));
                }
            }
            return txs;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Deletes transactions which appeared above the given block height from the wallet, but does not touch the keys.
     * This is useful if you have some keys and wish to replay the block chain into the wallet in order to pick them up.
     * Triggers auto saving.
     *
     *从钱包中删除显示在给定块高度以上的事务，但不触摸密钥。
     *如果您有一些钥匙，并希望将区块链重放到钱包中以便取用，这很有用。
     *触发自动保存。
     */
    @Override
    public void refresh() {
        lock.lock();
        try {
            log.info("Refreshing wallet pocket {}", type);
            lastBlockSeenHash = null;
            lastBlockSeenHeight = -1;
            lastBlockSeenTimeSecs = 0;
            blockTimes.clear();
            missingTimestamps.clear();
            unspentOutputs.clear();
            confirmed.clear();
            pending.clear();
            rawTransactions.clear();
            addressesStatus.clear();
            clearTransientState();
        } finally {
            lock.unlock();
        }
    }

    /** Returns the hash of the last seen best-chain block, or null if the wallet is too old to store this data.
     * 返回上次看到的最佳链块的哈希值，如果钱包太旧，无法存储此数据，则返回空值。
     * */
    @Nullable
    public Sha256Hash getLastBlockSeenHash() {
        lock.lock();
        try {
            return lastBlockSeenHash;
        } finally {
            lock.unlock();
        }
    }

    public void setLastBlockSeenHash(@Nullable Sha256Hash lastBlockSeenHash) {
        lock.lock();
        try {
            this.lastBlockSeenHash = lastBlockSeenHash;
        } finally {
            lock.unlock();
        }
        walletSaveLater();
    }

    public void setLastBlockSeenHeight(int lastBlockSeenHeight) {
        lock.lock();
        try {
            this.lastBlockSeenHeight = lastBlockSeenHeight;
        } finally {
            lock.unlock();
        }
        walletSaveLater();
    }

    public void setLastBlockSeenTimeSecs(long timeSecs) {
        lock.lock();
        try {
            lastBlockSeenTimeSecs = timeSecs;
        } finally {
            lock.unlock();
        }
        walletSaveLater();
    }

    /**
     * Returns the UNIX time in seconds since the epoch extracted from the last best seen block header. This timestamp
     * is <b>not</b> the local time at which the block was first observed by this application but rather what the block
     * (i.e. miner) self declares. It is allowed to have some significant drift from the real time at which the block
     * was found, although most miners do use accurate times. If this wallet is old and does not have a recorded
     * time then this method returns zero.
     * 返回从上一个最清晰的块头提取epoch以来的Unix时间（秒）。这个时间戳
     *
     * *是该应用程序首次观察到块的本地时间，而不是块的时间。
     *
     * *（即Miner）自我声明。它允许有一些明显的漂移从实时块
     *
     * *被发现，尽管大多数矿工使用的是准确的时间。如果这个钱包是旧的，而且没有记录
     *
     * *然后此方法返回零。
     */
    public long getLastBlockSeenTimeSecs() {
        lock.lock();
        try {
            return lastBlockSeenTimeSecs;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a {@link java.util.Date} representing the time extracted from the last best seen block header. This timestamp
     * is <b>not</b> the local time at which the block was first observed by this application but rather what the block
     * (i.e. miner) self declares. It is allowed to have some significant drift from the real time at which the block
     * was found, although most miners do use accurate times. If this wallet is old and does not have a recorded
     * time then this method returns null.
     */
    @Nullable
    public Date getLastBlockSeenTime() {
        final long secs = getLastBlockSeenTimeSecs();
        if (secs == 0)
            return null;
        else
            return new Date(secs * 1000);
    }

    /**
     * Returns the height of the last seen best-chain block. Can be 0 if a wallet is brand new or -1 if the wallet
     * is old and doesn't have that data.
     */
    public int getLastBlockSeenHeight() {
        lock.lock();
        try {
            return lastBlockSeenHeight;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Value getBalance() {
        return getBalance(false);
    }

    //获取钱包余额
    public Value getBalance(boolean includeReceiving) {
        lock.lock();
        try {
            long value = 0;
            for (OutPointOutput utxo : getUnspentOutputs(includeReceiving).values()) {
                value = LongMath.checkedAdd(value, utxo.getValueLong());
            }
            return type.value(value);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Sets that the specified status is currently updating i.e. getting transactions.
     *
     * Returns true if registered successfully or false if status already updating
     * 设置指定的状态当前正在更新，即获取事务。
     * *如果注册成功，则返回true；如果状态已更新，则返回false
     */
    //更新地址状态
    @VisibleForTesting boolean registerStatusForUpdate(AddressStatus status) {
        checkNotNull(status.getStatus());

        lock.lock();
        try {
            // If current address is updating
            if (statusPendingUpdates.containsKey(status.getAddress())) {
                AddressStatus updatingAddressStatus = statusPendingUpdates.get(status.getAddress());
                String updatingStatus = updatingAddressStatus.getStatus();

                // If the same status is updating, don't update again
                if (updatingStatus != null && updatingStatus.equals(status.getStatus())) {
                    return false;
                } else { // Status is newer, so replace the updating status
                    statusPendingUpdates.put(status.getAddress(), status);
                    return true;
                }
            } else { // This status is new
                statusPendingUpdates.put(status.getAddress(), status);
                return true;
            }
        }
        finally {
            lock.unlock();
        }
    }

    //提交地址状态
    void commitAddressStatus(AddressStatus newStatus) {
        if (!newStatus.canCommitStatus()) {
            log.warn("Tried to commit an address status with a non applied state: {}:{}",
                    newStatus.getAddress(), newStatus.getStatus());
            return;
        }

        lock.lock();
        try {
            AddressStatus updatingStatus = statusPendingUpdates.get(newStatus.getAddress());
            if (updatingStatus != null && updatingStatus.equals(newStatus)) {
                statusPendingUpdates.remove(newStatus.getAddress());
            }
            addressesStatus.put(newStatus.getAddress(), newStatus.getStatus());
            queueOnConnectivity();
        }
        finally {
            lock.unlock();
        }
        // Skip saving null statuses
        if (newStatus.getStatus() != null) {
            walletSaveLater();
        }
    }

    //判断当前地址状态是否改变了
    private boolean isAddressStatusChanged(AddressStatus addressStatus) {
        lock.lock();
        try {
            AbstractAddress address = addressStatus.getAddress();
            String newStatus = addressStatus.getStatus();
            if (addressesStatus.containsKey(address)) {
                String previousStatus = addressesStatus.get(address);
                if (previousStatus == null) {
                    return newStatus != null; // Status changed if newStatus is not null
                } else {
                    return !previousStatus.equals(newStatus);
                }
            }
            else {
                // Unused address, just mark it that we watch it
                if (newStatus == null) {
                    commitAddressStatus(addressStatus);
                    return false;
                } else {
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Nullable
    public AddressStatus getAddressStatus(AbstractAddress address) {
        lock.lock();
        try {
            if (addressesStatus.containsKey(address)) {
                return new AddressStatus(address, addressesStatus.get(address));
            }
            else {
                return null;
            }
        }
        finally {
            lock.unlock();
        }
    }

    public List<AddressStatus> getAllAddressStatus() {
        lock.lock();
        try {
            ArrayList<AddressStatus> statuses = new ArrayList<>(addressesStatus.size());
            for (Map.Entry<AbstractAddress, String> status : addressesStatus.entrySet()) {
                statuses.add(new AddressStatus(status.getKey(), status.getValue()));
            }
            return statuses;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Returns all the addresses that are not currently watched
     * 返回当前未被监视的所有地址
     */
    @VisibleForTesting List<AbstractAddress> getAddressesToWatch() {
        lock.lock();
        try {
            ImmutableList.Builder<AbstractAddress> addressesToWatch = ImmutableList.builder();
            //getActiveAddresses 调用walletPocketHD的getActiveAddresses
            for (AbstractAddress address : getActiveAddresses()) {
                // If address not already subscribed or pending subscription如果地址尚未订阅或待定订阅
                if (!addressesSubscribed.contains(address) && !addressesPendingSubscription.contains(address)) {
                    addressesToWatch.add(address);
                }
            }
            return addressesToWatch.build();
        }
        finally {
            lock.unlock();
        }
    }

    //如果地址还是准备订阅集合就加入已订阅集合(pending 集合是addToWatch 方法产生的地址)
    private void confirmAddressSubscription(AbstractAddress address) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        if (addressesPendingSubscription.contains(address)) {
            log.debug("Subscribed to {}", address);
            addressesPendingSubscription.remove(address);
            addressesSubscribed.add(address);
        }
    }

    //如果有新的区块生成调用
    @Override
    public void onNewBlock(BlockHeader header) {
        log.info("Got a {} block: {}", type.getName(), header.getBlockHeight());
        boolean shouldSave = false;
        lock.lock();
        try {
            lastBlockSeenTimeSecs = header.getTimestamp();
            lastBlockSeenHeight = header.getBlockHeight();
            //更新交易时间
            updateTransactionTimes(header);
            //
            for (BitTransaction tx : rawTransactions.values()) {
                // Save wallet when we have new TXs   如果该transaction 深度<4  就保存钱包
                if (tx.getDepthInBlocks() < TX_DEPTH_SAVE_THRESHOLD) shouldSave = true;
                // 更新Transaction区块深度   和  unspentOutput.setDepthInBlocks(newDepth)
                maybeUpdateBlockDepth(tx, true);
            }
            //通知多个监听者有新区块生成
            queueOnNewBlock();
        } finally {
            lock.unlock();
        }
        //保存钱包
        if (shouldSave) walletSaveLater();
    }

    //更新Transaction区块深度   和  unspentOutput.setDepthInBlocks(newDepth)
    private void maybeUpdateBlockDepth(BitTransaction tx, boolean updateUtxoSet) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        if (tx.getConfidenceType() != BUILDING) return;
        int newDepth = lastBlockSeenHeight - tx.getAppearedAtChainHeight() + 1;
        if (newDepth > 1) {
            tx.setDepthInBlocks(newDepth);

            // Update unspent outputs
            if (updateUtxoSet) {
                for (TransactionOutput output : tx.getOutputs(false)) {
                    OutPointOutput unspentOutput = unspentOutputs.get(TrimmedOutPoint.get(output));
                    if (unspentOutput != null) {
                        unspentOutput.setDepthInBlocks(newDepth);
                    }
                }
            }
        }
    }
//blockchain.block.get_header   回调
    @Override
    public void onBlockUpdate(BlockHeader header) {
        log.info("Got a {} block update: {}", type.getName(), header.getBlockHeight());
        lock.lock();
        try {
            updateTransactionTimes(header);
            queueOnNewBlock();
        }
        finally {
            lock.unlock();
        }
    }
    //更新交易时间
    private void updateTransactionTimes(BlockHeader header) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        Integer height = header.getBlockHeight();
        Long timestamp = header.getTimestamp();
        boolean mustSave = false;
        //插入该区块头的区块高度和时间
        blockTimes.put(height, timestamp);
        //如果该集合有这个高度
        if (missingTimestamps.containsKey(height)) {
            for (Sha256Hash hash : missingTimestamps.get(height)) {
                if (rawTransactions.containsKey(hash)) {
                    //得到该交易  修改时间
                    rawTransactions.get(hash).setTimestamp(timestamp);
                    mustSave = true;
                }
            }
        }
        //然后删除该集合的对应区块高度
        missingTimestamps.remove(height);
        if (mustSave) {
            //保存钱包
            walletSaveLater();
        }
    }

    //当blockchain.address.subscribe 获取到地址状态后调用(ServerClient调用地址后会调用该方法)
    @Override
    public void onAddressStatusUpdate(AddressStatus status) {
        log.debug("Got a status {}", status);
        lock.lock();
        try {
            //如果地址还是准备订阅集合就加入已订阅集合(pending 集合是addToWatch 方法产生的地址)
            confirmAddressSubscription(status.getAddress());
            //如果该地址已经使用过
            if (status.getStatus() != null) {
                //标记地址为已经使用  keys.markPubHashAsUsed(address.getHash160());
                markAddressAsUsed(status.getAddress());
                // 再次订阅地址？
                subscribeToAddressesIfNeeded();

                //如果该地址已经使用过   1判断当前地址状态是否改变了   2获取getUnspentTx  getHistoryTx
                if (isAddressStatusChanged(status)) {
                    // statusPendingUpdates这个集合加入这个status
                    if (registerStatusForUpdate(status)) {
                        log.info("Must get transactions for address {}, status {}",
                                status.getAddress(), status.getStatus());

                        if (blockchainConnection != null) {
                            //{"jsonrpc": "2.0", "result": [{"tx_hash": "ba428e29301f873f3b6fa58ace48d7f0318542c437db06337d189909b39c6b97", "tx_pos": 1, "height": 47235, "value": 1000000000}], "id": 42}
                            blockchainConnection.getUnspentTx(status, this);
                            //{"jsonrpc": "2.0", "result": [{"tx_hash": "ba428e29301f873f3b6fa58ace48d7f0318542c437db06337d189909b39c6b97", "height": 47235}], "id": 43}
                            blockchainConnection.getHistoryTx(status, this);
                        }
                    } else {
                        log.info("Status {} already updating", status.getStatus());
                    }
                }
            }
            //如果该地址还没使用
            else {
                // Address not used, just update the status
                commitAddressStatus(status);
                tryToApplyState();
            }
        }
        finally {
            lock.unlock();
        }
    }

    //blockchain.address.listunspent后调用
    @Override
    public void onUnspentTransactionUpdate(AddressStatus status, List<UnspentTx> unspentTxes) {
        lock.lock();
        try {
            AddressStatus updatingStatus = statusPendingUpdates.get(status.getAddress());
            // Check if this updating status is valid  如果该地址状态和当初存储的一样
            log.info(status.getAddress()+"              "+unspentTxes.size());
            if (updatingStatus != null && updatingStatus.equals(status)) {
                log.info(status.getAddress()+"              "+unspentTxes.get(0).getTxHash());
                //将要提取的交易排队
                updatingStatus.queueUnspentTransactions(unspentTxes);
                //获取交易   此处可能是吧utxo获取
                fetchTransactionsIfNeeded(unspentTxes);
                //待看
                tryToApplyState(updatingStatus);
            }
            else {
                log.info("Ignoring unspent tx call because no entry found or newer entry.");
            }
        }
        finally {
            lock.unlock();
        }
    }

    //交易历史
    @Override
    public void onTransactionHistory(AddressStatus status, List<HistoryTx> historyTxes) {
        lock.lock();
        try {
            AddressStatus updatingStatus = statusPendingUpdates.get(status.getAddress());
            // Check if this updating status is valid
            if (updatingStatus != null && updatingStatus.equals(status)) {
                updatingStatus.queueHistoryTransactions(historyTxes);
                fetchTransactionsIfNeeded(historyTxes);
                //不调用功能也正常。。。但是进度条一直在跳
                tryToApplyState();
            } else {
                log.info("Ignoring history tx call because no entry found or newer entry.");
            }
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Try to apply all address states
     */
    private void tryToApplyState() {
        lock.lock();
        try {
            for (AddressStatus status : Lists.newArrayList(statusPendingUpdates.values())) {
                tryToApplyState(status);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Try to apply the status state
     */
    private void tryToApplyState(AddressStatus status) {
        lock.lock();
        try {
            if (statusPendingUpdates.containsKey(status.getAddress())) {
                if (status.isUnspentTxQueued() && !status.isUnspentTxStateApplied()) {
                    Set<Sha256Hash> txHashes = status.getUnspentTxHashes();
                    HashMap<Sha256Hash, BitTransaction> txs = getTransactions(txHashes);
                    // We have all the transactions, apply state
                    if (txs.size() == txHashes.size()) {
                        applyUnspentState(status, txs);
                    }
                }
                if (status.isHistoryTxQueued() && !status.isHistoryTxStateApplied()) {
                    Set<Sha256Hash> txHashes = status.getHistoryTxHashes();
                    HashMap<Sha256Hash, BitTransaction> txs = getTransactions(txHashes);
                    // We have all the transactions, apply state
                    if (txs.size() == txHashes.size()) {
                        applyHistoryState(status, txs);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void applyUnspentState(AddressStatus status, HashMap<Sha256Hash, BitTransaction> txs) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        checkState(!status.isUnspentTxStateApplied(), "Unspent tx state already applied");

        // Get all the outputs that relate this this address, we will remove some of them if they
        // are not present in this status update
        /**
         * //获取与此地址相关的所有输出，如果它们
         * //在此状态更新中不存在
         */
        AbstractAddress address = status.getAddress();
        Set<TrimmedOutPoint> notPresentInStatus = new HashSet<>();
        for (Map.Entry<TrimmedOutPoint, OutPointOutput> utxo : unspentOutputs.entrySet()) {
            if (BitAddressUtils.producesAddress(utxo.getValue().getScriptPubKey(), address)) {
                notPresentInStatus.add(utxo.getKey());
            }
        }

        // Update all the unspent outputs in this status
        for (UnspentTx unspentTx : status.getUnspentTxs()) {
            TrimmedOutPoint outPoint =
                    new TrimmedOutPoint(type, unspentTx.getTxPos(), unspentTx.getTxHash());

            // Get the related transaction
            BitTransaction tx = checkNotNull(txs.get(outPoint.getHash()));

            // Update the transaction and UTXO set confirmations
            checkTxConfirmation(unspentTx, tx);

            // If not present add it now
            if (!unspentOutputs.containsKey(outPoint)) {
                OutPointOutput utxo = new OutPointOutput(tx, outPoint.getIndex());
                if (tx.getConfidenceType() == BUILDING) {
                    utxo.setAppearedAtChainHeight(tx.getAppearedAtChainHeight());
                    utxo.setDepthInBlocks(tx.getDepthInBlocks());
                }
                unspentOutputs.put(utxo.getOutPoint(), utxo);
            }

            // Mark this utxo to not be removed
            notPresentInStatus.remove(outPoint);
        }

        // Remove all unspent outputs that were not present in this address status
        for (TrimmedOutPoint toRemove : notPresentInStatus) {
            unspentOutputs.remove(toRemove);
        }

        status.setUnspentTxStateApplied(true);
        if (status.canCommitStatus()) commitAddressStatus(status);
        queueOnNewBalance();
    }

    private void applyHistoryState(AddressStatus status, HashMap<Sha256Hash, BitTransaction> txs) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        checkState(!status.isHistoryTxStateApplied(), "History tx state already applied");

        // Update confirmation status if necessary
        for (HistoryTx historyTx : status.getHistoryTxs()) {
            BitTransaction tx = checkNotNull(txs.get(historyTx.getTxHash()));
            checkTxConfirmation(historyTx, tx);
        }

        status.setHistoryTxStateApplied(true);
        if (status.canCommitStatus()) commitAddressStatus(status);
    }

    private void checkTxConfirmation(HistoryTx historyTx, BitTransaction tx) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        int height = historyTx.getHeight();
        TransactionConfidence.ConfidenceType confidence = tx.getConfidenceType();
        if (height > 0) {
            switch (confidence) {
                case BUILDING:
                    // If the height is the same, don't do anything
                    if (tx.getAppearedAtChainHeight() == historyTx.getHeight()) {
                        break;
                    }
                case PENDING:
                    setAppearedAtChainHeight(tx, height, true);
                    maybeUpdateBlockDepth(tx, true);
                    maybeMovePool(tx);
                    break;
                case DEAD:
                case UNKNOWN:
                default:
                    throw new RuntimeException("Unsupported confidence type: " +
                            tx.getConfidenceType().name());
            }
        }
    }

    private void setAppearedAtChainHeight(BitTransaction tx, int height, boolean updateUtxoSet) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        tx.setAppearedAtChainHeight(height);
        fetchTimestamp(tx, height);
        // Update unspent outputs
        if (updateUtxoSet) {
            for (TransactionOutput output : tx.getOutputs(false)) {
                OutPointOutput unspentOutput = unspentOutputs.get(TrimmedOutPoint.get(output));
                if (unspentOutput != null) {
                    unspentOutput.setAppearedAtChainHeight(height);
                }
            }
        }
    }

    //
    private void fetchTimestamp(BitTransaction tx, Integer height) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        if (blockTimes.containsKey(height)) {
            tx.setTimestamp(blockTimes.get(height));
        } else {
            if (log.isDebugEnabled()) log.debug("Must get timestamp for {} block on height {}", type.getName(), height);
            if (!missingTimestamps.containsKey(height)) {
                missingTimestamps.put(height, new HashSet<Sha256Hash>());
                missingTimestamps.get(height).add(tx.getHash());
                if (blockchainConnection != null) {
                    blockchainConnection.getBlock(height, this);
                }
            } else {
                missingTimestamps.get(height).add(tx.getHash());
            }
        }
    }

    /**
     * If the transactions outputs are all marked as spent, and it's in the unspent map, move it.
     * If the owned transactions outputs are not all marked as spent, and it's in the spent map, move it.
     * 如果事务输出都标记为已用，并且位于未用映射中，则移动它。
     * *如果所拥有的事务输出未全部标记为已用，并且位于已用映射中，则移动它。
     */
    private void maybeMovePool(BitTransaction tx) {
        lock.lock();
        try {
            if (tx.getConfidenceType() == BUILDING) {
                // Transaction is confirmed, move it
                if (pending.remove(tx.getHash()) != null) {
                    confirmed.put(tx.getHash(), tx);
                    trimTransaction(tx.getHash());
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void fetchTransactionsIfNeeded(List<? extends HistoryTx> historyTxes) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");

        for (HistoryTx historyTx : historyTxes) {
            fetchTransactionIfNeeded(historyTx.getTxHash(), historyTx.getHeight());
        }
    }

    private void fetchTransactionIfNeeded(Sha256Hash txHash, @Nullable Integer height) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");

        // Check if need to fetch the transaction检查是否需要获取交易
        //如果该交易本地已经有了  就执行 else if的
        if (!isTransactionAvailableOrQueued(txHash)) {
            fetchingTransactions.put(txHash, height);
            log.info("Going to fetch transaction with hash {}", txHash);
            if (blockchainConnection != null) {
                //根据txid 获取交易详情
                blockchainConnection.getTransaction(txHash, this);
            }
        } else if (fetchingTransactions.containsKey(txHash)) {
            // Check if we should update the confirmation height
            Integer txHeight = fetchingTransactions.get(txHash);
            if (height != null && txHeight != null && height < txHeight) {
                fetchingTransactions.put(txHash, height);
            }
        }
    }

    private boolean isTransactionAvailableOrQueued(Sha256Hash txHash) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        return rawTransactions.containsKey(txHash) || isTransactionQueued(txHash);
    }

    private boolean isTransactionQueued(Sha256Hash txHash) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        return outOfOrderTransactions.containsKey(txHash) ||
                fetchingTransactions.containsKey(txHash);
    }


//    @VisibleForTesting
//    void addNewTransactionIfNeeded(Transaction tx) {
//        addNewTransactionIfNeeded(new BitTransaction(tx));
//    }

    @VisibleForTesting
    //添加新的transaction
    void addNewTransactionIfNeeded(BitTransaction tx) {
        lock.lock();
        try {
            Sha256Hash hash = tx.getHash();

            // If was fetching this tx, remove it如果正在获取此Tx，请删除它  因为已经获取完毕
            Integer appearedInHeight = fetchingTransactions.remove(hash);

            // This tx not in wallet, add it
            if (!rawTransactions.containsKey(hash) && !outOfOrderTransactions.containsKey(hash)) {
                if (tx.getConfidenceType() == UNKNOWN) {
                    // Set if transaction is confirmed 交易确认时设置
                    if (appearedInHeight != null && appearedInHeight > 0) {
                        setAppearedAtChainHeight(tx, appearedInHeight, false);
                        maybeUpdateBlockDepth(tx, false);
                    } else {
                        tx.setConfidenceType(PENDING);
                    }
                }

                // Check if some input transactions are available检查某些输入事务是否可用
                Set<Sha256Hash> missingTransactions = new HashSet<>();
                for (TransactionInput input : tx.getInputs()) {
                    Sha256Hash dependencyTx = input.getOutpoint().getHash();
                    // If this transaction depends on a previous transaction that is yet fetched如果此交易依赖于尚未提取的上一个交易
                    if (isInputMine(input) && !rawTransactions.containsKey(dependencyTx)) {
                        missingTransactions.add(dependencyTx);
                    }
                }
                if (!missingTransactions.isEmpty()) {
                    outOfOrderTransactions.put(hash,
                            new AbstractMap.SimpleImmutableEntry<>(tx, missingTransactions));
                    // Fetch the missing Transactions获取缺少的交易
                    for (Sha256Hash missingTx : missingTransactions) {
                        fetchTransactionIfNeeded(missingTx, appearedInHeight);
                    }
                    return;
                }

                addWalletTransaction(null, tx, true);

                // Check if out of order transactions can be added检查是否可以添加无序交易记录
                for (Map.Entry<BitTransaction, Set<Sha256Hash>> outOfOrderTx :
                        Lists.newLinkedList(outOfOrderTransactions.values())) {
                    Set<Sha256Hash> missingTxs = outOfOrderTx.getValue();
                    // Check if this tx satisfies the dependency检查此Tx是否满足相关性
                    if (missingTxs.contains(hash)) {
                        missingTxs.remove(hash);
                        // Try to add the out of order transaction尝试添加无序事务
                        if (missingTxs.isEmpty()) {
                            outOfOrderTransactions.remove(outOfOrderTx.getKey().getHash());
                            addNewTransactionIfNeeded(outOfOrderTx.getKey());
                        }
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean isInputMine(TransactionInput input) {
        lock.lock();
        try {
            try {
                Script script = input.getScriptSig();
                // TODO check multi sig scripts
                return isPubKeyMine(script.getPubKey());
            } catch (ScriptException e) {
                // We didn't understand this input ScriptSig: ignore it.
                log.debug("Could not parse tx input script: {}", e.toString());
                return false;
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void onTransactionUpdate(BitTransaction tx) {
        if (log.isInfoEnabled()) log.info("Got a new transaction {}", tx.getHash());
        lock.lock();
        try {
            //
            addNewTransactionIfNeeded(tx);
            tryToApplyState();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onTransactionBroadcast(BitTransaction tx) {
        lock.lock();
        try {
            log.info("Transaction sent {}", tx);
            addNewTransactionIfNeeded(tx);
        } finally {
            lock.unlock();
        }
        queueOnTransactionBroadcastSuccess(tx);
    }

    @Override
    public void onTransactionBroadcastError(BitTransaction tx) {
        queueOnTransactionBroadcastFailure(tx);
    }

    //这个方法什么时候调用
    //ServerClient 会调用这个方法  每分钟调用一次或者进入app 调用一次

    @Override
    public void onConnection(BlockchainConnection blockchainConnection) {
        log.info("onConnectiononConnectiononConnectiononConnectiononConnectiononConnection");
        lock.lock();
        try {
            this.blockchainConnection = (BitBlockchainConnection) blockchainConnection;
            log.error("onConnectiononConnectiononConnectiononConnectiononConnectiononConnection2");
            //清理数据
            clearTransientState();
            //订阅头
            subscribeToBlockchain();
            //订阅地址
            subscribeToAddressesIfNeeded();
            queueOnConnectivity();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onDisconnect() {
        lock.lock();
        try {
            blockchainConnection = null;
            clearTransientState();
            queueOnConnectivity();
        } finally {
            lock.unlock();
        }
    }

    private void subscribeToBlockchain() {
        lock.lock();
        try {
            //有一个ServerClient  调用其subscribeToBlockchain()
            if (blockchainConnection != null) {
                blockchainConnection.subscribeToBlockchain(this);
                //根据高度获取区块
                for (Integer missingTimestampOnHeight : missingTimestamps.keySet()) {
                    blockchainConnection.getBlock(missingTimestampOnHeight, this);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    void subscribeToAddressesIfNeeded() {
        lock.lock();
        try {
            if (blockchainConnection != null) {
                //得到需要观察的地址集合
                List<AbstractAddress> addressesToWatch = getAddressesToWatch();
                if (addressesToWatch.size() > 0) {
                    addressesPendingSubscription.addAll(addressesToWatch);
                    blockchainConnection.subscribeToAddresses(addressesToWatch, this);
                    queueOnConnectivity();
                }
            }
        } catch (Exception e) {
            log.error("Error subscribing to addresses", e);
        } finally {
            lock.unlock();
        }
    }

    private void clearTransientState() {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        addressesSubscribed.clear();
        addressesPendingSubscription.clear();
        statusPendingUpdates.clear();
        fetchingTransactions.clear();
        outOfOrderTransactions.clear();
        lastBalance = type.value(0);
    }

    /**
     * Used when de-serializing the wallet
     */
    void addUnspentOutput(OutPointOutput utxo) {
        lock.lock();
        try {
            unspentOutputs.put(utxo.getOutPoint(), utxo);
        } finally {
            lock.unlock();
        }
    }

    //此处会调用simpleAddTransaction
    public void restoreWalletTransactions(ArrayList<WalletTransaction<BitTransaction>> wtxs) {
        lock.lock();
        try {
            for (WalletTransaction<BitTransaction> wtx : wtxs) {
                BitTransaction tx = wtx.getTransaction();
                simpleAddTransaction(wtx.getPool(), tx);
                if (tx.getConfidenceType() == BUILDING && tx.getTimestamp() == 0) {
                    fetchTimestamp(tx, tx.getAppearedAtChainHeight());
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public Map<Sha256Hash, BitTransaction> getBitTransactionPool(WalletTransaction.Pool pool) {
        lock.lock();
        try {
            switch (pool) {
                case CONFIRMED:
                    return Maps.newHashMap(confirmed);
                case PENDING:
                    return Maps.newHashMap(pending);
                default:
                    throw new RuntimeException("Unknown wallet transaction type " + pool);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<Sha256Hash, Transaction> getTransactionPool(org.bitcoinj.wallet.WalletTransaction.Pool pool) {
        lock.lock();
        try {
            switch (pool) {
                case UNSPENT:
                case SPENT:
                    return toRawTransactions(confirmed);
                case PENDING:
                    return toRawTransactions(pending);
                case DEAD:
                default:
                    throw new RuntimeException("Unknown wallet transaction type " + pool);
            }
        } finally {
            lock.unlock();
        }
    }

    static Map<Sha256Hash, Transaction> toRawTransactions(Map<Sha256Hash, BitTransaction> txs) {
        Map<Sha256Hash, Transaction> rawTxs = new HashMap<>(txs.size());
        for (Map.Entry<Sha256Hash, BitTransaction> tx : txs.entrySet()) {
            rawTxs.put(tx.getKey(), tx.getValue().getRawTransaction());
        }
        return rawTxs;
    }

    void queueOnNewBalance() {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");

        final Value balance = getBalance();

        // If balance changed, send event
        if (balance.compareTo(lastBalance) != 0) {
            lastBalance = balance;
            log.info("New balance {}", balance);
            for (final ListenerRegistration<WalletAccountEventListener> registration : listeners) {
                registration.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        registration.listener.onNewBalance(balance);
                        registration.listener.onWalletChanged(TransactionWatcherWallet.this);
                    }
                });
            }
        }
    }

    void queueOnNewBlock() {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        for (final ListenerRegistration<WalletAccountEventListener> registration : listeners) {
            registration.executor.execute(new Runnable() {
                @Override
                public void run() {
                    registration.listener.onNewBlock(TransactionWatcherWallet.this);
                    registration.listener.onWalletChanged(TransactionWatcherWallet.this);
                }
            });
        }
    }

    void queueOnConnectivity() {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        final WalletConnectivityStatus newConnectivity = getConnectivityStatus();
        if (newConnectivity != lastConnectivity) {
            lastConnectivity = newConnectivity;
            for (final ListenerRegistration<WalletAccountEventListener> registration : listeners) {
                registration.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        registration.listener.onConnectivityStatus(newConnectivity);
                        registration.listener.onWalletChanged(TransactionWatcherWallet.this);
                    }
                });
            }
        }
    }

    void queueOnTransactionBroadcastSuccess(final BitTransaction tx) {
        for (final ListenerRegistration<WalletAccountEventListener> registration : listeners) {
            registration.executor.execute(new Runnable() {
                @Override
                public void run() {
                    registration.listener.onTransactionBroadcastSuccess(TransactionWatcherWallet.this, tx);
                }
            });
        }
    }

    void queueOnTransactionBroadcastFailure(final BitTransaction tx) {
        for (final ListenerRegistration<WalletAccountEventListener> registration : listeners) {
            registration.executor.execute(new Runnable() {
                @Override
                public void run() {
                    registration.listener.onTransactionBroadcastFailure(TransactionWatcherWallet.this, tx);
                }
            });
        }
    }

    public void addEventListener(WalletAccountEventListener listener) {
        addEventListener(listener, Threading.USER_THREAD);
    }

    public void addEventListener(WalletAccountEventListener listener, Executor executor) {
        listeners.add(new ListenerRegistration<>(listener, executor));
    }

    public boolean removeEventListener(WalletAccountEventListener listener) {
        return ListenerRegistration.removeFromList(listener, listeners);
    }

    public boolean isLoading() {
        lock.lock();
        try {
            return blockchainConnection != null && (addressesStatus.isEmpty() ||
                    !addressesPendingSubscription.isEmpty() ||
                    !statusPendingUpdates.isEmpty() ||
                    !fetchingTransactions.isEmpty());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void broadcastTx(AbstractTransaction tx) throws TransactionBroadcastException {
        if (tx instanceof BitTransaction) {
            // TODO throw transaction broadcast exception
            broadcastTx((BitTransaction) tx, this);
        } else {
            throw new TransactionBroadcastException("Incompatible transaction type: " + tx.getClass().getName());
        }
    }

    @Override
    public boolean broadcastTxSync(AbstractTransaction tx) throws TransactionBroadcastException {
        if (tx instanceof BitTransaction) {
            return broadcastBitTxSync((BitTransaction) tx);
        } else {
            throw new TransactionBroadcastException("Unsupported transaction class: " +
                    tx.getClass().getName() + ", need: " + BitTransaction.class.getName());
        }
    }

    private boolean broadcastBitTxSync(BitTransaction tx) throws TransactionBroadcastException {
        if (isConnected()) {
            lock.lock();
            try {
                if (log.isInfoEnabled()) {
                    log.info("Broadcasting tx {}", Utils.HEX.encode(tx.bitcoinSerialize()));
                }
                boolean success = blockchainConnection.broadcastTxSync(tx);
                if (success) {
                    onTransactionBroadcast(tx);
                } else {
                    onTransactionBroadcastError(tx);
                }
                return success;
            } finally {
                lock.unlock();
            }
        } else {
            throw new TransactionBroadcastException("No connection available");
        }
    }

    private void broadcastTx(BitTransaction tx, TransactionEventListener<BitTransaction> listener)
            throws TransactionBroadcastException {
        if (isConnected()) {
            lock.lock();
            try {
                if (log.isInfoEnabled()) {
                    log.info("Broadcasting tx {}", Utils.HEX.encode(tx.bitcoinSerialize()));
                }
                blockchainConnection.broadcastTx(tx, listener != null ? listener : this);
            } finally {
                lock.unlock();
            }
        } else {
            throw new TransactionBroadcastException("No connection available");
        }
    }

    public boolean isConnected() {
        lock.lock();
        try {
            return blockchainConnection != null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void disconnect() {
        lock.lock();
        try {
            if (blockchainConnection != null) {
                blockchainConnection.stopAsync();
            }
        } finally {
            lock.unlock();
        }

    }

    //获取未花费输出
    Map<TrimmedOutPoint, OutPointOutput> getUnspentOutputs(boolean includeUnsafe) {
        lock.lock();
        try {
            // The confirmed UTXO set
            Map<TrimmedOutPoint, OutPointOutput> utxoSet = Maps.newHashMap(unspentOutputs);
            Set<TrimmedOutPoint> spentTxo = new HashSet<>();
            Set<TrimmedOutPoint> txSpendingTxo = new HashSet<>();

            // The unconfirmed UTXO set originating from transactions the wallet sends
            //来自钱包发送的交易的未经确认的utxo集合
            for (BitTransaction tx : pending.values()) {
                if (includeUnsafe || tx.getSource() == Source.SELF) {
                    boolean isDoubleSpending = false;
                    txSpendingTxo.clear();
                    // Remove any unspent outputs that this transaction spends
                    for (TransactionInput txi : tx.getInputs()) {
                        TrimmedOutPoint outPoint = TrimmedOutPoint.get(txi);
                        // Check if another transaction
                        if (spentTxo.contains(outPoint)) {
                            log.warn("Transaction {} double-spends outpoint {}:{}",
                                    tx.getHash(), outPoint.getHash(), outPoint.getIndex());
                            isDoubleSpending = true;
                            break;
                        }
                        txSpendingTxo.add(outPoint);
                    }

                    if (isDoubleSpending) continue;

                    spentTxo.addAll(txSpendingTxo);

                    List<TransactionOutput> outputs = tx.getOutputs();
                    for (int i = 0; i < outputs.size(); i++) {
                        TransactionOutput output = outputs.get(i);
                        if (output.isMineOrWatched(this)) {
                            OutPointOutput outPointOutput = new OutPointOutput(tx, i);
                            utxoSet.put(outPointOutput.getOutPoint(), outPointOutput);
                        }
                    }
                }
            }

            // Remove any outputs that are spent
            for (TrimmedOutPoint spent : spentTxo) {
                utxoSet.remove(spent);
            }

            return utxoSet;
        } finally {
            lock.unlock();
        }
    }

    @Nullable
    public BitTransaction getTransaction(Sha256Hash txId) {
        lock.lock();
        try {
            return rawTransactions.get(txId);
        } finally {
            lock.unlock();
        }
    }

    @Nullable
    @Override
    public BitTransaction getTransaction(String transactionId) {
        lock.lock();
        try {
            return rawTransactions.get(new Sha256Hash(transactionId));
        } finally {
            lock.unlock();
        }
    }

    //得到一个该币种账号的交易历史
    @Override
    public Map<Sha256Hash, BitTransaction> getTransactions() {
        lock.lock();
        try {
            return ImmutableMap.copyOf(rawTransactions);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<Sha256Hash, BitTransaction> getPendingTransactions() {
        lock.lock();
        try {
            return ImmutableMap.copyOf(pending);
        } finally {
            lock.unlock();
        }
    }

    public OutPointOutput getUnspentTxOutput(TransactionOutPoint outPoint) {
        lock.lock();
        try {
            return unspentOutputs.get(TrimmedOutPoint.get(outPoint));
        } finally {
            lock.unlock();
        }
    }
}
