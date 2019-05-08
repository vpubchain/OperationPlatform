package com.jiuling.operate.data;


import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.ContactUsBean;
import com.jiuling.operate.entity.SetUserInfoBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateContactUsActivityModel {

    private ApiService mApiService;

    public OperateContactUsActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }


    public Observable<BaseResponse<Boolean>> contactUs(String authorization, ContactUsBean contactUsBean){


        return  mApiService.contactUs(authorization,contactUsBean);
    }



}
