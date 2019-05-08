package com.jiuling.operate.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jiuling.commonbusiness.AppConstants;
import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.util.LoggingInterceptor;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.data.http.LenientGsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/14.
 */

@Module
public class HttpModule {



    final String basic = Credentials.basic("vp", "vp");


    @Provides
    @FragmentScope
    public Retrofit provideRetrofit(){
//    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
//OkHttpClient okHttpClient

//        OkHttpClient httpClient = new OkHttpClient();
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
//        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//                .authenticator(new Authenticator() {
//                    @Nullable
//                    @Override
//                    public Request authenticate(@Nullable Route route, Response response) throws IOException {
//                        return response.request().newBuilder().header("Authorization", basic).build();
//                    }
//                }).build();
                .build();



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConstants.HOST)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(LenientGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);

        return builder.build();

    }



    @Provides
    @FragmentScope
    public ApiService provideApiService(Retrofit retrofit){

        return  retrofit.create(ApiService.class);
    }
}