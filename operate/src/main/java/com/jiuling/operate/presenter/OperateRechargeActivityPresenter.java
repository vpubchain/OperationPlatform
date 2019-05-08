package com.jiuling.operate.presenter;

import android.util.Log;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.contract.IOperateRechargeActivityView;
import com.jiuling.operate.data.OperateControlActivityModel;
import com.jiuling.operate.data.OperateRechargeActivityModel;
import com.jiuling.operate.entity.RequestAlipayBean;
import com.jiuling.operate.entity.RequestRechargeBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateRechargeActivityPresenter extends BasePresenter<OperateRechargeActivityModel,IOperateRechargeActivityView> {



    public OperateRechargeActivityPresenter(OperateRechargeActivityModel mainActivityModel, IOperateRechargeActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void recharge(String authorization, RequestRechargeBean requestRechargeBean, boolean isShowDialog) {

        mModel.recharge(authorization,requestRechargeBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String alipayInfo) {
                        Log.i("body","获取支付宝结果onNext"+alipayInfo);
//                        mView.getUserOrderInfo(orderInfoBean.getData());
                        mView.getRechargeInfo(BaseApplication.alipayInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("body","获取支付宝结果onError"+e.getMessage()+"           "+e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }




}
