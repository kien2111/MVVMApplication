package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.app.Activity;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ActivityBuilder_BindUniversalActivity.UniversalActivitySubcomponent.class)
public abstract class ActivityBuilder_BindUniversalActivity {
  private ActivityBuilder_BindUniversalActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(UniversalActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      UniversalActivitySubcomponent.Builder builder);

  @Subcomponent(modules = {UniversalModule.class, FragmentBuilder.class})
  @PerActivity
  public interface UniversalActivitySubcomponent extends AndroidInjector<UniversalActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<UniversalActivity> {}
  }
}
