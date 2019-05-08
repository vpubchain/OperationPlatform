package com.vpubchain.core.util;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.ValueType;
import com.vpubchain.core.wallet.AbstractAddress;
import com.vpubchain.core.wallet.WalletAccount;

/**
 * @author John L. Jegutanis
 */
public class TypeUtils {
    public static boolean is(CoinType myType, WalletAccount other) {
        return other != null && myType.equals(other.getCoinType());
    }
    
    public static boolean is(CoinType myType, ValueType otherType) {
        return otherType != null && myType.equals(otherType);
    }
    
    public static boolean is(CoinType myType, AbstractAddress address) {
        return address != null && myType.equals(address.getType());
    }
}
