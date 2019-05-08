package com.jiuling.operate.presenter;

import android.util.Log;
import android.widget.TextView;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.entity.BaseResponseDataNull;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateConfirmBuyActivityView;
import com.jiuling.operate.contract.IOperateNodeFragmentView;
import com.jiuling.operate.data.OperateConfirmBuyActivityModel;
import com.jiuling.operate.data.OperateNodeFragmentModel;
import com.jiuling.operate.entity.CapitalFlowInfoBean;
import com.jiuling.operate.entity.MasterNodeBean;
import com.jiuling.operate.entity.OrderInfoBean;
import com.jiuling.operate.entity.RenewNodeBean;
import com.jiuling.operate.entity.RequestCancelOrderBean;
import com.jiuling.operate.entity.RequestNodeBean;
import com.jiuling.operate.entity.RequestOrderBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateNodeFragmentPresenter extends BasePresenter<OperateNodeFragmentModel,IOperateNodeFragmentView> {



    public OperateNodeFragmentPresenter(OperateNodeFragmentModel mainActivityModel, IOperateNodeFragmentView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void getMasterNodeInfo(String authorization, RequestNodeBean requestNodeBean,boolean isShowDialog) {

        mModel.getMasterNodeInfo(authorization,requestNodeBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MasterNodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MasterNodeBean masterNodeBean) {

                        mView.getMasterNodeInfo(masterNodeBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    public void getUserOrderInfo(String authorization, RequestOrderBean requestNodeBean, boolean isShowDialog) {

        mModel.getUserOrderInfo(authorization,requestNodeBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<OrderInfoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<OrderInfoBean> orderInfoBean) {

                        mView.getUserOrderInfo(orderInfoBean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }




    public void getCapitalFlowInfo(String authorization,String url,int page,int limit,String serialNumber, boolean isShowDialog) {

        mModel.getCapitalFlowInfo(authorization,url,page,limit,serialNumber)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CapitalFlowInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CapitalFlowInfoBean capitalFlowInfoBean) {

                        mView.getCapitalFlowInfo(capitalFlowInfoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


        public void installTrusteeshipNode(String authorization,String url,boolean isShowDialog) {

//            mModel.installTrusteeshipNode(authorization,url)
//                    .compose(RxJavaHelper.<Boolean>flatResponse())
//                    .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
//                        @Override
//                        public void onNext(@NonNull Boolean installTrusteeshipNodeResult) {
//
//                            mView.installTrusteeshipNode(installTrusteeshipNodeResult);
//
//                        }
//                    });

            mModel.installTrusteeshipNode(authorization,url)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponseDataNull>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(BaseResponseDataNull msg) {

                            Log.i("zhuantai","onNext");
                            mView.installTrusteeshipNode(true);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("zhuantai","onError"+e.toString());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });


        }



    public void nodeRenew(String authorization,String url,boolean isShowDialog) {

        mModel.nodeRenew(authorization,url)
                .compose(RxJavaHelper.<RenewNodeBean>flatResponse())
                .subscribe(new ProgressSubcriber<RenewNodeBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull RenewNodeBean renewNodeBean) {

                        mView.nodeRenew(renewNodeBean);


                    }
                });

    }



    public void cancleOrder(String authorization, RequestCancelOrderBean requestCancelOrderBean, boolean isShowDialog) {


        mModel.cancleOrder(authorization,requestCancelOrderBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseDataNull>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponseDataNull msg) {

                        Log.i("zhuantai","onNext");
                        mView.cancleOrder(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("zhuantai","onError"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }


}
