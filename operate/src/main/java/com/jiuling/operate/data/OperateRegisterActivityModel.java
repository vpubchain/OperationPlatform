package com.jiuling.operate.data;


import com.jiuling.commonbusiness.base.recyclerbase.BaseRecyclerBean;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.RegisterBean;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateRegisterActivityModel {

    private ApiService mApiService;

    public OperateRegisterActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }



    public Observable<BaseResponse<Boolean>> getRegisterCode(String mobileNumber){

        return  mApiService.getRegisterCode("smsCode/reg/"+ mobileNumber);
    }


    public Observable<BaseResponse<Boolean>> register(String mobileNumber,String code){


        RegisterBean registerBean = new RegisterBean(mobileNumber);

        return  mApiService.register("user/reg?code="+code,registerBean);

    }

}
