package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateContactUsActivityView;
import com.jiuling.operate.data.OperateContactUsActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateContactUsActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateContactUsModule {

    private IOperateContactUsActivityView iContactUsActivityView;

    public OperateContactUsModule(IOperateContactUsActivityView iContactUsActivityView){


        this.iContactUsActivityView = iContactUsActivityView;
    }

    @Provides
    @FragmentScope
    public OperateContactUsActivityModel privodeModel(ApiService apiService){

        return  new OperateContactUsActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateContactUsActivityView privodeView(){

        return  iContactUsActivityView;
    }

    @Provides
    @FragmentScope
    public OperateContactUsActivityPresenter privodePresenter(OperateContactUsActivityModel confirmTransactionActivityModel, IOperateContactUsActivityView iConfirmTransationActivityView){

        return new OperateContactUsActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}