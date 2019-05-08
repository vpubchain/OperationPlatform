package com.jiuling.operate.data.http;


import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.entity.BaseResponseDataNull;
import com.jiuling.operate.entity.CapitalFlowInfoBean;
import com.jiuling.operate.entity.ContactUsBean;
import com.jiuling.operate.entity.LoginResultBean;
import com.jiuling.operate.entity.MasterNodeBean;
import com.jiuling.operate.entity.NodeDetailsBean;
import com.jiuling.operate.entity.OperateMainBean;
import com.jiuling.operate.entity.OrderInfoBean;
import com.jiuling.operate.entity.OrderNumberBean;
import com.jiuling.operate.entity.PersonalInfoBean;
import com.jiuling.operate.entity.PriceBean;
import com.jiuling.operate.entity.RequestBalancePaymentBean;
import com.jiuling.operate.entity.RequestCancelOrderBean;
import com.jiuling.operate.entity.RequestChangePhoneBean;
import com.jiuling.operate.entity.RequestRechargeBean;
import com.jiuling.operate.entity.RegisterBean;
import com.jiuling.operate.entity.RenewNodeBean;
import com.jiuling.operate.entity.RequestAlipayBean;
import com.jiuling.operate.entity.RequestNodeBean;
import com.jiuling.operate.entity.RequestOrderBean;
import com.jiuling.operate.entity.RequestOrderNumberBean;
import com.jiuling.operate.entity.SetUserInfoBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/12/26.
 */
public interface ApiService {


    @GET
    Observable<BaseResponse<Boolean>> getLoginCode(@Url String url);
//    Observable<BaseResponse<LoginInfoBean>> getUserList(@Field("username") String username, @Field("password") String password);

//    smsCode/phone/change/18820785560
    @GET
    Observable<BaseResponse<Boolean>> getChangePhoneCode(@Header("Authorization") String authorization,@Url String url);


//    user/phone?code=6473
    @Headers("Content-Type:application/json")
    @PUT
    Observable<BaseResponse<Boolean>> changePhone(@Header("Authorization") String authorization,@Url String url, @Body RequestChangePhoneBean requestChangePhoneBean);



//    //获取eth代币 余额
//    @GET
//    Observable<BaseResponse<BalanceBean>> getEthTokenBalance(@Url String url, @Query("address") String address);

    @Headers({"Content-Type:application/x-www-form-urlencoded","Authorization:Basic YXBwOmFwcA=="})
    @FormUrlEncoded
    @POST("mobile/token")
    Observable<LoginResultBean> login(@Field("grant_type") String grant_type, @Field("scope") String scope, @Field("mobile") String mobile, @Field("code") String code);


    @GET
    Observable<BaseResponse<Boolean>> getRegisterCode(@Url String url);

    //注册接口
    @Headers("Content-Type:application/json")
    @POST
    Observable<BaseResponse<Boolean>> register(@Url String url,@Body RegisterBean registerBean);


    //https://mn.vpubchain.com/h5/api/bsMasternode/getUserMasternodeCount
    @GET("bsMasternode/getUserMasternodeCount")
    Observable<BaseResponse<UserMasternodeCountBean>> getUserMasternodeCount(@Header("Authorization") String authorization);



    //日收入统计
    @GET("bsMasternode/getUserMasternodeIncome/0")
    Observable<BaseResponse<StatisticsIncomeBean>> getUserMasternodeIncomeDay(@Header("Authorization") String authorization);


    //月收入统计
    @GET("bsMasternode/getUserMasternodeIncome/1")
    Observable<BaseResponse<StatisticsIncomeBean>> getUserMasternodeIncomeMonth(@Header("Authorization") String authorization);


    //节点价格
    @GET("bsProduct/detail")
    Observable<BaseResponse<List<PriceBean>>> getNodePrice(@Header("Authorization") String authorization);


    //获取我的个人信息
    @GET("user/info")
    Observable<BaseResponse<PersonalInfoBean>> getPersonalInfo(@Header("Authorization") String authorization);



