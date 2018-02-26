package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final NetModule module;

  private final Provider<Gson> gsonProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetModule_ProvideRetrofitFactory(
      NetModule module, Provider<Gson> gsonProvider, Provider<OkHttpClient> okHttpClientProvider) {
    assert module != null;
    this.module = module;
    assert gsonProvider != null;
    this.gsonProvider = gsonProvider;
    assert okHttpClientProvider != null;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public Retrofit get() {
    return Preconditions.checkNotNull(
        module.provideRetrofit(gsonProvider.get(), okHttpClientProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Retrofit> create(
      NetModule module, Provider<Gson> gsonProvider, Provider<OkHttpClient> okHttpClientProvider) {
    return new NetModule_ProvideRetrofitFactory(module, gsonProvider, okHttpClientProvider);
  }

  /** Proxies {@link NetModule#provideRetrofit(Gson, OkHttpClient)}. */
  public static Retrofit proxyProvideRetrofit(
      NetModule instance, Gson gson, OkHttpClient okHttpClient) {
    return instance.provideRetrofit(gson, okHttpClient);
  }
}
