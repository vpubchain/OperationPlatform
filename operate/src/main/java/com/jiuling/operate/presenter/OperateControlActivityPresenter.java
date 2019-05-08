package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.contract.IOperateMainActivityView;
import com.jiuling.operate.data.OperateControlActivityModel;
import com.jiuling.operate.data.OperateMainActivityModel;
import com.jiuling.operate.entity.MasterNodeBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import io.reactivex.annotations.NonNull;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateControlActivityPresenter extends BasePresenter<OperateControlActivityModel,IOperateControlActivityView> {



    public OperateControlActivityPresenter(OperateControlActivityModel mainActivityModel, IOperateControlActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void getUserMasternodeCount(String authorization,boolean isShowDialog) {

        mModel.getUserMasternodeCount(authorization)
                .compose(RxJavaHelper.<UserMasternodeCountBean>flatResponse())
                .subscribe(new ProgressSubcriber<UserMasternodeCountBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull UserMasternodeCountBean userMasternodeCountBean) {

                        mView.getUserMasternodeCountBean(userMasternodeCountBean);

                    }
                });

    }

    public void getUserMasternodeIncomeDay(String authorization,boolean isShowDialog) {

        mModel.getUserMasternodeIncomeDay(authorization)
                .compose(RxJavaHelper.<StatisticsIncomeBean>flatResponse())
                .subscribe(new ProgressSubcriber<StatisticsIncomeBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull StatisticsIncomeBean statisticsIncomeBean) {

                        mView.getUserMasternodeIncomeDay(statisticsIncomeBean,true);

                    }
                });

    }


    public void getUserMasternodeIncomeMonth(String authorization,boolean isShowDialog) {

        mModel.getUserMasternodeIncomeMonth(authorization)
                .compose(RxJavaHelper.<StatisticsIncomeBean>flatResponse())
                .subscribe(new ProgressSubcriber<StatisticsIncomeBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull StatisticsIncomeBean statisticsIncomeBean) {

                        mView.getUserMasternodeIncomeDay(statisticsIncomeBean,false);

                    }
                });

    }



}
