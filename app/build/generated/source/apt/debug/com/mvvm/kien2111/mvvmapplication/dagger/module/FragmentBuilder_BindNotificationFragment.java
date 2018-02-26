package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.support.v4.app.Fragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = FragmentBuilder_BindNotificationFragment.NotificationFragmentSubcomponent.class
)
public abstract class FragmentBuilder_BindNotificationFragment {
  private FragmentBuilder_BindNotificationFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(NotificationFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      NotificationFragmentSubcomponent.Builder builder);

  @Subcomponent(modules = NotificationModule.class)
  @PerFragment
  public interface NotificationFragmentSubcomponent extends AndroidInjector<NotificationFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NotificationFragment> {}
  }
}
