package com.mvvm.kien2111.mvvmapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MyApplication_MembersInjector implements MembersInjector<MyApplication> {
  private final Provider<DispatchingAndroidInjector<Activity>> activityInjectorProvider;

  private final Provider<DispatchingAndroidInjector<BroadcastReceiver>>
      broadcastReceiverInjectorProvider;

  private final Provider<DispatchingAndroidInjector<Fragment>> fragmentInjectorProvider;

  private final Provider<DispatchingAndroidInjector<Service>> serviceInjectorProvider;

  private final Provider<DispatchingAndroidInjector<ContentProvider>>
      contentProviderInjectorProvider;

  private final Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
      supportFragmentInjectorProvider;

  public MyApplication_MembersInjector(
      Provider<DispatchingAndroidInjector<Activity>> activityInjectorProvider,
      Provider<DispatchingAndroidInjector<BroadcastReceiver>> broadcastReceiverInjectorProvider,
      Provider<DispatchingAndroidInjector<Fragment>> fragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<Service>> serviceInjectorProvider,
      Provider<DispatchingAndroidInjector<ContentProvider>> contentProviderInjectorProvider,
      Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
          supportFragmentInjectorProvider) {
    assert activityInjectorProvider != null;
    this.activityInjectorProvider = activityInjectorProvider;
    assert broadcastReceiverInjectorProvider != null;
    this.broadcastReceiverInjectorProvider = broadcastReceiverInjectorProvider;
    assert fragmentInjectorProvider != null;
    this.fragmentInjectorProvider = fragmentInjectorProvider;
    assert serviceInjectorProvider != null;
    this.serviceInjectorProvider = serviceInjectorProvider;
    assert contentProviderInjectorProvider != null;
    this.contentProviderInjectorProvider = contentProviderInjectorProvider;
    assert supportFragmentInjectorProvider != null;
    this.supportFragmentInjectorProvider = supportFragmentInjectorProvider;
  }

  public static MembersInjector<MyApplication> create(
      Provider<DispatchingAndroidInjector<Activity>> activityInjectorProvider,
      Provider<DispatchingAndroidInjector<BroadcastReceiver>> broadcastReceiverInjectorProvider,
      Provider<DispatchingAndroidInjector<Fragment>> fragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<Service>> serviceInjectorProvider,
      Provider<DispatchingAndroidInjector<ContentProvider>> contentProviderInjectorProvider,
      Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
          supportFragmentInjectorProvider) {
    return new MyApplication_MembersInjector(
        activityInjectorProvider,
        broadcastReceiverInjectorProvider,
        fragmentInjectorProvider,
        serviceInjectorProvider,
        contentProviderInjectorProvider,
        supportFragmentInjectorProvider);
  }

  @Override
  public void injectMembers(MyApplication instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    dagger.android.DaggerApplication_MembersInjector.injectActivityInjector(
        instance, activityInjectorProvider);
    dagger.android.DaggerApplication_MembersInjector.injectBroadcastReceiverInjector(
        instance, broadcastReceiverInjectorProvider);
    dagger.android.DaggerApplication_MembersInjector.injectFragmentInjector(
        instance, fragmentInjectorProvider);
    dagger.android.DaggerApplication_MembersInjector.injectServiceInjector(
        instance, serviceInjectorProvider);
    dagger.android.DaggerApplication_MembersInjector.injectContentProviderInjector(
        instance, contentProviderInjectorProvider);
    dagger.android.DaggerApplication_MembersInjector.injectSetInjected(instance);
    dagger.android.support.DaggerApplication_MembersInjector.injectSupportFragmentInjector(
        instance, supportFragmentInjectorProvider);
  }
}
