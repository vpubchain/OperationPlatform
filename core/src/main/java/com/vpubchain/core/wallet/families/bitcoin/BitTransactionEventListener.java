package com.vpubchain.core.wallet.families.bitcoin;

import com.vpubchain.core.network.AddressStatus;
import com.vpubchain.core.network.ServerClient.UnspentTx;
import com.vpubchain.core.network.interfaces.TransactionEventListener;

import java.util.List;

/**
 * @author John L. Jegutanis
 */
public interface BitTransactionEventListener extends TransactionEventListener<BitTransaction> {
    void onUnspentTransactionUpdate(AddressStatus status, List<UnspentTx> UnspentTxes);
}
