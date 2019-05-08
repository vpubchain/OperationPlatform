package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.data.OperateControlActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateControlActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateControlModule {

    private IOperateControlActivityView iControlActivityView;

    public OperateControlModule(IOperateControlActivityView iControlActivityView){


        this.iControlActivityView = iControlActivityView;
    }

    @Provides
    @FragmentScope
    public OperateControlActivityModel privodeModel(ApiService apiService){

        return  new OperateControlActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateControlActivityView privodeView(){

        return  iControlActivityView;
    }

    @Provides
    @FragmentScope
    public OperateControlActivityPresenter privodePresenter(OperateControlActivityModel confirmTransactionActivityModel, IOperateControlActivityView iConfirmTransationActivityView){

        return new OperateControlActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}