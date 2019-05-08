package com.jiuling.commonbusiness.util;


import com.jiuling.commonbusiness.Constans;

/**
 * Created by Chain-Cloud on 2018/8/6.
 */

public class CoinTypeToName {

    public static String CoinTypeToName(int type){
//临时方法
        String coinName = "";
        switch (type){
            case Constans.TYPE_BIT_COIN:
                coinName = Constans.BTC;
                break;
            case Constans.TYPE_ETH_COIN:
                coinName = Constans.ETH;
                break;
            case Constans.TYPE_LTC_COIN:
                coinName = Constans.LTC;
                break;
            case Constans.TYPE_BCH_COIN:
                coinName = Constans.BCH;
                break;
            case Constans.TYPE_EOS_COIN:
                coinName = Constans.EOS;
                break;
            case Constans.TYPE_VECHAIN_COIN:
                coinName = Constans.VECHAIN;
                break;
            case Constans.TYPE_DASH_COIN:
                coinName = Constans.DASH;
                break;
            case Constans.TYPE_OMG_COIN:
                coinName = Constans.OMG;
                break;
            case Constans.TYPE_ICX_COIN:
                coinName = Constans.ICX;
                break;
            case Constans.TYPE_GTC_COIN:
                coinName = Constans.GTC;
                break;
            case Constans.TYPE_BNB_COIN:
                coinName = Constans.BNB;
                break;
            case Constans.TYPE_ETC_COIN:
                coinName = Constans.ETC;
                break;

        }

        return coinName;

    }

}
