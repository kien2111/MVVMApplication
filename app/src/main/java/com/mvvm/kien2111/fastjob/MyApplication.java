package com.mvvm.kien2111.fastjob;


import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.mvvm.kien2111.fastjob.dagger.component.DaggerAppComponent;
import com.mvvm.kien2111.fastjob.dagger.module.AppModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import timber.log.Timber;

/**
 * Created by WhoAmI on 21/01/2018.
 */

public class MyApplication extends Application implements HasActivityInjector,HasServiceInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule())
                .build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }
}
