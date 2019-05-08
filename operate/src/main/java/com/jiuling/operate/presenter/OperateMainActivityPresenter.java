package com.jiuling.operate.presenter;

import android.util.Log;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateMainActivityView;
import com.jiuling.operate.data.OperateMainActivityModel;
import com.jiuling.operate.entity.OperateMainBean;


import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateMainActivityPresenter extends BasePresenter<OperateMainActivityModel,IOperateMainActivityView> {



    public OperateMainActivityPresenter(OperateMainActivityModel mainActivityModel, IOperateMainActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }

    public void getMasternodeCount(String auth,boolean isShowDialog) {

        mModel.getMasternodeCount(auth)
                .compose(RxJavaHelper.<OperateMainBean>flatResponse())
                .subscribe(new ProgressSubcriber<OperateMainBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull OperateMainBean operateMainBean) {

                        mView.getMasternodeCount(operateMainBean);

                    }
                });

    }



}
