package com.jiuling.operate.presenter;

import android.util.Log;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.base.recyclerbase.BaseRecyclerBean;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateLoginActivityView;
import com.jiuling.operate.contract.IOperateMainActivityView;
import com.jiuling.operate.data.OperateLoginActivityModel;
import com.jiuling.operate.data.OperateMainActivityModel;
import com.jiuling.operate.entity.LoginResultBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateLoginActivityPresenter extends BasePresenter<OperateLoginActivityModel,IOperateLoginActivityView> {

    private SharedPreferencesUtils sharedPreferencesUtils;


    public OperateLoginActivityPresenter(OperateLoginActivityModel mainActivityModel, IOperateLoginActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void getLoginCode(String mobile,boolean isShowDialog) {

        mModel.getLoginCode(mobile)
                .compose(RxJavaHelper.<Boolean>flatResponse())
                .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull Boolean success) {

                        mView.getLoginCode();

                    }
                });

    }

    public void login(String mobile,String code,boolean isShowDialog) {

        mModel.login(mobile,code)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.i("body","onSubscribe");
                    }

                    @Override
                    public void onNext(LoginResultBean loginResultBean) {

                        Log.i("body","onNext");
                        sharedPreferencesUtils = new SharedPreferencesUtils(BaseApplication.getInstance(),SharedPreferencesUtils.LOGIN);
                        sharedPreferencesUtils.putInfo(SharedPreferencesUtils.ACCEE_TOKEN,loginResultBean.getAccess_token());
                        sharedPreferencesUtils.putInfo(SharedPreferencesUtils.REFRESH_TOKEN,loginResultBean.getRefresh_token());
                        sharedPreferencesUtils.putInfo(SharedPreferencesUtils.PHONE,loginResultBean.getPhone());
                        //过期时间
                        long currentime = System.currentTimeMillis();
                        long expires = loginResultBean.getExpires_in();
                        long expires_in = currentime + expires*1000;
                        sharedPreferencesUtils.putInfo(SharedPreferencesUtils.EXPIRATION,expires_in+"");

                        mView.loginSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("body","onError"+e.toString()+"         "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("body","onComplete");
                    }
                });

//        mModel.login(mobile,code)
//                .compose(RxJavaHelper.<Boolean>flatResponse())
//                .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
//                    @Override
//                    public void onNext(@NonNull Boolean success) {
//
//                        mView.loginSuccess();
//
//                    }
//                });

    }










}
