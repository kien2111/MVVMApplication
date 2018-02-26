package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.support.v4.app.Fragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      FragmentBuilder_BindFavouriteProfileFragment.FavouriteProfileFragmentSubcomponent.class
)
public abstract class FragmentBuilder_BindFavouriteProfileFragment {
  private FragmentBuilder_BindFavouriteProfileFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(FavouriteProfileFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      FavouriteProfileFragmentSubcomponent.Builder builder);

  @Subcomponent(modules = FavouriteProfileModule.class)
  @PerFragment
  public interface FavouriteProfileFragmentSubcomponent
      extends AndroidInjector<FavouriteProfileFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FavouriteProfileFragment> {}
  }
}
