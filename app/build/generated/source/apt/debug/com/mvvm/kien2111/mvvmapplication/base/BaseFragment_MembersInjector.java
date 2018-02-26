package com.mvvm.kien2111.mvvmapplication.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseFragment_MembersInjector<VM extends ViewModel, VB extends ViewDataBinding>
    implements MembersInjector<BaseFragment<VM, VB>> {
  private final Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public BaseFragment_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    assert childFragmentInjectorProvider != null;
    this.childFragmentInjectorProvider = childFragmentInjectorProvider;
    assert viewModelFactoryProvider != null;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static <VM extends ViewModel, VB extends ViewDataBinding>
      MembersInjector<BaseFragment<VM, VB>> create(
          Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
          Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new BaseFragment_MembersInjector<VM, VB>(
        childFragmentInjectorProvider, viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(BaseFragment<VM, VB> instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    dagger.android.support.DaggerFragment_MembersInjector.injectChildFragmentInjector(
        instance, childFragmentInjectorProvider);
    instance.viewModelFactory = viewModelFactoryProvider.get();
  }

  public static <VM extends ViewModel, VB extends ViewDataBinding> void injectViewModelFactory(
      BaseFragment<VM, VB> instance, Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    instance.viewModelFactory = viewModelFactoryProvider.get();
  }
}
