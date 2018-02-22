package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerActivity;
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
}
