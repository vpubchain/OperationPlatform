package com.vpubchain.wallet.util.rx;

import android.content.Context;
import android.util.Log;


import com.vpubchain.wallet.data.BaseResponse;
import com.vpubchain.wallet.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava+Retrofit请求帮助类
 * Created by Rayman on 2017/5/9.
 */

public class RxJavaHelper<T> {
    private static RxJavaHelper mInstance;
    private Context context;

    private RxJavaHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public static RxJavaHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (RxJavaHelper.class) {
                if (mInstance == null) {
                    mInstance = new RxJavaHelper(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public static <T> ObservableTransformer<BaseResponse<T>, T> flatResponse() {
        Log.i("testaaa","走不走");
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                Log.i("testaaa","走不走1");
                return (ObservableSource<T>) upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<BaseResponse<T>, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(BaseResponse<T> tBaseResponse) throws Exception {
                                Log.i("testaaa","走不走2");
                                if (tBaseResponse.isSuccess()) {
                                    Log.i("testaaa","走不走3"+tBaseResponse.getData());
                                    return createData(tBaseResponse.getData());
                                } else {
                                    Log.i("testaaa","走不走4"+tBaseResponse.getCode()+tBaseResponse.getMsg());
                                    int code = tBaseResponse.getCode();
                                    String msg = tBaseResponse.getMsg() == null ? "" : tBaseResponse.getMsg();
                                    Log.i("testaaa","抛出错误"+code+"           "+msg);
                                    return Observable.error(new ApiException(code, msg));
                                }
                            }
                        });
            }
        };
    }

    private static <T> ObservableSource<?> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(data);
                e.onComplete();
            }
        });
    }
}
