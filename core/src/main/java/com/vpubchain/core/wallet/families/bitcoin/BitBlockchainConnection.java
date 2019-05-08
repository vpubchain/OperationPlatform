package com.vpubchain.core.wallet.families.bitcoin;

import com.vpubchain.core.network.AddressStatus;
import com.vpubchain.core.network.interfaces.BlockchainConnection;

/**
 * @author John L. Jegutanis
 */
public interface BitBlockchainConnection extends BlockchainConnection<BitTransaction> {
    void getUnspentTx(AddressStatus status, BitTransactionEventListener listener);
}
