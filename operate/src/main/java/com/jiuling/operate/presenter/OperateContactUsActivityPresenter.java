package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateContactUsActivityView;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.data.OperateContactUsActivityModel;
import com.jiuling.operate.data.OperateControlActivityModel;
import com.jiuling.operate.entity.ContactUsBean;
import com.jiuling.operate.entity.PriceBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateContactUsActivityPresenter extends BasePresenter<OperateContactUsActivityModel,IOperateContactUsActivityView> {



    public OperateContactUsActivityPresenter(OperateContactUsActivityModel mainActivityModel, IOperateContactUsActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }



    public void contactUs(String authorization,ContactUsBean contactUsBean,boolean isShowDialog) {

        mModel.contactUs(authorization,contactUsBean)
                .compose(RxJavaHelper.<Boolean>flatResponse())
                .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull Boolean result) {


                        mView.contactUs(result);

                    }
                });

    }










}
