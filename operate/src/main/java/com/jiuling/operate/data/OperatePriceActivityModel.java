package com.jiuling.operate.data;


import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.PriceBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperatePriceActivityModel {

    private ApiService mApiService;

    public OperatePriceActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }


    public Observable<BaseResponse<List<PriceBean>>> getNodePrice(String authorization){


        return  mApiService.getNodePrice(authorization);
    }

}
