package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.app.Activity;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerActivity;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpActivity;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ActivityBuilder_BindSignUpActivity.SignUpActivitySubcomponent.class)
public abstract class ActivityBuilder_BindSignUpActivity {
  private ActivityBuilder_BindSignUpActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(SignUpActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      SignUpActivitySubcomponent.Builder builder);

  @Subcomponent(modules = SignUpModule.class)
  @PerActivity
  public interface SignUpActivitySubcomponent extends AndroidInjector<SignUpActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SignUpActivity> {}
  }
}
