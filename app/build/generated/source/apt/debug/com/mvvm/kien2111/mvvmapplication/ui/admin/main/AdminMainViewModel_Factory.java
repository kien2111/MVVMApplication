package com.mvvm.kien2111.mvvmapplication.ui.admin.main;

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
public final class AdminMainViewModel_Factory implements Factory<AdminMainViewModel> {
  private final MembersInjector<AdminMainViewModel> adminMainViewModelMembersInjector;

  private final Provider<UserRepository> userRepositoryProvider;

  public AdminMainViewModel_Factory(
      MembersInjector<AdminMainViewModel> adminMainViewModelMembersInjector,
      Provider<UserRepository> userRepositoryProvider) {
    assert adminMainViewModelMembersInjector != null;
    this.adminMainViewModelMembersInjector = adminMainViewModelMembersInjector;
    assert userRepositoryProvider != null;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public AdminMainViewModel get() {
    return MembersInjectors.injectMembers(
        adminMainViewModelMembersInjector, new AdminMainViewModel(userRepositoryProvider.get()));
  }

  public static Factory<AdminMainViewModel> create(
      MembersInjector<AdminMainViewModel> adminMainViewModelMembersInjector,
      Provider<UserRepository> userRepositoryProvider) {
    return new AdminMainViewModel_Factory(
        adminMainViewModelMembersInjector, userRepositoryProvider);
  }
}
