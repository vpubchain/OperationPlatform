package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.base.recyclerbase.BaseRecyclerBean;
import com.jiuling.commonbusiness.util.MobileUtil;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateLoginActivityView;
import com.jiuling.operate.contract.IOperateRegisterActivityView;
import com.jiuling.operate.data.OperateLoginActivityModel;
import com.jiuling.operate.data.OperateRegisterActivityModel;

import io.reactivex.annotations.NonNull;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateRegisterActivityPresenter extends BasePresenter<OperateRegisterActivityModel,IOperateRegisterActivityView> {



    public OperateRegisterActivityPresenter(OperateRegisterActivityModel mainActivityModel, IOperateRegisterActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }

    public void getRegisterCode(String mobile,boolean isShowDialog) {

        mModel.getRegisterCode(mobile)
                .compose(RxJavaHelper.<Boolean>flatResponse())
                .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull Boolean btcTransationBeanList) {

                        mView.getRegisterCode();

                    }
                });

    }


    public void register(String code,String mobile,boolean isShowDialog) {

        mModel.register(mobile,code)
                .compose(RxJavaHelper.<Boolean>flatResponse())
                .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull Boolean result) {

                        mView.registerSuccess(result);

                    }
                });

    }


    public void checkInfo(String code,String phone) {

        if (phone.equals("")){
            mView.checkInfoResult(false,"手机号不能为空");
        }else if (!MobileUtil.isMobileNO(phone)){
            mView.checkInfoResult(false,"手机号格式不正确");
        }else if (code.equals("")){
            mView.checkInfoResult(false,"验证码不能为空");
        }else {
            mView.checkInfoResult(true,"");
        }

    }
}
