package com.mvvm.kien2111.fastjob.dagger.component;

import com.mvvm.kien2111.fastjob.MyApplication;
import com.mvvm.kien2111.fastjob.dagger.module.ActivityBuilder;
import com.mvvm.kien2111.fastjob.dagger.module.AppModule;
import com.mvvm.kien2111.fastjob.dagger.module.NetModule;
import com.mvvm.kien2111.fastjob.dagger.module.ServiceBuilder;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by WhoAmI on 21/01/2018.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        AppModule.class,
        ServiceBuilder.class,
        NetModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);
        Builder appModule(AppModule appModule); // add this

        AppComponent build();
    }

    void inject(MyApplication myApplication);
}
