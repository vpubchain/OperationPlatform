package com.jiuling.operate.di.component;



import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.operate.di.module.HttpModule;
import com.jiuling.operate.di.module.OperateControlModule;
import com.jiuling.operate.di.module.OperateReChargeModule;
import com.jiuling.operate.ui.OperateControlActivity;
import com.jiuling.operate.ui.OperateRechargeActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/26.
 */

@FragmentScope
@Component(modules = {OperateReChargeModule.class,HttpModule.class},dependencies = ApplicationComponent.class)
public interface OperateRechargeComponent {

    void injectReChargeControlActivity(OperateRechargeActivity mainActivity);

}