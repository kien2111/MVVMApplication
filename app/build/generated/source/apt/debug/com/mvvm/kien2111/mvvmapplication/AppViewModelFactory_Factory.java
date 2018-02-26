package com.mvvm.kien2111.mvvmapplication;

import android.arch.lifecycle.ViewModel;
import dagger.internal.Factory;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppViewModelFactory_Factory implements Factory<AppViewModelFactory> {
  private final Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider;

  public AppViewModelFactory_Factory(
      Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider) {
    assert creatorsProvider != null;
    this.creatorsProvider = creatorsProvider;
  }

  @Override
  public AppViewModelFactory get() {
    return new AppViewModelFactory(creatorsProvider.get());
  }

  public static Factory<AppViewModelFactory> create(
      Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider) {
    return new AppViewModelFactory_Factory(creatorsProvider);
  }
}
