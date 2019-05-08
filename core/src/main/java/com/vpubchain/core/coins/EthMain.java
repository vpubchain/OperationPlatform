package com.vpubchain.core.coins;

import com.vpubchain.core.coins.families.BitFamily;
import com.vpubchain.core.coins.families.EthFamily;

/**
 * @author John L. Jegutanis
 */
public class EthMain extends EthFamily {
    public EthMain() {
        id = "eth.main"; // Do not change this id as wallets serialize this string

        addressHeader = 76;
        p2shHeader = 16;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 204;

        name = "Ethereum";
        symbol = "ETH";
        uriScheme = "eth"; // TODO add multi uri, darkcoin
        bip44Index = 5;
        unitExponent = 8;
        feeValue = value(100000);
        minNonDust = value(1000); // 0.00001 DASH mininput
        softDustLimit = value(100000); // 0.001 DASH
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
        signedMessageHeader = toBytes("DarkCoin Signed Message:\n");
    }

    private static EthMain instance = new EthMain();
    public static synchronized CoinType get() {
        return instance;
    }
}
