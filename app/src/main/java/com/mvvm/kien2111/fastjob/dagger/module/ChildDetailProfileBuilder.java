package com.mvvm.kien2111.fastjob.dagger.module;

import com.mvvm.kien2111.fastjob.dagger.Scope.PerChildDetailProfile;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.about.AboutFragment;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.about.AboutModule;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.rate.ListRateFragment;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.rate.ListRateModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 04/04/2018.
 */
@Module
public abstract class ChildDetailProfileBuilder {
    @ContributesAndroidInjector(modules = {ListRateModule.class})
    @PerChildDetailProfile
    abstract ListRateFragment bindListRateFragment();

    @ContributesAndroidInjector(modules = {AboutModule.class})
    @PerChildDetailProfile
    abstract AboutFragment bindAboutFragment();
}
