package com.mvvm.kien2111.mvvmapplication.ui.universal.user;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UserViewModel_Factory implements Factory<UserViewModel> {
  private final MembersInjector<UserViewModel> userViewModelMembersInjector;

  public UserViewModel_Factory(MembersInjector<UserViewModel> userViewModelMembersInjector) {
    assert userViewModelMembersInjector != null;
    this.userViewModelMembersInjector = userViewModelMembersInjector;
  }

  @Override
  public UserViewModel get() {
    return MembersInjectors.injectMembers(userViewModelMembersInjector, new UserViewModel());
  }

  public static Factory<UserViewModel> create(
      MembersInjector<UserViewModel> userViewModelMembersInjector) {
    return new UserViewModel_Factory(userViewModelMembersInjector);
  }
}
