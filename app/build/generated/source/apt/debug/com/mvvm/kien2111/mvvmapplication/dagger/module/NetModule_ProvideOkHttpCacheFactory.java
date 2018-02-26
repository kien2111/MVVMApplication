package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.MyApplication;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideOkHttpCacheFactory implements Factory<Cache> {
  private final NetModule module;

  private final Provider<MyApplication> applicationProvider;

  public NetModule_ProvideOkHttpCacheFactory(
      NetModule module, Provider<MyApplication> applicationProvider) {
    assert module != null;
    this.module = module;
    assert applicationProvider != null;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public Cache get() {
    return Preconditions.checkNotNull(
        module.provideOkHttpCache(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Cache> create(
      NetModule module, Provider<MyApplication> applicationProvider) {
    return new NetModule_ProvideOkHttpCacheFactory(module, applicationProvider);
  }

  /** Proxies {@link NetModule#provideOkHttpCache(MyApplication)}. */
  public static Cache proxyProvideOkHttpCache(NetModule instance, MyApplication application) {
    return instance.provideOkHttpCache(application);
  }
}
