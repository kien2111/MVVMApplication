package com.mvvm.kien2111.mvvmapplication.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ViewDataBinding;
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
public final class BaseActivity_MembersInjector<VM extends ViewModel, VB extends ViewDataBinding>
    implements MembersInjector<BaseActivity<VM, VB>> {
  private final Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider;

  private final Provider<DispatchingAndroidInjector<android.app.Fragment>>
      frameworkFragmentInjectorProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  private final Provider<MyApplication> mApplicationProvider;

  public BaseActivity_MembersInjector(
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

  public static <VM extends ViewModel, VB extends ViewDataBinding>
      MembersInjector<BaseActivity<VM, VB>> create(
          Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider,
          Provider<DispatchingAndroidInjector<android.app.Fragment>>
              frameworkFragmentInjectorProvider,
          Provider<ViewModelProvider.Factory> viewModelFactoryProvider,
          Provider<MyApplication> mApplicationProvider) {
    return new BaseActivity_MembersInjector<VM, VB>(
        supportFragmentInjectorProvider,
        frameworkFragmentInjectorProvider,
        viewModelFactoryProvider,
        mApplicationProvider);
  }

  @Override
  public void injectMembers(BaseActivity<VM, VB> instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    dagger.android.support.DaggerAppCompatActivity_MembersInjector.injectSupportFragmentInjector(
        instance, supportFragmentInjectorProvider);
    dagger.android.support.DaggerAppCompatActivity_MembersInjector.injectFrameworkFragmentInjector(
        instance, frameworkFragmentInjectorProvider);
    instance.viewModelFactory = viewModelFactoryProvider.get();
    instance.mApplication = mApplicationProvider.get();
  }

  public static <VM extends ViewModel, VB extends ViewDataBinding> void injectViewModelFactory(
      BaseActivity<VM, VB> instance, Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    instance.viewModelFactory = viewModelFactoryProvider.get();
  }

  public static <VM extends ViewModel, VB extends ViewDataBinding> void injectMApplication(
      BaseActivity<VM, VB> instance, Provider<MyApplication> mApplicationProvider) {
    instance.mApplication = mApplicationProvider.get();
  }
}
