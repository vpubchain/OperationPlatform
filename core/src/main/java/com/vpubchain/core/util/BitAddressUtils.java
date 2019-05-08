package com.vpubchain.core.util;

import com.vpubchain.core.exceptions.AddressMalformedException;
import com.vpubchain.core.wallet.AbstractAddress;
import com.vpubchain.core.wallet.families.bitcoin.BitAddress;

import org.bitcoinj.script.Script;

import static com.vpubchain.core.Preconditions.checkArgument;

/**
 * @author John L. Jegutanis
 */
public class BitAddressUtils {
    public static boolean isP2SHAddress(AbstractAddress address) {
        checkArgument(address instanceof BitAddress, "This address cannot be a P2SH address");
        return ((BitAddress) address).isP2SHAddress();
    }

    public static byte[] getHash160(AbstractAddress address) {
        checkArgument(address instanceof BitAddress, "Cannot get hash160 from this address");
        return ((BitAddress) address).getHash160();
    }

    public static boolean producesAddress(Script script, AbstractAddress address) {
        try {
            return BitAddress.from(address.getType(), script).equals(address);
        } catch (AddressMalformedException e) {
            return false;
        }
    }
}
