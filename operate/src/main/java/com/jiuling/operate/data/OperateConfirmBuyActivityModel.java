package com.jiuling.operate.data;


import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.NodeDetailsBean;
import com.jiuling.operate.entity.OrderNumberBean;
import com.jiuling.operate.entity.RequestOrderNumberBean;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateConfirmBuyActivityModel {

    private ApiService mApiService;

    public OperateConfirmBuyActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }


    public Observable<BaseResponse<NodeDetailsBean>> getNodeDetailById(String authorization, String url){


        return  mApiService.getNodeDetailById(authorization,url);
    }

}
