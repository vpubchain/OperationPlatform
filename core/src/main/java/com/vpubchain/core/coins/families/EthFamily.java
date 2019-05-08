package com.vpubchain.core.coins.families;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.exceptions.AddressMalformedException;
import com.vpubchain.core.wallet.families.bitcoin.BitAddress;

import org.bitcoinj.core.AddressFormatException;

public abstract class EthFamily extends CoinType {

    {
        family = Families.ETH;
    }

    @Override
    public BitAddress newAddress(String addressStr) throws AddressMalformedException {
        return BitAddress.from(this, addressStr);
    }
//    public static BitAddress from(CoinType type, String address) throws AddressMalformedException {
//        try {
//            return new BitAddress(type, address);
//        } catch (AddressFormatException e) {
//            throw new AddressMalformedException(e);
//        }
//    }



}
