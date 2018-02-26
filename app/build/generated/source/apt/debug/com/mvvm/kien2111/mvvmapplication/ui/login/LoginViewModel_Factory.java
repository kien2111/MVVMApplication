package com.mvvm.kien2111.mvvmapplication.ui.login;

import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final MembersInjector<LoginViewModel> loginViewModelMembersInjector;

  private final Provider<UserRepository> userRepositoryProvider;

  public LoginViewModel_Factory(
      MembersInjector<LoginViewModel> loginViewModelMembersInjector,
      Provider<UserRepository> userRepositoryProvider) {
    assert loginViewModelMembersInjector != null;
    this.loginViewModelMembersInjector = loginViewModelMembersInjector;
    assert userRepositoryProvider != null;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public LoginViewModel get() {
    return MembersInjectors.injectMembers(
        loginViewModelMembersInjector, new LoginViewModel(userRepositoryProvider.get()));
  }

  public static Factory<LoginViewModel> create(
      MembersInjector<LoginViewModel> loginViewModelMembersInjector,
      Provider<UserRepository> userRepositoryProvider) {
    return new LoginViewModel_Factory(loginViewModelMembersInjector, userRepositoryProvider);
  }
}
