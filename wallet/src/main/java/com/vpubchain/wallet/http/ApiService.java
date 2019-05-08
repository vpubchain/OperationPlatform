package com.vpubchain.wallet.http;


import com.vpubchain.wallet.data.BaseResponse;
import com.vpubchain.wallet.data.EthResponse;
import com.vpubchain.wallet.data.bean.EthTransactionBean;

import java.util.List;

import retrofit2.http.Field;
import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/12/26.
 */
public interface ApiService {

    @GET
    Observable<BaseResponse> getLoginCode(@Url String url);
//    Observable<BaseResponse<LoginInfoBean>> getUserList(@Field("username") String username, @Field("password") String password);

//    //获取eth代币 余额
//    @GET
//    Observable<BaseResponse<BalanceBean>> getEthTokenBalance(@Url String url, @Query("address") String address);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("mobile/token")
    Observable<BaseResponse> login(@Field("grant_type") String grant_type,@Field("scope") String scope,@Field("code") String code,@Field("mobile") String mobile);




    //"http://api.etherscan.io/api?module=account&action=txlist&address=0x7bB60e19Bb128d08f251038F3D3C2A7d481d914F"
    //获取eth transaction
//    @GET("api?module=account&action=txlist&startblock=7357905&endblock=7357906&sort=asc")
    @GET("api?module=account&action=txlist&sort=asc")
    Observable<EthResponse> getEthTransaction(@Query("address") String address);



}