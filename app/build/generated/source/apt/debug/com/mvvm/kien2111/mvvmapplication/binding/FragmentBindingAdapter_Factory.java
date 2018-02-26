package com.mvvm.kien2111.mvvmapplication.binding;

import android.support.v4.app.Fragment;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class FragmentBindingAdapter_Factory implements Factory<FragmentBindingAdapter> {
  private final Provider<Fragment> fragmentProvider;

  public FragmentBindingAdapter_Factory(Provider<Fragment> fragmentProvider) {
    assert fragmentProvider != null;
    this.fragmentProvider = fragmentProvider;
  }

  @Override
  public FragmentBindingAdapter get() {
    return new FragmentBindingAdapter(fragmentProvider.get());
  }

  public static Factory<FragmentBindingAdapter> create(Provider<Fragment> fragmentProvider) {
    return new FragmentBindingAdapter_Factory(fragmentProvider);
  }
}
