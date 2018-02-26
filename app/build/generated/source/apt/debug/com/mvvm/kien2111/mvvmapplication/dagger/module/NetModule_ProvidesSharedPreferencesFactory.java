package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.content.SharedPreferences;
import com.mvvm.kien2111.mvvmapplication.MyApplication;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvidesSharedPreferencesFactory
    implements Factory<SharedPreferences> {
  private final NetModule module;

  private final Provider<MyApplication> applicationProvider;

  public NetModule_ProvidesSharedPreferencesFactory(
      NetModule module, Provider<MyApplication> applicationProvider) {
    assert module != null;
    this.module = module;
    assert applicationProvider != null;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public SharedPreferences get() {
    return Preconditions.checkNotNull(
        module.providesSharedPreferences(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<SharedPreferences> create(
      NetModule module, Provider<MyApplication> applicationProvider) {
    return new NetModule_ProvidesSharedPreferencesFactory(module, applicationProvider);
  }

  /** Proxies {@link NetModule#providesSharedPreferences(MyApplication)}. */
  public static SharedPreferences proxyProvidesSharedPreferences(
      NetModule instance, MyApplication application) {
    return instance.providesSharedPreferences(application);
  }
}
