package com.jiuling.operate.data;


import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.RequestRechargeBean;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateRechargeActivityModel {

    private ApiService mApiService;

    public OperateRechargeActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }

    public Observable<String> recharge(String authorization, RequestRechargeBean requestRechargeBean){
        return mApiService.recharge(authorization,requestRechargeBean);
    }



}
