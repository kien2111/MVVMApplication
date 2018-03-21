package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser.AllUserFragment;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser.AllUserModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.upgradeuser.UngradeUserFragment;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.upgradeuser.UngradeUserModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by donki on 3/7/2018.
 */
@Module
public abstract class FragmentManageUserBuilder {
    @PerFragment
    @ContributesAndroidInjector(modules = {AllUserModule.class})
    abstract AllUserFragment binAllUserFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {UngradeUserModule.class})
    abstract UngradeUserFragment binUngradeUserFragment();
}
