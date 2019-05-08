package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateContactUsActivityView;
import com.jiuling.operate.contract.IOperatePriceActivityView;
import com.jiuling.operate.data.OperateContactUsActivityModel;
import com.jiuling.operate.data.OperatePriceActivityModel;
import com.jiuling.operate.entity.PriceBean;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperatePriceActivityPresenter extends BasePresenter<OperatePriceActivityModel,IOperatePriceActivityView> {



    public OperatePriceActivityPresenter(OperatePriceActivityModel mainActivityModel, IOperatePriceActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void getNodePrice(String authorization,boolean isShowDialog) {

        mModel.getNodePrice(authorization)
                .compose(RxJavaHelper.<List<PriceBean>>flatResponse())
                .subscribe(new ProgressSubcriber<List<PriceBean>>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull List<PriceBean> priceBeans) {

                        mView.getNodePrice(priceBeans);
//                        mView.getLoginCode();

                    }
                });

    }





}
