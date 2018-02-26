package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.support.v4.app.Fragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = FragmentBuilder_BindUserFragment.UserFragmentSubcomponent.class)
public abstract class FragmentBuilder_BindUserFragment {
  private FragmentBuilder_BindUserFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(UserFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      UserFragmentSubcomponent.Builder builder);

  @Subcomponent(modules = UserModule.class)
  @PerFragment
  public interface UserFragmentSubcomponent extends AndroidInjector<UserFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<UserFragment> {}
  }
}
