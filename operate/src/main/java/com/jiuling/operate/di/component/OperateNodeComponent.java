package com.jiuling.operate.di.component;



import com.jiuling.commonbusiness.di.FragmentScope;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.operate.di.module.HttpModule;
import com.jiuling.operate.di.module.OperateConfirmBuyModule;
import com.jiuling.operate.di.module.OperateNodeModule;
import com.jiuling.operate.ui.OperateConfirmBuyActivity;
import com.jiuling.operate.ui.fragment.NodeFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/26.
 */

@FragmentScope
@Component(modules = {OperateNodeModule.class,HttpModule.class},dependencies = ApplicationComponent.class)
public interface OperateNodeComponent {

    void injectNodeFragment(NodeFragment mainActivity);

}