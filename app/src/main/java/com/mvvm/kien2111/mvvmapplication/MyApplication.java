package com.mvvm.kien2111.mvvmapplication;


import android.app.Activity;
import android.app.Application;

import com.mvvm.kien2111.mvvmapplication.dagger.component.AppComponent;
import com.mvvm.kien2111.mvvmapplication.dagger.component.DaggerAppComponent;
import com.mvvm.kien2111.mvvmapplication.dagger.module.AppModule;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by WhoAmI on 21/01/2018.
 */

public class MyApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

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
}
