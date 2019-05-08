package com.jiuling.commonbusiness.util;

import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.eventbus.FinishMainActivityEvent;
import com.jiuling.commonbusiness.eventbus.ShowToastMainActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/1/4.
 */
public class LoggingInterceptor implements Interceptor {



//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
//        Request request = chain.request();
//
//        Response response = chain.proceed(request);
//
//
//        //这里不能直接使用response.body().string()的方式输出日志
//        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
//        //个新的response给应用层处理
//        ResponseBody responseBody = response.peekBody(1024 * 1024);
//        Log.i("body",responseBody.string()+"bodyaaa");
//
//
//
//
//        int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
//        int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
//        if (isNetworkReachable(BaseApplication.getInstance())) {
//            request = request.newBuilder()
////                    .addHeader("apikey", "2ffc3e48c7086e0e1faa003d781c0e69")
//                    .cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
//                    .build();
//        } else {
//            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
//                    .build();
//        }
//        if (isNetworkReachable(BaseApplication.getInstance())) {
//            response = response.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, max-age=" + maxAge)
//                    .build();
//        } else {
//            response = response.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
//        return response;
//
//
//
//    }
//
//    /**
//     * 判断网络是否可用
//     *
//     * @param context Context对象
//     */
//    public static Boolean isNetworkReachable(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo current = cm.getActiveNetworkInfo();
//        if (current == null) {
//            return false;
//        }
//        return (current.isAvailable());
//
//
//    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        Log.i("body",request.toString());
        Log.i("body","header"+request.header("Content-Type"));

        long t1 = System.nanoTime();//请求发起的时间
//        logger.info(String.format("发送请求 %s on %s%n%s",
//                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
//        Log.i("body",response.body().string()+"bodyaaa");

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String responseBodyMsg = responseBody.string();
        if (responseBodyMsg.contains("alipay_sdk")){
            BaseApplication.alipayInfo = responseBodyMsg;
        }
        Log.i("body",responseBodyMsg+"bodyaaa");
//        logger.info(String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
//                response.request().url(),
//                responseBody.string(),
//                (t2 - t1) / 1e6d,
//                response.headers()));

        //删除token  重新登录

        if (responseBodyMsg.contains("invalid_token")){
//            ToastUtils.showToast("登录凭证过期，请重新登录");
            EventBus.getDefault().post(new ShowToastMainActivityEvent());
            ARouter.getInstance().build("/ModuleOperate/OperateLogin").withString("outTime","1").navigation();

//            Toast.makeText(BaseApplication.getInstance(),"登录凭证过期，请重新登录",Toast.LENGTH_LONG).show();
        }


        return response;
    }




}