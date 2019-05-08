package com.vpubchain.core.network.interfaces;

/**
 * @author John L. Jegutanis
 */
public interface ConnectionEventListener {
    void onConnection(BlockchainConnection blockchainConnection);
    void onDisconnect();
}
