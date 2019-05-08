package com.jiuling.commonbusiness.di.component;

import android.content.Context;


import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.di.module.ApplicationModule;
import com.jiuling.commonbusiness.di.module.HttpModule;
import com.jiuling.commonbusiness.util.rx.RxErrorHandler;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Rayman on 2017/10/11.
 */

@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {

    BaseApplication getApplication();

    Context getContext();

    RxErrorHandler getRxErrorHandler();
}
