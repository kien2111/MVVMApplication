package com.mvvm.kien2111.mvvmapplication.ui.universal.common;

import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NavigationController_Factory implements Factory<NavigationController> {
  private final Provider<UniversalActivity> activityProvider;

  public NavigationController_Factory(Provider<UniversalActivity> activityProvider) {
    assert activityProvider != null;
    this.activityProvider = activityProvider;
  }

  @Override
  public NavigationController get() {
    return new NavigationController(activityProvider.get());
  }

  public static Factory<NavigationController> create(Provider<UniversalActivity> activityProvider) {
    return new NavigationController_Factory(activityProvider);
  }
}
