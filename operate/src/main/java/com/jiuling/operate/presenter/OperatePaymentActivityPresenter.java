package com.jiuling.operate.presenter;

import android.util.Log;
import android.widget.TextView;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperatePaymentActivityView;
import com.jiuling.operate.data.OperatePaymentActivityModel;
import com.jiuling.operate.entity.OrderInfoBean;
import com.jiuling.operate.entity.OrderNumberBean;
import com.jiuling.operate.entity.RequestAlipayBean;
import com.jiuling.operate.entity.RequestBalancePaymentBean;
import com.jiuling.operate.entity.RequestOrderNumberBean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperatePaymentActivityPresenter extends BasePresenter<OperatePaymentActivityModel,IOperatePaymentActivityView> {



    public OperatePaymentActivityPresenter(OperatePaymentActivityModel mainActivityModel, IOperatePaymentActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void getOrderNubmer(String authorization, RequestOrderNumberBean requestOrderNumberBean, boolean isShowDialog) {

        mModel.getOrderNubmer(authorization,requestOrderNumberBean)
                .compose(RxJavaHelper.<OrderNumberBean>flatResponse())
                .subscribe(new ProgressSubcriber<OrderNumberBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull OrderNumberBean orderNumberBean) {

                        mView.getOrderNubmer(orderNumberBean);

                    }
                });

    }


    public void getAlipayInfo(String authorization, RequestAlipayBean requestAlipayBean, boolean isShowDialog) {

        mModel.getAlipayInfo(authorization,requestAlipayBean)
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
                        mView.getAlipayInfo(BaseApplication.alipayInfo);
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



    public void balancePayment(String authorization, RequestBalancePaymentBean requestBalancePaymentBean, boolean isShowDialog) {

//        mModel.balancePayment(authorization,requestBalancePaymentBean)
//                .compose(RxJavaHelper.<String>flatResponse())
//                .subscribe(new ProgressSubcriber<String>(BaseApplication.getInstance(), mView,isShowDialog){
//                    @Override
//                    public void onNext(@NonNull String result) {
//
//                        mView.balancePaymentResult(result);
//
//                    }
//                });

        mModel.balancePayment(authorization,requestBalancePaymentBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> alipayInfo) {
//                        mView.getUserOrderInfo(orderInfoBean.getData());
                        if (alipayInfo.getSuccess()){
                            mView.balancePaymentResult("");
                        }else {
                            ToastUtils.showToast(alipayInfo.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });



    }



}
