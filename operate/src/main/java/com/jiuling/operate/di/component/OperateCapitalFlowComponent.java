package com.jiuling.operate.di.component;



import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.operate.di.module.HttpModule;
import com.jiuling.operate.di.module.OperateCapitalFlowModule;
import com.jiuling.operate.ui.OperateCapitalFlowActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/26.
 */

@FragmentScope
@Component(modules = {OperateCapitalFlowModule.class,HttpModule.class},dependencies = ApplicationComponent.class)
public interface OperateCapitalFlowComponent {

    void injectOperateCapitalFlowActivity(OperateCapitalFlowActivity mainActivity);

}