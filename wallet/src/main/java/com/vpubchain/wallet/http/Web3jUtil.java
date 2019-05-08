package com.vpubchain.wallet.http;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.AdminFactory;
import org.web3j.protocol.http.HttpService;

public class Web3jUtil {

    private volatile static Admin web3j = null;


//    public Admin getWeb3j(){
//        return  AdminFactory.build(new HttpService("https://mainnet.infura.io/v3/5851647d663a415190b0ecebd377e186"));
//    }

    public static Admin getInstance(){

        if (null == web3j){
            synchronized (Web3jUtil.class){
                if (null == web3j){
                    web3j = AdminFactory.build(new HttpService("https://ropsten.infura.io/v3/5851647d663a415190b0ecebd377e186"));
//                    web3j = AdminFactory.build(new HttpService("https://mainnet.infura.io/v3/5851647d663a415190b0ecebd377e186"));
                }
            }
        }

        return web3j;

    }





}
