package com.jiuling.operate.data;


import android.util.Log;

import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.entity.OrderNumberBean;
import com.jiuling.operate.entity.PriceBean;
import com.jiuling.operate.entity.RequestAlipayBean;
import com.jiuling.operate.entity.RequestBalancePaymentBean;
import com.jiuling.operate.entity.RequestOrderNumberBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperatePaymentActivityModel {

    private ApiService mApiService;

    public OperatePaymentActivityModel(ApiService apiService){

        this.mApiService  =apiService;
    }


    public Observable<BaseResponse<OrderNumberBean>> getOrderNubmer(String authorization, RequestOrderNumberBean requestOrderNumberBean){

        Log.i("body","getOrderNumber"+requestOrderNumberBean.getPrice() +"          "+ requestOrderNumberBean.getQuantity() +"          "+ requestOrderNumberBean.getProduct_type_id());

        return  mApiService.getOrderNubmer(authorization,requestOrderNumberBean);
    }


    public Observable<String> getAlipayInfo(String authorization, RequestAlipayBean requestAlipayBean){
        Log.i("body","getAlipayInfo"+requestAlipayBean.getOut_trade_no());
        return mApiService.getAlipayInfo(authorization,requestAlipayBean);
    }


    public Observable<BaseResponse<String>> balancePayment(String authorization, RequestBalancePaymentBean requestBalancePaymentBean){
        return mApiService.balancePayment(authorization,requestBalancePaymentBean);
    }


}
