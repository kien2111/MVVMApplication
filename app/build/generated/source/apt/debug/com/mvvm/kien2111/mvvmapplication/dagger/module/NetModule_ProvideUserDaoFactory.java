package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.db.UserDao;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetModule_ProvideUserDaoFactory implements Factory<UserDao> {
  private final NetModule module;

  public NetModule_ProvideUserDaoFactory(NetModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public UserDao get() {
    return Preconditions.checkNotNull(
        module.provideUserDao(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<UserDao> create(NetModule module) {
    return new NetModule_ProvideUserDaoFactory(module);
  }

  /** Proxies {@link NetModule#provideUserDao()}. */
  public static UserDao proxyProvideUserDao(NetModule instance) {
    return instance.provideUserDao();
  }
}
