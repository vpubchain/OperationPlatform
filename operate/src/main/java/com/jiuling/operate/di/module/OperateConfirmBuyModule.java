package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateConfirmBuyActivityView;
import com.jiuling.operate.contract.IOperateConfirmBuyActivityView;
import com.jiuling.operate.data.OperateConfirmBuyActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateConfirmBuyActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateConfirmBuyModule {

    private IOperateConfirmBuyActivityView iConfirmBuyActivityView;

    public OperateConfirmBuyModule(IOperateConfirmBuyActivityView iConfirmBuyActivityView){


        this.iConfirmBuyActivityView = iConfirmBuyActivityView;
    }

    @Provides
    @FragmentScope
    public OperateConfirmBuyActivityModel privodeModel(ApiService apiService){

        return  new OperateConfirmBuyActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateConfirmBuyActivityView privodeView(){

        return  iConfirmBuyActivityView;
    }

    @Provides
    @FragmentScope
    public OperateConfirmBuyActivityPresenter privodePresenter(OperateConfirmBuyActivityModel confirmTransactionActivityModel, IOperateConfirmBuyActivityView iConfirmTransationActivityView){

        return new OperateConfirmBuyActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}