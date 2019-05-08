package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperatePriceActivityView;
import com.jiuling.operate.data.OperatePriceActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperatePriceActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperatePriceModule {

    private IOperatePriceActivityView iPriceActivityView;

    public OperatePriceModule(IOperatePriceActivityView iPriceActivityView){


        this.iPriceActivityView = iPriceActivityView;
    }

    @Provides
    @FragmentScope
    public OperatePriceActivityModel privodeModel(ApiService apiService){

        return  new OperatePriceActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperatePriceActivityView privodeView(){

        return  iPriceActivityView;
    }

    @Provides
    @FragmentScope
    public OperatePriceActivityPresenter privodePresenter(OperatePriceActivityModel confirmTransactionActivityModel, IOperatePriceActivityView iConfirmTransationActivityView){

        return new OperatePriceActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}