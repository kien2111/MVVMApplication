package com.mvvm.kien2111.mvvmapplication.dagger.component;

import com.mvvm.kien2111.mvvmapplication.MyApplication;
import com.mvvm.kien2111.mvvmapplication.dagger.module.ActivityBuilder;
import com.mvvm.kien2111.mvvmapplication.dagger.module.AppModule;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule;
import javax.inject.Singleton;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by WhoAmI on 21/01/2018.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        AppModule.class,
        NetModule.class})
public interface AppComponent extends AndroidInjector<MyApplication>{
    @Component.Builder abstract class Builder extends AndroidInjector.Builder<MyApplication>{}
}
