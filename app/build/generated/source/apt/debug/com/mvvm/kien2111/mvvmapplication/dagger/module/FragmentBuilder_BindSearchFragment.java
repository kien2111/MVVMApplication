package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.support.v4.app.Fragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = FragmentBuilder_BindSearchFragment.SearchFragmentSubcomponent.class)
public abstract class FragmentBuilder_BindSearchFragment {
  private FragmentBuilder_BindSearchFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(SearchFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      SearchFragmentSubcomponent.Builder builder);

  @Subcomponent(modules = SearchModule.class)
  @PerFragment
  public interface SearchFragmentSubcomponent extends AndroidInjector<SearchFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SearchFragment> {}
  }
}
