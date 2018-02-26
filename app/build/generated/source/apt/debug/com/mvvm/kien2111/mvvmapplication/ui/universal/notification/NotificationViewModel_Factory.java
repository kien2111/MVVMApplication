package com.mvvm.kien2111.mvvmapplication.ui.universal.notification;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NotificationViewModel_Factory implements Factory<NotificationViewModel> {
  private final MembersInjector<NotificationViewModel> notificationViewModelMembersInjector;

  public NotificationViewModel_Factory(
      MembersInjector<NotificationViewModel> notificationViewModelMembersInjector) {
    assert notificationViewModelMembersInjector != null;
    this.notificationViewModelMembersInjector = notificationViewModelMembersInjector;
  }

  @Override
  public NotificationViewModel get() {
    return MembersInjectors.injectMembers(
        notificationViewModelMembersInjector, new NotificationViewModel());
  }

  public static Factory<NotificationViewModel> create(
      MembersInjector<NotificationViewModel> notificationViewModelMembersInjector) {
    return new NotificationViewModel_Factory(notificationViewModelMembersInjector);
  }
}
