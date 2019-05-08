package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateMainActivityView;
import com.jiuling.operate.data.OperateMainActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateMainActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateMainModule {

    private IOperateMainActivityView iMainActivityView;

    public OperateMainModule(IOperateMainActivityView iMainActivityView){


        this.iMainActivityView = iMainActivityView;
    }

    @Provides
    @FragmentScope
    public OperateMainActivityModel privodeModel(ApiService apiService){

        return  new OperateMainActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateMainActivityView privodeView(){

        return  iMainActivityView;
    }

    @Provides
    @FragmentScope
    public OperateMainActivityPresenter privodePresenter(OperateMainActivityModel confirmTransactionActivityModel, IOperateMainActivityView iConfirmTransationActivityView){

        return new OperateMainActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}