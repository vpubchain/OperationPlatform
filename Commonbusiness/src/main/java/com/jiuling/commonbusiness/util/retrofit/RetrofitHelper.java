package com.jiuling.commonbusiness.util.retrofit;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jiuling.commonbusiness.AppConstants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装Retrofit请求
 * Created by Rayman on 2017/10/14.
 */

public class RetrofitHelper {

    private static RetrofitHelper mInstance;
    private Context context;
    private Retrofit mRetrofit;
    private boolean isTokenRequest = true;
    private String HOST = AppConstants.HOST;

    /**
     * RetrofitHelper，默认都是带Token请求
     *
     * @param context
     */
    private RetrofitHelper(Context context) {
        this.context = context;
        initRetrofit();
    }

    /**
     * 判断是否需要添加token
     *
     * @param context
     * @param isTokenRequest
     */
    private RetrofitHelper(Context context, boolean isTokenRequest) {
        this.context = context;
        this.isTokenRequest = isTokenRequest;
        initRetrofit();
    }

    private RetrofitHelper(RetrofitHelper helper) {
        this.isTokenRequest = helper.isTokenRequest;
        this.HOST = helper.HOST;
    }

    public static RetrofitHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (RetrofitHelper.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitHelper(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 配置Retrofit信息
     */
    private void initRetrofit() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(isTokenRequest ? new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl.Builder authorizedUrlBuilder = request.url()
                                .newBuilder()
                                //添加统一参数 如手机唯一标识符,token等
                                .addQueryParameter("key1", "value1")
                                .addQueryParameter("key2", "value2");
                        Request newRequest = request.newBuilder()
                                //对所有请求添加请求头
                                .header("mobileFlag", "adfsaeefe").addHeader("type", "4")
                                .method(request.method(), request.body())
                                .url(authorizedUrlBuilder.build())
                                .build();
                        return chain.proceed(newRequest);
                    }
                } : null)
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }

    public static class Builder {

        private RetrofitHelper helper;
        private Context context;

        public Builder(Context context) {
            this.context = context;
            helper = new RetrofitHelper(context);
        }

        public Builder isTokenRequest(boolean isTokenRequest) {
            helper.isTokenRequest = isTokenRequest;
            return this;
        }

        public Builder setHost(String host) {
            helper.HOST = host;
            return this;
        }

        public RetrofitHelper build() {
            return new RetrofitHelper(helper);
        }
    }
}
