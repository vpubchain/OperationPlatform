package com.jiuling.operate.di.component;



import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.operate.di.module.HttpModule;
import com.jiuling.operate.di.module.OperateLoginModule;
import com.jiuling.operate.di.module.OperateRegisterModule;
import com.jiuling.operate.ui.OperateLoginActivity;
import com.jiuling.operate.ui.OperateRegisterActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/26.
 */

@FragmentScope
@Component(modules = {OperateRegisterModule.class,HttpModule.class},dependencies = ApplicationComponent.class)
public interface OperateRegisterComponent {

    void injectOperateRegisterActivity(OperateRegisterActivity mainActivity);

}