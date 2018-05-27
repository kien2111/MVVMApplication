package com.mvvm.kien2111.fastjob.dagger.module;

import com.mvvm.kien2111.fastjob.dagger.Scope.PerChildFeedFragment;
import com.mvvm.kien2111.fastjob.ui.universal.feed.category.CategoryFragment;
import com.mvvm.kien2111.fastjob.ui.universal.feed.category.CategoryModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 21/03/2018.
 */
@PerChildFeedFragment
@Module
public abstract class ChildFeedFragmentBuilder {
    @ContributesAndroidInjector(modules = {CategoryModule.class})
    abstract CategoryFragment bindCategoryFragment();
}
