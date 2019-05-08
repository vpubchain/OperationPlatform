package com.vpubchain.core.wallet;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.math.LongMath;
import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.Value;
import com.vpubchain.core.event.MessageEvent;
import com.vpubchain.core.exceptions.Bip44KeyLookAheadExceededException;
import com.vpubchain.core.protos.Protos;
import com.vpubchain.core.util.KeyUtils;
import com.vpubchain.core.wallet.families.bitcoin.BitAddress;
import com.vpubchain.core.wallet.families.bitcoin.OutPointOutput;
import com.vpubchain.core.wallet.families.eth.EthAddress;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.KeyCrypter;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.RedeemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.crypto.params.KeyParameter;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.AdminFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import rx.Observer;

import static com.vpubchain.core.Preconditions.checkArgument;
import static com.vpubchain.core.Preconditions.checkNotNull;
import static com.vpubchain.core.util.BitAddressUtils.getHash160;
import static org.bitcoinj.wallet.KeyChain.KeyPurpose.CHANGE;
import static org.bitcoinj.wallet.KeyChain.KeyPurpose.RECEIVE_FUNDS;
import static org.bitcoinj.wallet.KeyChain.KeyPurpose.REFUND;

public class EthWalletPocketHD extends BitWalletBase{
    private static final Logger log = LoggerFactory.getLogger(EthWalletPocketHD.class);

    @VisibleForTesting
    protected SimpleHDKeyChain keys;
    protected DeterministicKey rootkey;
    private String address;
    public static String privateKey;
    public static String publicKey;
    public static BigInteger nonce;

    private long longValue = 0;

