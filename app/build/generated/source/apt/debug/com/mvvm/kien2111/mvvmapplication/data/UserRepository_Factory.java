package com.mvvm.kien2111.mvvmapplication.data;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.db.UserDao;
import com.mvvm.kien2111.mvvmapplication.interfaces.UserService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UserRepository_Factory implements Factory<UserRepository> {
  private final Provider<UserService> userServiceProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<AppExecutors> appExecutorsProvider;

  public UserRepository_Factory(
      Provider<UserService> userServiceProvider,
      Provider<UserDao> userDaoProvider,
      Provider<AppExecutors> appExecutorsProvider) {
    assert userServiceProvider != null;
    this.userServiceProvider = userServiceProvider;
    assert userDaoProvider != null;
    this.userDaoProvider = userDaoProvider;
    assert appExecutorsProvider != null;
    this.appExecutorsProvider = appExecutorsProvider;
  }

  @Override
  public UserRepository get() {
    return new UserRepository(
        userServiceProvider.get(), userDaoProvider.get(), appExecutorsProvider.get());
  }

  public static Factory<UserRepository> create(
      Provider<UserService> userServiceProvider,
      Provider<UserDao> userDaoProvider,
      Provider<AppExecutors> appExecutorsProvider) {
    return new UserRepository_Factory(userServiceProvider, userDaoProvider, appExecutorsProvider);
  }
}
