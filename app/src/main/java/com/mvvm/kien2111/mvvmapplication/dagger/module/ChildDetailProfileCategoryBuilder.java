package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerChildDetailCategory;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateModule;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.skilledfreelancer.SkilledFreelancerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.skilledfreelancer.SkilledFreelancerModule;

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

    @PerChildDetailCategory
    @ContributesAndroidInjector(modules = {SkilledFreelancerModule.class})
    abstract SkilledFreelancerFragment bindSkillFreelancerFragment();
}
