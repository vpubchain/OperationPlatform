package com.jiuling.operate.presenter;

import android.widget.TextView;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateConfirmBuyActivityView;
import com.jiuling.operate.contract.IOperateContactUsActivityView;
import com.jiuling.operate.data.OperateConfirmBuyActivityModel;
import com.jiuling.operate.data.OperateContactUsActivityModel;
import com.jiuling.operate.entity.NodeDetailsBean;
import com.jiuling.operate.entity.OrderNumberBean;
import com.jiuling.operate.entity.RequestOrderNumberBean;

import io.reactivex.annotations.NonNull;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateConfirmBuyActivityPresenter extends BasePresenter<OperateConfirmBuyActivityModel,IOperateConfirmBuyActivityView> {



    public OperateConfirmBuyActivityPresenter(OperateConfirmBuyActivityModel mainActivityModel, IOperateConfirmBuyActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void setBuyCount(TextView tvBuyCount, boolean isAdd) {

        int count = Integer.parseInt(tvBuyCount.getText().toString());
        if (count == 1 && !isAdd){

        }else if (isAdd){
            tvBuyCount.setText(count+1+"");
            mView.getBuyCount(count+1);
        }else {
            tvBuyCount.setText(count-1+"");
            mView.getBuyCount(count-1);
        }



    }


    public void getNodeDetailById(String authorization, String url, boolean isShowDialog) {

        mModel.getNodeDetailById(authorization,url)
                .compose(RxJavaHelper.<NodeDetailsBean>flatResponse())
                .subscribe(new ProgressSubcriber<NodeDetailsBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull NodeDetailsBean nodeDetailsBean) {

                        mView.getNodeDetailById(nodeDetailsBean);

                    }
                });

    }


}
