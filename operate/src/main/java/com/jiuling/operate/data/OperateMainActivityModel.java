package com.jiuling.operate.data;


import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.OperateMainBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateMainActivityModel {

    private ApiService mApiService;

    public OperateMainActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }

    public Observable<BaseResponse<OperateMainBean>> getMasternodeCount(String auth){

        return  mApiService.getMasternodeCount(auth);
    }



}
