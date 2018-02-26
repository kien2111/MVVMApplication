package com.mvvm.kien2111.mvvmapplication.authenticate;

import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AuthenticatorImpl_MembersInjector implements MembersInjector<AuthenticatorImpl> {
  private final Provider<UserRepository> userRepositoryProvider;

  public AuthenticatorImpl_MembersInjector(Provider<UserRepository> userRepositoryProvider) {
    assert userRepositoryProvider != null;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  public static MembersInjector<AuthenticatorImpl> create(
      Provider<UserRepository> userRepositoryProvider) {
    return new AuthenticatorImpl_MembersInjector(userRepositoryProvider);
  }

  @Override
  public void injectMembers(AuthenticatorImpl instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.userRepository = userRepositoryProvider.get();
  }

  public static void injectUserRepository(
      AuthenticatorImpl instance, Provider<UserRepository> userRepositoryProvider) {
    instance.userRepository = userRepositoryProvider.get();
  }
}
