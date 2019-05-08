package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperatePaymentActivityView;
import com.jiuling.operate.data.OperatePaymentActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperatePaymentActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperatePaymentModule {

    private IOperatePaymentActivityView iPaymentActivityView;

    public OperatePaymentModule(IOperatePaymentActivityView iPaymentActivityView){


        this.iPaymentActivityView = iPaymentActivityView;
    }

    @Provides
    @FragmentScope
    public OperatePaymentActivityModel privodeModel(ApiService apiService){

        return  new OperatePaymentActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperatePaymentActivityView privodeView(){

        return  iPaymentActivityView;
    }

    @Provides
    @FragmentScope
    public OperatePaymentActivityPresenter privodePresenter(OperatePaymentActivityModel confirmTransactionActivityModel, IOperatePaymentActivityView iConfirmTransationActivityView){

        return new OperatePaymentActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}