package com.jiuling.operate.di.component;



import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.operate.di.module.HttpModule;
import com.jiuling.operate.di.module.OperateControlModule;
import com.jiuling.operate.di.module.OperateInformationModule;
import com.jiuling.operate.ui.OperateControlActivity;
import com.jiuling.operate.ui.OperateInformationActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/26.
 */

@FragmentScope
@Component(modules = {OperateInformationModule.class,HttpModule.class},dependencies = ApplicationComponent.class)
public interface OperateInformationComponent {

    void injectOperateInformationActivity(OperateInformationActivity mainActivity);

}