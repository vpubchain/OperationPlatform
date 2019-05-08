package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateMyOrderActivityView;
import com.jiuling.operate.data.OperateMyOrderActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateMyOrderActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateMyOrderModule {

    private IOperateMyOrderActivityView iMyOrderActivityView;

    public OperateMyOrderModule(IOperateMyOrderActivityView iMyOrderActivityView){


        this.iMyOrderActivityView = iMyOrderActivityView;
    }

    @Provides
    @FragmentScope
    public OperateMyOrderActivityModel privodeModel(ApiService apiService){

        return  new OperateMyOrderActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateMyOrderActivityView privodeView(){

        return  iMyOrderActivityView;
    }

    @Provides
    @FragmentScope
    public OperateMyOrderActivityPresenter privodePresenter(OperateMyOrderActivityModel confirmTransactionActivityModel, IOperateMyOrderActivityView iConfirmTransationActivityView){

        return new OperateMyOrderActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}