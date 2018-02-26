package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.Fragment;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchFragment_MembersInjector implements MembersInjector<SearchFragment> {
  private final Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public SearchFragment_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    assert childFragmentInjectorProvider != null;
    this.childFragmentInjectorProvider = childFragmentInjectorProvider;
    assert viewModelFactoryProvider != null;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<SearchFragment> create(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new SearchFragment_MembersInjector(
        childFragmentInjectorProvider, viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(SearchFragment instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    dagger.android.support.DaggerFragment_MembersInjector.injectChildFragmentInjector(
        instance, childFragmentInjectorProvider);
    com.mvvm.kien2111.mvvmapplication.base.BaseFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider);
  }
}
