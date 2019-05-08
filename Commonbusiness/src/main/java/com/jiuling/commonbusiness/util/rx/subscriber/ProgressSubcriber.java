package com.jiuling.commonbusiness.util.rx.subscriber;

import android.content.Context;
import android.util.Log;


import com.jiuling.commonbusiness.contract.IBasicContract;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public abstract class ProgressSubcriber<T> extends ErrorHandlerSubscriber<T> {

    private IBasicContract.IViewImpl mView;
    private boolean isShowProgress=true;

    public ProgressSubcriber(Context context, IBasicContract.IViewImpl view) {
        super(context);
        this.mView = view;
    }

    public ProgressSubcriber(Context context, IBasicContract.IViewImpl view,boolean isShowProgress) {
        super(context);
        this.mView = view;
        this.isShowProgress=isShowProgress;
    }


    public boolean isShowProgress() {
        return isShowProgress;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.i("ttttttttt","onSubscribe");
        if (isShowProgress()) {
            if (mView!=null){
                mView.showLoading();
            }
        }
    }


    @Override
    public void onComplete() {
        Log.i("ttttttttt","onComplete");
        if (mView!=null){
            mView.dismissLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (mView!=null){
            mView.dismissLoading();
        }
    }

    @Override
    protected void requestTimeOut() {
        super.requestTimeOut();

        if (mView!=null){
            mView.requestTimeOut();
        }

    }
}
