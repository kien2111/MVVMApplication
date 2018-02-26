package com.mvvm.kien2111.mvvmapplication.ui.universal.feed;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class FeedViewModel_Factory implements Factory<FeedViewModel> {
  private final MembersInjector<FeedViewModel> feedViewModelMembersInjector;

  public FeedViewModel_Factory(MembersInjector<FeedViewModel> feedViewModelMembersInjector) {
    assert feedViewModelMembersInjector != null;
    this.feedViewModelMembersInjector = feedViewModelMembersInjector;
  }

  @Override
  public FeedViewModel get() {
    return MembersInjectors.injectMembers(feedViewModelMembersInjector, new FeedViewModel());
  }

  public static Factory<FeedViewModel> create(
      MembersInjector<FeedViewModel> feedViewModelMembersInjector) {
    return new FeedViewModel_Factory(feedViewModelMembersInjector);
  }
}
