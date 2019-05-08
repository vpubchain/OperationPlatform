package com.jiuling.operate.di.component;



import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.operate.di.module.HttpModule;
import com.jiuling.operate.di.module.OperateMyNodeModule;
import com.jiuling.operate.ui.OperateMyNodeActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/26.
 */

@FragmentScope
@Component(modules = {OperateMyNodeModule.class,HttpModule.class},dependencies = ApplicationComponent.class)
public interface OperateMyNodeComponent {

    void injectOperateMyNodeActivity(OperateMyNodeActivity mainActivity);

}