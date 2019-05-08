package com.vpubchain.core.wallet.families.eth;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.nxt.Account;
import com.vpubchain.core.coins.nxt.Convert;
import com.vpubchain.core.exceptions.AddressMalformedException;
import com.vpubchain.core.wallet.AbstractAddress;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.WrongNetworkException;
import org.bitcoinj.script.Script;

import java.nio.ByteBuffer;

/**
 * @author John L. Jegutanis
 */
public class EthAddress  implements AbstractAddress {

    private final CoinType type;
    private final byte[] publicKey;
    private final long accountId;
    private final String rsAccount;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EthAddress(CoinType type, byte[] pubKey, String address) {
        this.type = type;
        publicKey = pubKey;
        accountId = Account.getId(pubKey);
        rsAccount = Convert.rsAccount(type, accountId);
        this.address = address;
    }

    public EthAddress(CoinType type, long accountId, String rsAccount) {
        this.type = type;
        publicKey = null;
        this.accountId = accountId;
        this.rsAccount = rsAccount;
    }

    public EthAddress(CoinType type, String rsAccount) {
        this.type = type;
        publicKey = null;
        //待更改
        this.accountId = 9527L;
        this.rsAccount = rsAccount;
        this.address = rsAccount;
    }

    public EthAddress(CoinType type, long accountId) {
        this.type = type;
        publicKey = null;
        this.accountId = accountId;
        this.rsAccount = Convert.rsAccount(type, accountId);
    }

    @Override
    public CoinType getType() {
        return type;
    }

    @Override
    public long getId() {
        return accountId;
    }

    @Override
    public String toString() {
        return address;
    }
}
