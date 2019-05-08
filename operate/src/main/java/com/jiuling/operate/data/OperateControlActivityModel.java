package com.jiuling.operate.data;


import android.util.Log;

import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.LoginResultBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateControlActivityModel {

    private ApiService mApiService;

    public OperateControlActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }


    public Observable<BaseResponse<UserMasternodeCountBean>> getUserMasternodeCount(String authorization){


        return  mApiService.getUserMasternodeCount(authorization);
    }



    public Observable<BaseResponse<StatisticsIncomeBean>> getUserMasternodeIncomeDay(String authorization){


        return  mApiService.getUserMasternodeIncomeDay(authorization);
    }

    public Observable<BaseResponse<StatisticsIncomeBean>> getUserMasternodeIncomeMonth(String authorization){


        return  mApiService.getUserMasternodeIncomeMonth(authorization);
    }







}
