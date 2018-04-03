package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.authenticate.AuthenticatorService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 31/03/2018.
 */
@Module
public abstract class ServiceBuilder {
    @ContributesAndroidInjector
    abstract AuthenticatorService bindAuthenticatorService();
}
