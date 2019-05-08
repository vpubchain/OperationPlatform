package com.jiuling.operate.presenter;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.util.rx.RxJavaHelper;
import com.jiuling.commonbusiness.util.rx.subscriber.ProgressSubcriber;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.contract.IOperateInformationActivityView;
import com.jiuling.operate.data.OperateControlActivityModel;
import com.jiuling.operate.data.OperateInformationActivityModel;
import com.jiuling.operate.entity.PersonalInfoBean;
import com.jiuling.operate.entity.RequestChangePhoneBean;
import com.jiuling.operate.entity.SetUserInfoBean;
import com.jiuling.operate.entity.StatisticsIncomeBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chain-Cloud on 2018/4/19.
 */

public class OperateInformationActivityPresenter extends BasePresenter<OperateInformationActivityModel,IOperateInformationActivityView> {



    public OperateInformationActivityPresenter(OperateInformationActivityModel mainActivityModel, IOperateInformationActivityView iMainActivityView) {
        super(mainActivityModel, iMainActivityView);
    }


    public void getPersonalInfo(String authorization,boolean isShowDialog) {

        mModel.getPersonalInfo(authorization)
                .compose(RxJavaHelper.<PersonalInfoBean>flatResponse())
                .subscribe(new ProgressSubcriber<PersonalInfoBean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull PersonalInfoBean personalInfoBean) {

                        mView.getPersonalInfo(personalInfoBean);

//                        mView.getUserMasternodeIncomeDay(statisticsIncomeBean);

                    }
                });

    }


    public void getChangePhoneCode(String authorization,String phone,boolean isShowDialog) {

        mModel.getChangePhoneCode(authorization,phone)
                .compose(RxJavaHelper.<Boolean>flatResponse())
                .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull Boolean result) {

                        mView.getChangePhoneCode(result);

//                        mView.getUserMasternodeIncomeDay(statisticsIncomeBean);

                    }
                });

    }


    public void changePhone(String authorization, String code, RequestChangePhoneBean requestChangePhoneBean, boolean isShowDialog) {

        mModel.changePhone(authorization,code,requestChangePhoneBean)
                .compose(RxJavaHelper.<Boolean>flatResponse())
                .subscribe(new ProgressSubcriber<Boolean>(BaseApplication.getInstance(), mView,isShowDialog){
                    @Override
                    public void onNext(@NonNull Boolean result) {

                        mView.changePhone(result);

                    }
                });

    }


    public void setPersonalInfo(String authorization, SetUserInfoBean setUserInfoBean, boolean isShowDialog) {

        mModel.setPersonalInfo(authorization,setUserInfoBean)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<Boolean> booleanBaseResponse) {
                        mView.setPersonalInfo(booleanBaseResponse.getData());
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
