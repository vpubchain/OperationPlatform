package com.vpubchain.core.wallet.families.eth;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.Value;
import com.vpubchain.core.messages.TxMessage;
import com.vpubchain.core.wallet.AbstractAddress;
import com.vpubchain.core.wallet.AbstractTransaction;
import com.vpubchain.core.wallet.AbstractWallet;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.TransactionConfidence;

import java.util.List;

import javax.annotation.Nullable;

public class EthTransaction implements AbstractTransaction {



    @Override
    public CoinType getType() {
        return null;
    }

    @Override
    public Sha256Hash getHash() {
        return null;
    }

    @Override
    public String getHashAsString() {
        return null;
    }

    @Override
    public byte[] getHashBytes() {
        return new byte[0];
    }

    @Override
    public TransactionConfidence.ConfidenceType getConfidenceType() {
        return null;
    }

    @Override
    public void setConfidenceType(TransactionConfidence.ConfidenceType type) {

    }

    @Override
    public int getAppearedAtChainHeight() {
        return 0;
    }

    @Override
    public void setAppearedAtChainHeight(int appearedAtChainHeight) {

    }

    @Override
    public TransactionConfidence.Source getSource() {
        return null;
    }

    @Override
    public void setSource(TransactionConfidence.Source source) {

    }

    @Override
    public int getDepthInBlocks() {
        return 0;
    }

    @Override
    public void setDepthInBlocks(int depthInBlocks) {

    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public void setTimestamp(long timestamp) {

    }

    @Override
    public Value getValue(AbstractWallet wallet) {
        return null;
    }

    @Nullable
    @Override
    public Value getFee() {
        return null;
    }

    @Nullable
    @Override
    public TxMessage getMessage() {
        return null;
    }

    @Override
    public List<AbstractAddress> getReceivedFrom() {
        return null;
    }

    @Override
    public List<AbstractOutput> getSentTo() {
        return null;
    }

    @Override
    public boolean isGenerated() {
        return false;
    }

    @Override
    public boolean isTrimmed() {
        return false;
    }
}
