package com.jiuling.operate.di.component;



import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.operate.di.module.HttpModule;
import com.jiuling.operate.di.module.OperateContactUsModule;
import com.jiuling.operate.di.module.OperatePriceModule;
import com.jiuling.operate.ui.OperateContactUsActivity;
import com.jiuling.operate.ui.OperatePriceActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/26.
 */

@FragmentScope
@Component(modules = {OperatePriceModule.class,HttpModule.class},dependencies = ApplicationComponent.class)
public interface OperatePriceComponent {

    void injectOperatePriceActivity(OperatePriceActivity mainActivity);

}