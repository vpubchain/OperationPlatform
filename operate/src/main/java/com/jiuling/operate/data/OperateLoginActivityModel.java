package com.jiuling.operate.data;


import android.util.Log;

import com.jiuling.commonbusiness.base.recyclerbase.BaseRecyclerBean;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.LoginResultBean;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateLoginActivityModel {

    private ApiService mApiService;

    public OperateLoginActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }


    public Observable<BaseResponse<Boolean>> getLoginCode(String mobileNumber){

        return  mApiService.getLoginCode("smsCode/"+ mobileNumber);
    }


    public Observable<LoginResultBean> login(String mobileNumbe, String code){
        Log.i("body",mobileNumbe+"          "+code);

        return  mApiService.login("mobile","server",mobileNumbe,code);
    }







}