    /**
     * rootk 获取币种当前路径后生成的确认性密钥
     * coinType 币种类型
     * key 关键参数aesKey = crypter.deriveKey(password);
     */
    public EthWalletPocketHD(DeterministicKey rootKey, CoinType coinType,
                             @Nullable KeyCrypter keyCrypter, @Nullable KeyParameter key) {
        //Wallet 调用该构造方法创建钱包
        this(new SimpleHDKeyChain(rootKey, keyCrypter, key), coinType);
        //直接创建eth地址

        String[] pathArray = "m/44'/60'/0'/0/0".split("/");

        DeterministicKey dkKey = Wallet.masterKeyEthUse;
        for (int i = 1; i < pathArray.length; i++) {
            ChildNumber childNumber;
            if (pathArray[i].endsWith("'")) {
                int number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length() - 1));
                childNumber = new ChildNumber(number, true);
            } else {
                int number = Integer.parseInt(pathArray[i]);
                childNumber = new ChildNumber(number, false);
            }
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
        }
        ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());
        privateKey = keyPair.getPrivateKey().toString(16);
        publicKey = keyPair.getPublicKey().toString(16);
        try {
            WalletFile walletFile = org.web3j.crypto.Wallet.createLight("", keyPair);
            address = "0x" + walletFile.getAddress();
            log.error("eth地址"+address);
        } catch (CipherException e) {

        }


    }

    EthWalletPocketHD(SimpleHDKeyChain keys, CoinType coinType) {
        this(KeyUtils.getPublicKeyId(coinType, keys.getRootKey().getPubKey()), keys, coinType);
    }

    EthWalletPocketHD(String id, SimpleHDKeyChain keys, CoinType coinType) {
        super(checkNotNull(coinType), id);
        this.keys = checkNotNull(keys);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Region vending transactions and other internal state
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取此帐户的BIP44索引
     */
    public int getAccountIndex() {
        lock.lock();
        try {
            log.error("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"+keys.getAccountIndex());
            return keys.getAccountIndex();
        } finally {
            lock.unlock();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Serialization support
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    List<Protos.Key> serializeKeychainToProtobuf() {
        lock.lock();
        try {
            return keys.toProtobuf();
        } finally {
            lock.unlock();
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Encryption support
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean isEncryptable() {
        return true;
    }

    @Override
    public boolean isEncrypted() {
        lock.lock();
        try {
            return keys.isEncrypted();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get the wallet pocket's KeyCrypter, or null if the wallet pocket is not encrypted.
     * (Used in encrypting/ decrypting an ECKey).
     *获取钱包口袋的密钥加密器，如果钱包口袋未加密，则为空。
     *（用于加密/解密Eckey）
     */
    @Nullable
    @Override
    public KeyCrypter getKeyCrypter() {
        lock.lock();
        try {
            return keys.getKeyCrypter();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Encrypt the keys in the group using the KeyCrypter and the AES key. A good default KeyCrypter to use is
     * {@link org.bitcoinj.crypto.KeyCrypterScrypt}.
     *
     * @throws org.bitcoinj.crypto.KeyCrypterException Thrown if the wallet encryption fails for some reason,
     *         leaving the group unchanged.
     *
     *         使用密钥加密器和AES密钥加密组中的密钥。一个好的默认密钥加密器是
     *@link org.bitcoinj.crypto.keycrypterscrypt。
     *
     *@抛出org.bitcoinj.crypto.keyCrypterException，如果钱包加密因某种原因失败，
     *保持组不变。
     *
     */
    @Override
    public void encrypt(KeyCrypter keyCrypter, KeyParameter aesKey) {
        checkNotNull(keyCrypter);
        checkNotNull(aesKey);

        lock.lock();
        try {
            this.keys = this.keys.toEncrypted(keyCrypter, aesKey);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Decrypt the keys in the group using the previously given key crypter and the AES key. A good default
     * KeyCrypter to use is {@link org.bitcoinj.crypto.KeyCrypterScrypt}.
     *
     * @throws org.bitcoinj.crypto.KeyCrypterException Thrown if the wallet decryption fails for some reason, leaving the group unchanged.
     */
    @Override
    public void decrypt(KeyParameter aesKey) {
        checkNotNull(aesKey);

        lock.lock();
        try {
            this.keys = this.keys.toDecrypted(aesKey);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getPublicKeySerialized() {
        // Change the path of the key to match the BIP32 paths i.e. 0H/<account index>H
        //更改密钥的路径以匹配bip32路径，即0h/<account index>h
        DeterministicKey key = keys.getWatchingKey();
        ImmutableList<ChildNumber> path = ImmutableList.of(key.getChildNumber());
        key = new DeterministicKey(path, key.getChainCode(), key.getPubKeyPoint(), null, null);
        return key.serializePubB58();
    }

    @Override
    public boolean isWatchedScript(Script script) {
        // Not supported
        return false;
    }

    @Override
    public boolean isPayToScriptHashMine(byte[] payToScriptHash) {
        // Not supported
        return false;
    }

    /**
     * Locates a keypair from the basicKeyChain given the hash of the public key. This is needed
     * when finding out which key we need to use to redeem a transaction output.
     *
     * @return ECKey object or null if no such key was found.
     * 从给定公钥哈希的basickeychain中查找密钥对。这是需要的

     *当找出需要使用哪个键来兑现事务输出时。

     *

     *@返回Eckey对象，如果找不到此类键，则返回空值。
     */
    @Nullable
    @Override
    public ECKey findKeyFromPubHash(byte[] pubkeyHash) {
        lock.lock();
        try {
            return keys.findKeyFromPubHash(pubkeyHash);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Locates a keypair from the basicKeyChain given the raw public key bytes.
     * @return ECKey or null if no such key was found.
     */
    @Nullable
    @Override
    public ECKey findKeyFromPubKey(byte[] pubkey) {
        lock.lock();
        try {
            return keys.findKeyFromPubKey(pubkey);
        } finally {
            lock.unlock();
        }
    }

    @Nullable
    @Override
    public RedeemData findRedeemDataFromScriptHash(byte[] bytes) {
        return null;
    }

    @Override
    public byte[] getPublicKey() {
        lock.lock();
        try {
            return keys.getRootKey().getPubKey();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public EthAddress getChangeAddress() {
        return currentAddress(CHANGE);
    }

    @Override
    public EthAddress getReceiveAddress() {
        return currentAddress(RECEIVE_FUNDS);
    }

    public EthAddress getRefundAddress() { return currentAddress(REFUND); }

    @Override
    public boolean hasUsedAddresses() {
        return getNumberIssuedReceiveAddresses() != 0;
    }

    @Override
    public boolean canCreateNewAddresses() {
        return true;
    }

    @Override
    public EthAddress getReceiveAddress(boolean isManualAddressManagement) {
        return getAddress(RECEIVE_FUNDS, isManualAddressManagement);
    }

    @Override
    public EthAddress getRefundAddress(boolean isManualAddressManagement) {
        return getAddress(REFUND, isManualAddressManagement);
    }

    /**
     * Get the last used receiving address
     * 获取上次使用的接收地址keys.getLastIssuedKey(purpose)
     */
    @Nullable
    public EthAddress getLastUsedAddress(SimpleHDKeyChain.KeyPurpose purpose) {
        lock.lock();
        try {
            return getAddress();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns true is it is possible to create new fresh receive addresses, false otherwise
     * 返回true是否可以创建新的接收地址，否则返回false
     */
    public boolean canCreateFreshReceiveAddress() {
        lock.lock();
        try {
            DeterministicKey currentUnusedKey = keys.getCurrentUnusedKey(RECEIVE_FUNDS);
            int maximumKeyIndex = SimpleHDKeyChain.LOOKAHEAD - 1;

            // If there are used keys
            if (!addressesStatus.isEmpty()) {
                int lastUsedKeyIndex = 0;
                // Find the last used key index
                for (Map.Entry<AbstractAddress, String> entry : addressesStatus.entrySet()) {
                    if (entry.getValue() == null) continue;
                    DeterministicKey usedKey = keys.findKeyFromPubHash(getHash160(entry.getKey()));
                    if (usedKey != null && keys.isExternal(usedKey) && usedKey.getChildNumber().num() > lastUsedKeyIndex) {
                        lastUsedKeyIndex = usedKey.getChildNumber().num();
                    }
                }
                maximumKeyIndex = lastUsedKeyIndex + SimpleHDKeyChain.LOOKAHEAD;
            }

            log.info("Maximum key index for new key is {}", maximumKeyIndex);

            // If we exceeded the BIP44 look ahead threshold
            return currentUnusedKey.getChildNumber().num() < maximumKeyIndex;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get a fresh address by marking the current receive address as used. It will throw
     * {@link Bip44KeyLookAheadExceededException} if we requested too many addresses that
     * exceed the BIP44 look ahead threshold.
     * 通过将当前接收地址标记为已使用，获取新地址。它会投掷

     *@link bip44keylookaheadexception如果我们请求的地址太多，

     *超过了BIP44先行阈值
     */
    public EthAddress getFreshReceiveAddress() throws Bip44KeyLookAheadExceededException {
        lock.lock();
        try {
            return getAddress();
        } finally {
            lock.unlock();
            walletSaveNow();
        }
    }

    public EthAddress getFreshReceiveAddress(boolean isManualAddressManagement)
            throws Bip44KeyLookAheadExceededException {
        lock.lock();
        try {
            EthAddress newAddress = null;
            EthAddress freshAddress = getFreshReceiveAddress();
            if (isManualAddressManagement) {
                newAddress = getLastUsedAddress(RECEIVE_FUNDS);
            }
            if (newAddress == null) {
                newAddress = freshAddress;
            }
            return newAddress;
        } finally {
            lock.unlock();
            walletSaveNow();
        }
    }

    private static final Comparator<DeterministicKey> HD_KEY_COMPARATOR =
            new Comparator<DeterministicKey>() {
                @Override
                public int compare(final DeterministicKey k1, final DeterministicKey k2) {
                    int key1Num = k1.getChildNumber().num();
                    int key2Num = k2.getChildNumber().num();
                    // In reality Integer.compare(key2Num, key1Num) but is not available on older devices
                    return (key2Num < key1Num) ? -1 : ((key2Num == key1Num) ? 0 : 1);
                }
            };

    /**
     * Returns the number of issued receiving keys
     * 返回已发出的接收密钥数
     */
    public int getNumberIssuedReceiveAddresses() {
        lock.lock();
        try {
            return keys.getNumIssuedExternalKeys();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a list of addresses that have been issued.
     * The list is sorted in descending chronological order: older in the end
     *
     返回已发出的地址列表。

     *列表按时间顺序降序排序：最后是旧的
     */
    public List<AbstractAddress> getIssuedReceiveAddresses() {
        lock.lock();
        try {
            ArrayList<DeterministicKey> issuedKeys = keys.getIssuedExternalKeys();
            ArrayList<AbstractAddress> receiveAddresses = new ArrayList<>();

            Collections.sort(issuedKeys, HD_KEY_COMPARATOR);

            for (ECKey key : issuedKeys) {
                receiveAddresses.add(BitAddress.from(type, key));
            }
            return receiveAddresses;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get the currently used receiving and change addresses获取当前使用的接收和更改地址
     */
    public Set<AbstractAddress> getUsedAddresses() {
        lock.lock();
        try {
            HashSet<AbstractAddress> usedAddresses = new HashSet<>();

            for (Map.Entry<AbstractAddress, String> entry : addressesStatus.entrySet()) {
                if (entry.getValue() != null) {
                    usedAddresses.add(entry.getKey());
                }
            }

            return usedAddresses;
        } finally {
            lock.unlock();
        }
    }

    public EthAddress getAddress(SimpleHDKeyChain.KeyPurpose purpose,
                                 boolean isManualAddressManagement) {

        return getAddress();
    }

    /**
     * Get the currently latest unused address by purpose.按用途获取当前未使用的最新地址。
     */
    @VisibleForTesting EthAddress currentAddress(SimpleHDKeyChain.KeyPurpose purpose) {
        lock.lock();
        try {
//            List<String> listWords = new ArrayList<>();
//            listWords.add("crunch");
//            listWords.add("ramp");
//            listWords.add("poet");
//            listWords.add("pride");
//            listWords.add("until");
//            listWords.add("crawl");
//            listWords.add("weekend");
//            listWords.add("catalog");
//            listWords.add("expose");
//            listWords.add("rare");
//            listWords.add("urge");
//            listWords.add("crane");
//            long creationTimeSeconds = System.currentTimeMillis() / 1000;
//            DeterministicSeed seed = new DeterministicSeed(listWords, null, "", creationTimeSeconds);
//            byte[] seeds = seed.getSeedBytes();
//            if (seeds == null)
//                return null;
//            DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seeds);

//            //返回eth地址

            EthAddress ethAddress = new EthAddress(type,address);
            return  ethAddress;
//            return BitAddress.from(type, keys.getCurrentUnusedKey(purpose));

        } finally {
            lock.unlock();
            subscribeToAddressesIfNeeded();
        }
    }

    /**
     * Used to force keys creation, could take long time to complete so use it in a background
     * thread.
     * 用于强制创建密钥，可能需要很长时间才能完成，因此请在后台使用它

     *线程。
     */
    @VisibleForTesting
    public void maybeInitializeAllKeys() {
        lock.lock();
        try {
            keys.maybeLookAhead();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getPublicKeyMnemonic() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<AbstractAddress> getActiveAddresses() {
        lock.lock();
        try {
//            ImmutableList.Builder<AbstractAddress> activeAddresses = ImmutableList.builder();
//            for (DeterministicKey key : keys.getActiveKeys()) {
//                log.error(key.getPathAsString()+"          "+key.toString());
//                activeAddresses.add(BitAddress.from(type, key));
//            }
//            return activeAddresses.build();
            List<AbstractAddress> activeAddress = new ArrayList<>();
            activeAddress.add(getAddress());
            return activeAddress;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void markAddressAsUsed(AbstractAddress address) {
        checkArgument(address.getType().equals(type), "Wrong address type");
        if (address instanceof BitAddress) {
            markAddressAsUsed((BitAddress)address);
        } else {
            throw new IllegalArgumentException("Wrong address class");
        }

    }

    public void markAddressAsUsed(BitAddress address) {
        keys.markPubHashAsUsed(address.getHash160());
    }

    @Override
    public String toString() {
        return EthWalletPocketHD.class.getSimpleName() + " " + id.substring(0, 4)+ " " + type;
    }

    public EthAddress getAddress(){
        EthAddress ethAddress = new EthAddress(type,address);
        return  ethAddress;
    }


    //获取钱包余额
    public Value getBalance() {
        log.error("计算余额");
//        try {
//            long value = 1111;
//            return type.value(value);
//        } finally {
//        }


        //请求网络获取余额
//        //test eth
//        final Admin web3j = AdminFactory.build(new HttpService("https://mainnet.infura.io/v3/5851647d663a415190b0ecebd377e186"));
        final Admin web3j = AdminFactory.build(new HttpService("https://ropsten.infura.io/v3/5851647d663a415190b0ecebd377e186"));

        new Thread(){
            @Override
            public void run() {
                super.run();
                String value= null;
                nonce = null;
                try {
//                    Admin web3j = AdminFactory.build(new HttpService("https://rinkeby.infura.io/v3/5851647d663a415190b0ecebd377e186"));

                    BigInteger integer = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
                    //格式化位数  longValue:0.1056373914                integer 105637391400000000
                    value= Convert.fromWei(integer.toString(), Convert.Unit.ETHER).toString();
                    log.error("balance:"+ Convert.fromWei(integer.toString(), Convert.Unit.ETHER)+"                 "+integer);

                    //获取nonce
                     nonce = web3j.ethGetTransactionCount(address,DefaultBlockParameterName.LATEST).send().getTransactionCount();
                     if (nonce == null){
                         nonce = new BigInteger("0");
                     }
                     //保存nonce  到文件

//                     Request request = web3j.ethGetTransactionCount("0x119b6143E10a1f85EE2ebFd47f14f201DaaF3c90",DefaultBlockParameterName.LATEST);

                    //test

                   longValue = integer.longValue();
//                   longValue = Long.valueOf(value);

//                    StringFormat.stringToFormat(NumberArithmeticUtils.safeDivide(bigDecimal,1e18),6)+" ETH";

                    org.greenrobot.eventbus.EventBus.getDefault().post(new MessageEvent(type.value(longValue)));



                    log.error(value+"余额");

                } catch (Exception e) {
                    e.printStackTrace();

                }



            }
        }.start();

        return type.value(longValue);

    }


}
