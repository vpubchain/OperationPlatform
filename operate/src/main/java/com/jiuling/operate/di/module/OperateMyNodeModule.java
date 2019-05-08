package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateMyNodeActivityView;
import com.jiuling.operate.data.OperateMyNodeActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateMyNodeActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateMyNodeModule {

    private IOperateMyNodeActivityView iMyNodeActivityView;

    public OperateMyNodeModule(IOperateMyNodeActivityView iMyNodeActivityView){


        this.iMyNodeActivityView = iMyNodeActivityView;
    }

    @Provides
    @FragmentScope
    public OperateMyNodeActivityModel privodeModel(ApiService apiService){

        return  new OperateMyNodeActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateMyNodeActivityView privodeView(){

        return  iMyNodeActivityView;
    }

    @Provides
    @FragmentScope
    public OperateMyNodeActivityPresenter privodePresenter(OperateMyNodeActivityModel confirmTransactionActivityModel, IOperateMyNodeActivityView iConfirmTransationActivityView){

        return new OperateMyNodeActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}