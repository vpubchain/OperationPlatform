package com.vpubchain.core.coins.families;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.exceptions.AddressMalformedException;
import com.vpubchain.core.wallet.families.bitcoin.BitAddress;

/**
 * @author John L. Jegutanis
 *
 * This is the classical Suqa family that includes Litecoin, Dogecoin, Dash, etc
 */
public abstract class BitFamily extends CoinType {
    {
        family = Families.BITCOIN;
    }

    @Override
    public BitAddress newAddress(String addressStr) throws AddressMalformedException {
        return BitAddress.from(this, addressStr);
    }
}
