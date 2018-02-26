package com.mvvm.kien2111.mvvmapplication.ui.universal;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UniversalViewModel_Factory implements Factory<UniversalViewModel> {
  private final MembersInjector<UniversalViewModel> universalViewModelMembersInjector;

  public UniversalViewModel_Factory(
      MembersInjector<UniversalViewModel> universalViewModelMembersInjector) {
    assert universalViewModelMembersInjector != null;
    this.universalViewModelMembersInjector = universalViewModelMembersInjector;
  }

  @Override
  public UniversalViewModel get() {
    return MembersInjectors.injectMembers(
        universalViewModelMembersInjector, new UniversalViewModel());
  }

  public static Factory<UniversalViewModel> create(
      MembersInjector<UniversalViewModel> universalViewModelMembersInjector) {
    return new UniversalViewModel_Factory(universalViewModelMembersInjector);
  }
}
