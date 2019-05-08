package com.jiuling.operate.data;


import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.entity.BaseResponseDataNull;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.CapitalFlowInfoBean;
import com.jiuling.operate.entity.MasterNodeBean;
import com.jiuling.operate.entity.OrderInfoBean;
import com.jiuling.operate.entity.RenewNodeBean;
import com.jiuling.operate.entity.RequestCancelOrderBean;
import com.jiuling.operate.entity.RequestNodeBean;
import com.jiuling.operate.entity.RequestOrderBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateNodeFragmentModel {

    private ApiService mApiService;

    public OperateNodeFragmentModel(ApiService apiService){

        this.mApiService  =apiService;
    }

    public Observable<MasterNodeBean> getMasterNodeInfo(String authorization, RequestNodeBean requestNodeBean){


        return  mApiService.getMasterNodeInfo(authorization,requestNodeBean);
    }


    public Observable<BaseResponse<OrderInfoBean>> getUserOrderInfo(String authorization, RequestOrderBean requestOrderBean){

        return  mApiService.getUserOrderInfo(authorization,requestOrderBean);
    }


    public Observable<CapitalFlowInfoBean> getCapitalFlowInfo(String authorization,String url,int page,int limit,String serialNumber){
        return  mApiService.getCapitalFlowInfo(authorization,url,page,limit,serialNumber);
    }



    public Observable<BaseResponseDataNull> installTrusteeshipNode(String authorization, String url){
        return  mApiService.installTrusteeshipNode(authorization,url);
    }


    public Observable<BaseResponse<RenewNodeBean>> nodeRenew(String authorization, String url){
        return  mApiService.nodeRenew(authorization,url);
    }


    public Observable<BaseResponseDataNull> cancleOrder(String authorization, RequestCancelOrderBean requestCancelOrderBean){
        return  mApiService.cancleOrder(authorization,requestCancelOrderBean);
    }
}
