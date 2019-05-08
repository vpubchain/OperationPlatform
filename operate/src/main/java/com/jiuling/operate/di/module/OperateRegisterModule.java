package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateRegisterActivityView;
import com.jiuling.operate.contract.IOperateRegisterActivityView;
import com.jiuling.operate.data.OperateRegisterActivityModel;
import com.jiuling.operate.data.OperateRegisterActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateRegisterActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateRegisterModule {

    private IOperateRegisterActivityView iRegisterActivityView;

    public OperateRegisterModule(IOperateRegisterActivityView iRegisterActivityView){


        this.iRegisterActivityView = iRegisterActivityView;
    }

    @Provides
    @FragmentScope
    public OperateRegisterActivityModel privodeModel(ApiService apiService){

        return  new OperateRegisterActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateRegisterActivityView privodeView(){

        return  iRegisterActivityView;
    }

    @Provides
    @FragmentScope
    public OperateRegisterActivityPresenter privodePresenter(OperateRegisterActivityModel confirmTransactionActivityModel, IOperateRegisterActivityView iConfirmTransationActivityView){

        return new OperateRegisterActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}