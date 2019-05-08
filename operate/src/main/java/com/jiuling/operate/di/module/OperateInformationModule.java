package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateInformationActivityView;
import com.jiuling.operate.data.OperateInformationActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateInformationActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateInformationModule {

    private IOperateInformationActivityView iInformationActivityView;

    public OperateInformationModule(IOperateInformationActivityView iInformationActivityView){


        this.iInformationActivityView = iInformationActivityView;
    }

    @Provides
    @FragmentScope
    public OperateInformationActivityModel privodeModel(ApiService apiService){

        return  new OperateInformationActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateInformationActivityView privodeView(){

        return  iInformationActivityView;
    }

    @Provides
    @FragmentScope
    public OperateInformationActivityPresenter privodePresenter(OperateInformationActivityModel confirmTransactionActivityModel, IOperateInformationActivityView iConfirmTransationActivityView){

        return new OperateInformationActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}