    //更新用户信息
    @Headers("Content-Type:application/json")
    @PUT("user/info")
    Observable<BaseResponse<Boolean>> setPersonalInfo(@Header("Authorization") String authorization, @Body SetUserInfoBean setUserInfoBean);

    //获取我的节点相关信息
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("bsMasternode/masternodePage")
    Observable<MasterNodeBean> getMasterNodeInfo(@Header("Authorization") String authorization, @Body RequestNodeBean requestNodeBean);



    //联系我们
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("common/hostemail")
    Observable<BaseResponse<Boolean>> contactUs(@Header("Authorization") String authorization,@Body ContactUsBean contactUsBean);


    //获取我的订单相关信息
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("bsProductOrder/userOrdersPage")
    Observable<BaseResponse<OrderInfoBean>> getUserOrderInfo(@Header("Authorization") String authorization, @Body RequestOrderBean requestOrderBean);


    //获取资金流水相关信息
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET
    Observable<CapitalFlowInfoBean> getCapitalFlowInfo(@Header("Authorization") String authorization,@Url String url, @Query("page") int page,@Query("limit") int limit,@Query("serialNumber") String serialNumber);


    @GET("bsMasternode/getMasternodeCount")
    Observable<BaseResponse<OperateMainBean>> getMasternodeCount(@Header("Authorization") String authorization);


    //获取订单编号
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("bsProductOrder/create")
    Observable<BaseResponse<OrderNumberBean>> getOrderNubmer(@Header("Authorization") String authorization, @Body RequestOrderNumberBean requestOrderNumberBean);



    //根据商品id 获取详细产品信息
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET
    Observable<BaseResponse<NodeDetailsBean>> getNodeDetailById(@Header("Authorization") String authorization, @Url String url);




    //托管安装
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET
    Observable<BaseResponseDataNull> installTrusteeshipNode(@Header("Authorization") String authorization, @Url String url);


    //获取支付宝支付数据
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("alipay/app/pay")
    Observable<String> getAlipayInfo(@Header("Authorization") String authorization, @Body RequestAlipayBean requestAlipayBean);


    //节点续费
    //请求 URL: https://mn.vpubchain.com/api/bsProduct/allDtlsByMid/248
    @Headers("Content-Type:application/json;charset=UTF-8")
    @GET
    Observable<BaseResponse<RenewNodeBean>> nodeRenew(@Header("Authorization") String authorization, @Url String url);


    //获取充值数据
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("bsRecharge/app")
    Observable<String> recharge(@Header("Authorization") String authorization, @Body RequestRechargeBean requestRechargeBean);




    //余额支付
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("bsProductOrder/pay")
    Observable<BaseResponse<String>> balancePayment(@Header("Authorization") String authorization, @Body RequestBalancePaymentBean requestBalancePaymentBean);


    //取消订单
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("bsProductOrder/cancleOrder")
    Observable<BaseResponseDataNull> cancleOrder(@Header("Authorization") String authorization, @Body RequestCancelOrderBean requestCancelOrderBean);





//    //查询节点
//    @Headers("Content-Type:application/json;charset=UTF-8")
//    @POST("bsMasternode/masternodePage")
//    Observable<BaseResponse<Boolean>> searchNode(@Header("Authorization") String authorization,@Body ContactUsBean contactUsBean);



//    @Headers("Content-Type:application/json")
//    @POST("user/address")
//    Observable<BaseResponse<Boolean>> login(@Body RegisterBean registerBean);


//    //提交用户所有币接口
//    @Headers("Content-Type:application/json")
//    @POST("user/address")
//    Observable<BaseResponse<PostAllCoinResultBean>> postAllCoin(@Body PostAddressBean postAddressBean);
////    Observable<BaseResponse<PostAllCoinResultBean>> postAllCoin(@Field("userID") String userID, @Field("allCoin") String allCoin);
//


}