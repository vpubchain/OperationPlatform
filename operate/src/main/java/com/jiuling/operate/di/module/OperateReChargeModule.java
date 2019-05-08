package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateRechargeActivityView;
import com.jiuling.operate.data.OperateRechargeActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateRechargeActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateReChargeModule {

    private IOperateRechargeActivityView iRechargeActivityView;

    public OperateReChargeModule(IOperateRechargeActivityView iRechargeActivityView){


        this.iRechargeActivityView = iRechargeActivityView;
    }

    @Provides
    @FragmentScope
    public OperateRechargeActivityModel privodeModel(ApiService apiService){

        return  new OperateRechargeActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateRechargeActivityView privodeView(){

        return  iRechargeActivityView;
    }

    @Provides
    @FragmentScope
    public OperateRechargeActivityPresenter privodePresenter(OperateRechargeActivityModel confirmTransactionActivityModel, IOperateRechargeActivityView iConfirmTransationActivityView){

        return new OperateRechargeActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}