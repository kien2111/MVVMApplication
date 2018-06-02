package com.mvvm.kien2111.fastjob.dagger.module;

import com.mvvm.kien2111.fastjob.dagger.Scope.PerChildUpgradeProfile;
import com.mvvm.kien2111.fastjob.ui.upgrade.common.BaseIndicatorFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 05/05/2018.
 */
@Module
public abstract class ChildUpgradeProfileBuilder {

    @PerChildUpgradeProfile
    @ContributesAndroidInjector
    abstract BaseIndicatorFragment bindBaseIndicatorFragment();
}
