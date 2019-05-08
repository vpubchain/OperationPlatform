package com.vpubchain.wallet.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit工具类
 */
public class RetrofitUtils2Eth {
//    public static final String BASE_URL = "http://api.etherscan.io/";
    public static final String BASE_URL = "http://api-ropsten.etherscan.io/";
    /**
     * 超时时间
     */
    public static final int TIMEOUT = 60;
    private static volatile RetrofitUtils2Eth mInstance;
    private Retrofit mRetrofit;

    public static RetrofitUtils2Eth getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtils2Eth.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils2Eth();
                }
            }
        }
        return mInstance;
    }

    private RetrofitUtils2Eth() {
        initRetrofit();
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new LoggingInterceptor());
        // 设置超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);


        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                // 设置请求的域名
                .baseUrl(BASE_URL)
                // 设置解析转换工厂，用自己定义的
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    /**
     * 创建API
     */
    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}
