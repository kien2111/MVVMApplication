package com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class FavouriteProfileViewModel_Factory implements Factory<FavouriteProfileViewModel> {
  private final MembersInjector<FavouriteProfileViewModel> favouriteProfileViewModelMembersInjector;

  public FavouriteProfileViewModel_Factory(
      MembersInjector<FavouriteProfileViewModel> favouriteProfileViewModelMembersInjector) {
    assert favouriteProfileViewModelMembersInjector != null;
    this.favouriteProfileViewModelMembersInjector = favouriteProfileViewModelMembersInjector;
  }

  @Override
  public FavouriteProfileViewModel get() {
    return MembersInjectors.injectMembers(
        favouriteProfileViewModelMembersInjector, new FavouriteProfileViewModel());
  }

  public static Factory<FavouriteProfileViewModel> create(
      MembersInjector<FavouriteProfileViewModel> favouriteProfileViewModelMembersInjector) {
    return new FavouriteProfileViewModel_Factory(favouriteProfileViewModelMembersInjector);
  }
}
