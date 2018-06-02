package com.mvvm.kien2111.fastjob.dagger.module;

import com.mvvm.kien2111.fastjob.dagger.Scope.PerChildUserProfile;
import com.mvvm.kien2111.fastjob.ui.userprofile.bussiness.BussinessProfileFragment;
import com.mvvm.kien2111.fastjob.ui.userprofile.bussiness.BussinessProfileModule;
import com.mvvm.kien2111.fastjob.ui.userprofile.invidual.IndividualProfileFragment;
import com.mvvm.kien2111.fastjob.ui.userprofile.invidual.IndividualProfileModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 21/04/2018.
 */
@Module
public abstract class ChildUserProfileBuilder {
    @PerChildUserProfile
    @ContributesAndroidInjector(modules = {IndividualProfileModule.class})
    abstract IndividualProfileFragment bindIndividualProfileFragment();

    @PerChildUserProfile
    @ContributesAndroidInjector(modules = {BussinessProfileModule.class})
    abstract BussinessProfileFragment bindBussinessProfileFragment();
}
