package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.app.Application;

import com.mvvm.kien2111.mvvmapplication.MyApplication;

import dagger.Binds;
import dagger.Module;

/**
 * Created by WhoAmI on 21/01/2018.
 */
@Module(includes = ViewModelModule.class)
public abstract class AppModule {
    @Binds
    abstract Application provideContext(MyApplication application);


}
