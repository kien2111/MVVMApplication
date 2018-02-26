package com.mvvm.kien2111.mvvmapplication.dagger.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final NetModule module;

  private final Provider<Cache> cacheProvider;

  public NetModule_ProvideOkHttpClientFactory(NetModule module, Provider<Cache> cacheProvider) {
    assert module != null;
    this.module = module;
    assert cacheProvider != null;
    this.cacheProvider = cacheProvider;
  }

  @Override
  public OkHttpClient get() {
    return Preconditions.checkNotNull(
        module.provideOkHttpClient(cacheProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<OkHttpClient> create(NetModule module, Provider<Cache> cacheProvider) {
    return new NetModule_ProvideOkHttpClientFactory(module, cacheProvider);
  }

  /** Proxies {@link NetModule#provideOkHttpClient(Cache)}. */
  public static OkHttpClient proxyProvideOkHttpClient(NetModule instance, Cache cache) {
    return instance.provideOkHttpClient(cache);
  }
}
