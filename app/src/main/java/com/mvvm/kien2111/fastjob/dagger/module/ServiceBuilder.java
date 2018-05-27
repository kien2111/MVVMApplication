package com.mvvm.kien2111.fastjob.dagger.module;

import com.mvvm.kien2111.fastjob.authenticate.AuthenticatorService;

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
