package com.jiuling.commonbusiness.di.module;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jiuling.commonbusiness.AppConstants;
import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.http.ApiService;
import com.jiuling.commonbusiness.util.LoggingInterceptor;
import com.jiuling.commonbusiness.util.rx.RxErrorHandler;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class HttpModule {

    private Context context;

    public HttpModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public RxErrorHandler provideErrorHandlder() {
        return new RxErrorHandler(context);
    }



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
                .build();



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(AppConstants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
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
