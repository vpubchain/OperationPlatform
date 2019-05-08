package com.jiuling.operate.di.module;

import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.operate.contract.IOperateLoginActivityView;
import com.jiuling.operate.data.OperateLoginActivityModel;
import com.jiuling.operate.data.http.ApiService;
import com.jiuling.operate.presenter.OperateLoginActivityPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class OperateLoginModule {

    private IOperateLoginActivityView iLoginActivityView;

    public OperateLoginModule(IOperateLoginActivityView iLoginActivityView){


        this.iLoginActivityView = iLoginActivityView;
    }

    @Provides
    @FragmentScope
    public OperateLoginActivityModel privodeModel(ApiService apiService){

        return  new OperateLoginActivityModel(apiService);
    }


    @Provides
    @FragmentScope
    public IOperateLoginActivityView privodeView(){

        return  iLoginActivityView;
    }

    @Provides
    @FragmentScope
    public OperateLoginActivityPresenter privodePresenter(OperateLoginActivityModel confirmTransactionActivityModel, IOperateLoginActivityView iConfirmTransationActivityView){

        return new OperateLoginActivityPresenter(confirmTransactionActivityModel,iConfirmTransationActivityView);
    }



}