package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerActivity;
import com.mvvm.kien2111.mvvmapplication.ui.SplashActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.profile.AdminManageProfileActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.profile.AdminManageProfileModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.statistical.AdminStatisticalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.statistical.AdminStatisticalModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.ManageUserActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.ManageUserModule;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginModule;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpActivity;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpModule;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 27/01/2018.
 */

@Module
public abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = SignUpModule.class)
    abstract SignUpActivity bindSignUpActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {UniversalModule.class,FragmentBuilder.class})
    abstract UniversalActivity bindUniversalActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AdminMainModule.class})
    abstract  AdminMainActivity bindAdminMainActivity();

    @PerActivity
    @ContributesAndroidInjector (modules = {AdminStatisticalModule.class})
    abstract AdminStatisticalActivity binAdminStatisticalActivity();

    @PerActivity
    @ContributesAndroidInjector (modules = {AdminManageProfileModule.class})
    abstract AdminManageProfileActivity binAdminManageProfileActivity();


    @PerActivity
    @ContributesAndroidInjector(modules = {ManageUserModule.class,FragmentManageUserBuilder.class})
    abstract ManageUserActivity binManageUserActivity();

    @PerActivity
    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();


}
