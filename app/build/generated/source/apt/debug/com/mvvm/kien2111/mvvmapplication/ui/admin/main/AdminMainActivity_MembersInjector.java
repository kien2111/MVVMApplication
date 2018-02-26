package com.mvvm.kien2111.mvvmapplication.ui.admin.main;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.Fragment;
import com.mvvm.kien2111.mvvmapplication.MyApplication;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AdminMainActivity_MembersInjector implements MembersInjector<AdminMainActivity> {
  private final Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider;

  private final Provider<DispatchingAndroidInjector<android.app.Fragment>>
      frameworkFragmentInjectorProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  private final Provider<MyApplication> mApplicationProvider;

  public AdminMainActivity_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<android.app.Fragment>> frameworkFragmentInjectorProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider,
      Provider<MyApplication> mApplicationProvider) {
    assert supportFragmentInjectorProvider != null;
    this.supportFragmentInjectorProvider = supportFragmentInjectorProvider;
    assert frameworkFragmentInjectorProvider != null;
    this.frameworkFragmentInjectorProvider = frameworkFragmentInjectorProvider;
    assert viewModelFactoryProvider != null;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
    assert mApplicationProvider != null;
    this.mApplicationProvider = mApplicationProvider;
  }

  public static MembersInjector<AdminMainActivity> create(
      Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<android.app.Fragment>> frameworkFragmentInjectorProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider,
      Provider<MyApplication> mApplicationProvider) {
    return new AdminMainActivity_MembersInjector(
        supportFragmentInjectorProvider,
        frameworkFragmentInjectorProvider,
        viewModelFactoryProvider,
        mApplicationProvider);
  }

  @Override
  public void injectMembers(AdminMainActivity instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    dagger.android.support.DaggerAppCompatActivity_MembersInjector.injectSupportFragmentInjector(
        instance, supportFragmentInjectorProvider);
    dagger.android.support.DaggerAppCompatActivity_MembersInjector.injectFrameworkFragmentInjector(
        instance, frameworkFragmentInjectorProvider);
    com.mvvm.kien2111.mvvmapplication.base.BaseActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider);
    com.mvvm.kien2111.mvvmapplication.base.BaseActivity_MembersInjector.injectMApplication(
        instance, mApplicationProvider);
  }
}
