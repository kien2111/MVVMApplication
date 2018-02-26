package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.app.Activity;
import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ActivityBuilder_BindAdminMainActivity.AdminMainActivitySubcomponent.class)
public abstract class ActivityBuilder_BindAdminMainActivity {
  private ActivityBuilder_BindAdminMainActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(AdminMainActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      AdminMainActivitySubcomponent.Builder builder);

  @Subcomponent(modules = AdminMainModule.class)
  @PerActivity
  public interface AdminMainActivitySubcomponent extends AndroidInjector<AdminMainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AdminMainActivity> {}
  }
}
