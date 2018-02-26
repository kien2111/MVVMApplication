package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final MembersInjector<SearchViewModel> searchViewModelMembersInjector;

  public SearchViewModel_Factory(MembersInjector<SearchViewModel> searchViewModelMembersInjector) {
    assert searchViewModelMembersInjector != null;
    this.searchViewModelMembersInjector = searchViewModelMembersInjector;
  }

  @Override
  public SearchViewModel get() {
    return MembersInjectors.injectMembers(searchViewModelMembersInjector, new SearchViewModel());
  }

  public static Factory<SearchViewModel> create(
      MembersInjector<SearchViewModel> searchViewModelMembersInjector) {
    return new SearchViewModel_Factory(searchViewModelMembersInjector);
  }
}
