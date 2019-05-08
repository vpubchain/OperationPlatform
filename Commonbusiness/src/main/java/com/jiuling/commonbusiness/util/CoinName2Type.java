package com.jiuling.commonbusiness.util;

/**
 * Created by Chain-Cloud on 2018/8/16.
 */

public class CoinName2Type {

    public static int get(String name) {

        int type = 0;
        switch (name){

            case "btc":
                type = 0;
                break;
            case "eth":
                type = 1;
                break;
            case "ltc":
                type = 3;
                break;
            case "bch":
                type = 4;
                break;
            case "vechain":
                type = 1000;
                break;
            case "omisego":
                type = 1001;
                break;
            case "icon":
                type = 1002;
                break;
            case "game":
                type = 1003;
                break;
            case "bnb":
                type = 1004;
                break;
            case "etc":
                type = 12;
                break;
            default:
                type = 0;
                break;



        }

        return type;

    }
}
