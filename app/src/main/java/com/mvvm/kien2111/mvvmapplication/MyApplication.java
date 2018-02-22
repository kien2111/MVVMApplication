package com.mvvm.kien2111.mvvmapplication;


import android.app.Activity;

import com.mvvm.kien2111.mvvmapplication.dagger.component.DaggerAppComponent;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by WhoAmI on 21/01/2018.
 */

public class MyApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends MyApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

}
