package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateCapitalFlowActivityView;
import com.jiuling.operate.data.OperateCapitalFlowActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateCapitalFlowActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateCapitalFlowModule {

    private IOperateCapitalFlowActivityView iCapitalFlowActivityView;

    public OperateCapitalFlowModule(IOperateCapitalFlowActivityView iCapitalFlowActivityView){


        this.iCapitalFlowActivityView = iCapitalFlowActivityView;
    }

    @Provides
    @FragmentScope
    public OperateCapitalFlowActivityModel privodeModel(ApiService apiService){

        return  new OperateCapitalFlowActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateCapitalFlowActivityView privodeView(){

        return  iCapitalFlowActivityView;
    }

    @Provides
    @FragmentScope
    public OperateCapitalFlowActivityPresenter privodePresenter(OperateCapitalFlowActivityModel confirmTransactionActivityModel, IOperateCapitalFlowActivityView iConfirmTransationActivityView){

        return new OperateCapitalFlowActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}