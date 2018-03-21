package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerChildFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.DetailCategoryFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateModule;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category.CategoryFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category.CategoryModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 11/02/2018.
 */
@PerChildFragment
@Module
public abstract class ChildFragmentBuilder {
    @PerChildFragment
    @ContributesAndroidInjector(modules = {CategoryModule.class})
    abstract CategoryFragment bindCategoryFragment();

    @PerChildFragment
    @ContributesAndroidInjector(modules = {HighRateModule.class})
    abstract HighRateFragment bindHighRateFragment();
}
