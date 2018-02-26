package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.support.v4.app.Fragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = FragmentBuilder_BindFeedFragment.FeedFragmentSubcomponent.class)
public abstract class FragmentBuilder_BindFeedFragment {
  private FragmentBuilder_BindFeedFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(FeedFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      FeedFragmentSubcomponent.Builder builder);

  @Subcomponent(modules = FeedModule.class)
  @PerFragment
  public interface FeedFragmentSubcomponent extends AndroidInjector<FeedFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FeedFragment> {}
  }
}
