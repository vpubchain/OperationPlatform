package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateNodeFragmentView;
import com.jiuling.operate.data.OperateNodeFragmentModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateNodeFragmentPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateNodeModule {

    private IOperateNodeFragmentView iNodeFragmentView;

    public OperateNodeModule(IOperateNodeFragmentView iNodeFragmentView){


        this.iNodeFragmentView = iNodeFragmentView;
    }

    @Provides
    @FragmentScope
    public OperateNodeFragmentModel privodeModel(ApiService apiService){

        return  new OperateNodeFragmentModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateNodeFragmentView privodeView(){

        return  iNodeFragmentView;
    }

    @Provides
    @FragmentScope
    public OperateNodeFragmentPresenter privodePresenter(OperateNodeFragmentModel confirmTransactionActivityModel, IOperateNodeFragmentView iConfirmTransationActivityView){

        return new OperateNodeFragmentPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}