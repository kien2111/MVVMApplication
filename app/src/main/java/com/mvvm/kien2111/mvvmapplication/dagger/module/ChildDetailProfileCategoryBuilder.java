package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerChildDetailCategory;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerChildFeedFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 11/02/2018.
 */
@PerChildDetailCategory
@Module
public abstract class ChildDetailProfileCategoryBuilder {
    @PerChildDetailCategory
    @ContributesAndroidInjector(modules = {HighRateModule.class})
    abstract HighRateFragment bindHighRateFragment();
}
