package com.vpubchain.core.wallet;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.Value;
import com.vpubchain.core.coins.ValueType;
import com.vpubchain.core.exceptions.TransactionBroadcastException;
import com.vpubchain.core.network.interfaces.ConnectionEventListener;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.crypto.KeyCrypter;
import org.bitcoinj.wallet.KeyBag;

import org.spongycastle.crypto.params.KeyParameter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

/**
 * @author John L. Jegutanis
 */
public interface WalletAccount<T extends AbstractTransaction, A extends AbstractAddress>
        extends KeyBag, ConnectionEventListener, Serializable {

    class WalletAccountException extends Exception {
        public WalletAccountException(Throwable cause) {
            super(cause);
        }

        public WalletAccountException(String s) {
            super(s);
        }
    }

    String getId();
    String getDescriptionOrCoinName();
    String getDescription();
    void setDescription(String description);
    byte[] getPublicKey();
    //获取币种类型
    CoinType getCoinType();

    boolean isNew();

    Value getBalance();

    void refresh();

    boolean isConnected();
    boolean isLoading();
    void disconnect();
    WalletConnectivityStatus getConnectivityStatus();

    /**
     * Returns the address used for change outputs. Note: this will probably go away in future.
     * 返回用于更改输出的地址。注意：这在将来可能会消失。
     */
    AbstractAddress getChangeAddress();

    /**
     * Get current receive address, does not mark it as used.
     * 获取当前接收地址，不将其标记为已使用。
     */
    AbstractAddress getReceiveAddress();

    /**
     * Get current refund address, does not mark it as used.
     * 获取当前退款地址，不将其标记为已使用。
     *
     * Notice: This address could be the same as the current receive address
     * 此地址可能与当前接收地址相同
     */
    AbstractAddress getRefundAddress(boolean isManualAddressManagement);

    AbstractAddress getReceiveAddress(boolean isManualAddressManagement) ;


    /**
     * Returns true if this wallet has previously used addresses
     * 如果此钱包以前使用过地址，则返回“真”
     */
    boolean hasUsedAddresses();


    boolean broadcastTxSync(AbstractTransaction tx) throws TransactionBroadcastException;

    void broadcastTx(AbstractTransaction tx) throws TransactionBroadcastException;

    /**
     * Returns true if this wallet can create new addresses
     */
    boolean canCreateNewAddresses();

    T getTransaction(String transactionId);
    Map<Sha256Hash, T> getPendingTransactions();
    Map<Sha256Hash, T> getTransactions();

    //获取活跃地址
    List<AbstractAddress> getActiveAddresses();
    //标记地址为已经使用
    void markAddressAsUsed(AbstractAddress address);

    void setWallet(Wallet wallet);

    Wallet getWallet();

    void walletSaveLater();
    void walletSaveNow();

    boolean isEncryptable();
    boolean isEncrypted();
    //密码器
    KeyCrypter getKeyCrypter();
    void encrypt(KeyCrypter keyCrypter, KeyParameter aesKey);
    void decrypt(KeyParameter aesKey);

    boolean equals(WalletAccount otherAccount);

    void addEventListener(WalletAccountEventListener listener);
    void addEventListener(WalletAccountEventListener listener, Executor executor);
    boolean removeEventListener(WalletAccountEventListener listener);

    boolean isType(WalletAccount other);
    boolean isType(ValueType type);
    boolean isType(AbstractAddress address);

    boolean isAddressMine(AbstractAddress address);

    void maybeInitializeAllKeys();

    String getPublicKeyMnemonic();

    SendRequest getEmptyWalletRequest(AbstractAddress destination) throws WalletAccountException;
    SendRequest getSendToRequest(AbstractAddress destination, Value amount) throws WalletAccountException;

    //待看
    void completeAndSignTx(SendRequest request) throws WalletAccountException;
    void completeTransaction(SendRequest request) throws WalletAccountException;
    //签名交易
    void signTransaction(SendRequest request) throws WalletAccountException;

    //签名消息
    void signMessage(SignedMessage unsignedMessage, @Nullable KeyParameter aesKey);
    //验证
    void verifyMessage(SignedMessage signedMessage);

    String getPublicKeySerialized();
}
