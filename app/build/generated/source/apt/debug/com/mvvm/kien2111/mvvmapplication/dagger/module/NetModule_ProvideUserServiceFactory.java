package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.interfaces.UserService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideUserServiceFactory implements Factory<UserService> {
  private final NetModule module;

  private final Provider<Retrofit> retrofitProvider;

  public NetModule_ProvideUserServiceFactory(
      NetModule module, Provider<Retrofit> retrofitProvider) {
    assert module != null;
    this.module = module;
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public UserService get() {
    return Preconditions.checkNotNull(
        module.provideUserService(retrofitProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<UserService> create(NetModule module, Provider<Retrofit> retrofitProvider) {
    return new NetModule_ProvideUserServiceFactory(module, retrofitProvider);
  }

  /** Proxies {@link NetModule#provideUserService(Retrofit)}. */
  public static UserService proxyProvideUserService(NetModule instance, Retrofit retrofit) {
    return instance.provideUserService(retrofit);
  }
}
