package com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile;

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
public final class FavouriteProfileFragment_MembersInjector
    implements MembersInjector<FavouriteProfileFragment> {
  private final Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public FavouriteProfileFragment_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    assert childFragmentInjectorProvider != null;
    this.childFragmentInjectorProvider = childFragmentInjectorProvider;
    assert viewModelFactoryProvider != null;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<FavouriteProfileFragment> create(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new FavouriteProfileFragment_MembersInjector(
        childFragmentInjectorProvider, viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(FavouriteProfileFragment instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    dagger.android.support.DaggerFragment_MembersInjector.injectChildFragmentInjector(
        instance, childFragmentInjectorProvider);
    com.mvvm.kien2111.mvvmapplication.base.BaseFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider);
  }
}
