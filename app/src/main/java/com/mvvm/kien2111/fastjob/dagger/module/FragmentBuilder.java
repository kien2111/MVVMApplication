package com.mvvm.kien2111.fastjob.dagger.module;

import com.mvvm.kien2111.fastjob.dagger.Scope.PerFragment;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.BottomSheetDialogFilter;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.DetailCategoryFragment;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.DetailCategoryModule;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.DetailProfileFragment;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.DetailProfileModule;
import com.mvvm.kien2111.fastjob.ui.universal.feed.FeedFragment;
import com.mvvm.kien2111.fastjob.ui.universal.feed.FeedModule;
import com.mvvm.kien2111.fastjob.ui.universal.search.SearchFragment;
import com.mvvm.kien2111.fastjob.ui.universal.search.SearchModule;
import com.mvvm.kien2111.fastjob.ui.universal.user.UserFragment;
import com.mvvm.kien2111.fastjob.ui.universal.user.UserModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 05/02/2018.
 */

@Module
public abstract class FragmentBuilder {
    @PerFragment
    @ContributesAndroidInjector(modules = {UserModule.class})
    abstract UserFragment bindUserFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {SearchModule.class})
    abstract SearchFragment bindSearchFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {FeedModule.class,ChildFeedFragmentBuilder.class})
    abstract FeedFragment bindFeedFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {DetailCategoryModule.class})
    abstract DetailCategoryFragment bindDetailCategoryFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {DetailProfileModule.class,ChildDetailProfileBuilder.class})
    abstract DetailProfileFragment bindDetailProfileFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract BottomSheetDialogFilter bindBottomSheetDialogFilter();



}
