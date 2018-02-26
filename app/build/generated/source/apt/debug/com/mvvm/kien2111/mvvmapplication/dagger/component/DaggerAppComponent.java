package com.mvvm.kien2111.mvvmapplication.dagger.component;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.AppExecutors_Factory;
import com.mvvm.kien2111.mvvmapplication.AppViewModelFactory;
import com.mvvm.kien2111.mvvmapplication.AppViewModelFactory_Factory;
import com.mvvm.kien2111.mvvmapplication.MyApplication;
import com.mvvm.kien2111.mvvmapplication.MyApplication_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.dagger.module.ActivityBuilder_BindAdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.dagger.module.ActivityBuilder_BindLoginActivity;
import com.mvvm.kien2111.mvvmapplication.dagger.module.ActivityBuilder_BindSignUpActivity;
import com.mvvm.kien2111.mvvmapplication.dagger.module.ActivityBuilder_BindUniversalActivity;
import com.mvvm.kien2111.mvvmapplication.dagger.module.FragmentBuilder_BindFavouriteProfileFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.module.FragmentBuilder_BindFeedFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.module.FragmentBuilder_BindNotificationFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.module.FragmentBuilder_BindSearchFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.module.FragmentBuilder_BindUserFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule_ProvideGsonFactory;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule_ProvideOkHttpCacheFactory;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule_ProvideOkHttpClientFactory;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule_ProvideRetrofitFactory;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule_ProvideUserDaoFactory;
import com.mvvm.kien2111.mvvmapplication.dagger.module.NetModule_ProvideUserServiceFactory;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository_Factory;
import com.mvvm.kien2111.mvvmapplication.db.UserDao;
import com.mvvm.kien2111.mvvmapplication.interfaces.UserService;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpActivity;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpActivity_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileFragment_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedFragment_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationFragment_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchFragment_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchViewModel_Factory;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserFragment_MembersInjector;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserViewModel_Factory;
import dagger.MembersInjector;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.MapProviderFactory;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<ActivityBuilder_BindLoginActivity.LoginActivitySubcomponent.Builder>
      loginActivitySubcomponentBuilderProvider;

  private Provider<AndroidInjector.Factory<? extends Activity>> bindAndroidInjectorFactoryProvider;

  private Provider<ActivityBuilder_BindSignUpActivity.SignUpActivitySubcomponent.Builder>
      signUpActivitySubcomponentBuilderProvider;

  private Provider<AndroidInjector.Factory<? extends Activity>> bindAndroidInjectorFactoryProvider2;

  private Provider<ActivityBuilder_BindUniversalActivity.UniversalActivitySubcomponent.Builder>
      universalActivitySubcomponentBuilderProvider;

  private Provider<AndroidInjector.Factory<? extends Activity>> bindAndroidInjectorFactoryProvider3;

  private Provider<ActivityBuilder_BindAdminMainActivity.AdminMainActivitySubcomponent.Builder>
      adminMainActivitySubcomponentBuilderProvider;

  private Provider<AndroidInjector.Factory<? extends Activity>> bindAndroidInjectorFactoryProvider4;

  private Provider<
          Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>>>
      mapOfClassOfAndProviderOfFactoryOfProvider;

  private Provider<DispatchingAndroidInjector<Activity>> dispatchingAndroidInjectorProvider;

  private Provider<DispatchingAndroidInjector<BroadcastReceiver>>
      dispatchingAndroidInjectorProvider2;

  private Provider<DispatchingAndroidInjector<Fragment>> dispatchingAndroidInjectorProvider3;

  private Provider<DispatchingAndroidInjector<Service>> dispatchingAndroidInjectorProvider4;

  private Provider<DispatchingAndroidInjector<ContentProvider>> dispatchingAndroidInjectorProvider5;

  private Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
      dispatchingAndroidInjectorProvider6;

  private MembersInjector<MyApplication> myApplicationMembersInjector;

  private Provider<Gson> provideGsonProvider;

  private Provider<MyApplication> seedInstanceProvider;

  private Provider<Cache> provideOkHttpCacheProvider;

  private Provider<OkHttpClient> provideOkHttpClientProvider;

  private Provider<Retrofit> provideRetrofitProvider;

  private Provider<UserService> provideUserServiceProvider;

  private Provider<UserDao> provideUserDaoProvider;

  private Provider<AppExecutors> appExecutorsProvider;

  private Provider<UserRepository> userRepositoryProvider;

  private Provider<LoginViewModel> loginViewModelProvider;

  private Provider<ViewModel> bindLoginViewModelProvider;

  private Provider<SignUpViewModel> signUpViewModelProvider;

  private Provider<ViewModel> bindSignUpViewModelProvider;

  private Provider<UniversalViewModel> universalViewModelProvider;

  private Provider<ViewModel> bindUniversalViewModelProvider;

  private Provider<UserViewModel> userViewModelProvider;

  private Provider<ViewModel> bindUserViewModelProvider;

  private Provider<FeedViewModel> feedViewModelProvider;

  private Provider<ViewModel> bindFeedViewModelProvider;

  private Provider<NotificationViewModel> notificationViewModelProvider;

  private Provider<ViewModel> bindNotificationViewModelProvider;

  private Provider<SearchViewModel> searchViewModelProvider;

  private Provider<ViewModel> bindSearchViewModelProvider;

  private Provider<FavouriteProfileViewModel> favouriteProfileViewModelProvider;

  private Provider<ViewModel> bindFavouriteProfileViewModelProvider;

  private Provider<AdminMainViewModel> adminMainViewModelProvider;

  private Provider<ViewModel> bindAdminViewModelProvider;

  private Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>>
      mapOfClassOfAndProviderOfViewModelProvider;

  private Provider<AppViewModelFactory> appViewModelFactoryProvider;

  private Provider<ViewModelProvider.Factory> createViewModelFactoryProvider;

  private DaggerAppComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static AppComponent.Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.loginActivitySubcomponentBuilderProvider =
        new dagger.internal.Factory<
            ActivityBuilder_BindLoginActivity.LoginActivitySubcomponent.Builder>() {
          @Override
          public ActivityBuilder_BindLoginActivity.LoginActivitySubcomponent.Builder get() {
            return new LoginActivitySubcomponentBuilder();
          }
        };

    this.bindAndroidInjectorFactoryProvider = (Provider) loginActivitySubcomponentBuilderProvider;

    this.signUpActivitySubcomponentBuilderProvider =
        new dagger.internal.Factory<
            ActivityBuilder_BindSignUpActivity.SignUpActivitySubcomponent.Builder>() {
          @Override
          public ActivityBuilder_BindSignUpActivity.SignUpActivitySubcomponent.Builder get() {
            return new SignUpActivitySubcomponentBuilder();
          }
        };

    this.bindAndroidInjectorFactoryProvider2 = (Provider) signUpActivitySubcomponentBuilderProvider;

    this.universalActivitySubcomponentBuilderProvider =
        new dagger.internal.Factory<
            ActivityBuilder_BindUniversalActivity.UniversalActivitySubcomponent.Builder>() {
          @Override
          public ActivityBuilder_BindUniversalActivity.UniversalActivitySubcomponent.Builder get() {
            return new UniversalActivitySubcomponentBuilder();
          }
        };

    this.bindAndroidInjectorFactoryProvider3 =
        (Provider) universalActivitySubcomponentBuilderProvider;

    this.adminMainActivitySubcomponentBuilderProvider =
        new dagger.internal.Factory<
            ActivityBuilder_BindAdminMainActivity.AdminMainActivitySubcomponent.Builder>() {
          @Override
          public ActivityBuilder_BindAdminMainActivity.AdminMainActivitySubcomponent.Builder get() {
            return new AdminMainActivitySubcomponentBuilder();
          }
        };

    this.bindAndroidInjectorFactoryProvider4 =
        (Provider) adminMainActivitySubcomponentBuilderProvider;

    this.mapOfClassOfAndProviderOfFactoryOfProvider =
        MapProviderFactory
            .<Class<? extends Activity>, AndroidInjector.Factory<? extends Activity>>builder(4)
            .put(LoginActivity.class, bindAndroidInjectorFactoryProvider)
            .put(SignUpActivity.class, bindAndroidInjectorFactoryProvider2)
            .put(UniversalActivity.class, bindAndroidInjectorFactoryProvider3)
            .put(AdminMainActivity.class, bindAndroidInjectorFactoryProvider4)
            .build();

    this.dispatchingAndroidInjectorProvider =
        DispatchingAndroidInjector_Factory.create(mapOfClassOfAndProviderOfFactoryOfProvider);

    this.dispatchingAndroidInjectorProvider2 =
        DispatchingAndroidInjector_Factory.create(
            MapProviderFactory
                .<Class<? extends BroadcastReceiver>,
                    AndroidInjector.Factory<? extends BroadcastReceiver>>
                    empty());

    this.dispatchingAndroidInjectorProvider3 =
        DispatchingAndroidInjector_Factory.create(
            MapProviderFactory
                .<Class<? extends Fragment>, AndroidInjector.Factory<? extends Fragment>>empty());

    this.dispatchingAndroidInjectorProvider4 =
        DispatchingAndroidInjector_Factory.create(
            MapProviderFactory
                .<Class<? extends Service>, AndroidInjector.Factory<? extends Service>>empty());

    this.dispatchingAndroidInjectorProvider5 =
        DispatchingAndroidInjector_Factory.create(
            MapProviderFactory
                .<Class<? extends ContentProvider>,
                    AndroidInjector.Factory<? extends ContentProvider>>
                    empty());

    this.dispatchingAndroidInjectorProvider6 =
        DispatchingAndroidInjector_Factory.create(
            MapProviderFactory
                .<Class<? extends android.support.v4.app.Fragment>,
                    AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>
                    empty());

    this.myApplicationMembersInjector =
        MyApplication_MembersInjector.create(
            dispatchingAndroidInjectorProvider,
            dispatchingAndroidInjectorProvider2,
            dispatchingAndroidInjectorProvider3,
            dispatchingAndroidInjectorProvider4,
            dispatchingAndroidInjectorProvider5,
            dispatchingAndroidInjectorProvider6);

    this.provideGsonProvider =
        DoubleCheck.provider(NetModule_ProvideGsonFactory.create(builder.netModule));

    this.seedInstanceProvider = InstanceFactory.create(builder.seedInstance);

    this.provideOkHttpCacheProvider =
        DoubleCheck.provider(
            NetModule_ProvideOkHttpCacheFactory.create(builder.netModule, seedInstanceProvider));

    this.provideOkHttpClientProvider =
        DoubleCheck.provider(
            NetModule_ProvideOkHttpClientFactory.create(
                builder.netModule, provideOkHttpCacheProvider));

    this.provideRetrofitProvider =
        DoubleCheck.provider(
            NetModule_ProvideRetrofitFactory.create(
                builder.netModule, provideGsonProvider, provideOkHttpClientProvider));

    this.provideUserServiceProvider =
        DoubleCheck.provider(
            NetModule_ProvideUserServiceFactory.create(builder.netModule, provideRetrofitProvider));

    this.provideUserDaoProvider =
        DoubleCheck.provider(NetModule_ProvideUserDaoFactory.create(builder.netModule));

    this.appExecutorsProvider = DoubleCheck.provider(AppExecutors_Factory.create());

    this.userRepositoryProvider =
        UserRepository_Factory.create(
            provideUserServiceProvider, provideUserDaoProvider, appExecutorsProvider);

    this.loginViewModelProvider =
        LoginViewModel_Factory.create(
            MembersInjectors.<LoginViewModel>noOp(), userRepositoryProvider);

    this.bindLoginViewModelProvider = (Provider) loginViewModelProvider;

    this.signUpViewModelProvider =
        SignUpViewModel_Factory.create(MembersInjectors.<SignUpViewModel>noOp());

    this.bindSignUpViewModelProvider = (Provider) signUpViewModelProvider;

    this.universalViewModelProvider =
        UniversalViewModel_Factory.create(MembersInjectors.<UniversalViewModel>noOp());

    this.bindUniversalViewModelProvider = (Provider) universalViewModelProvider;

    this.userViewModelProvider =
        UserViewModel_Factory.create(MembersInjectors.<UserViewModel>noOp());

    this.bindUserViewModelProvider = (Provider) userViewModelProvider;

    this.feedViewModelProvider =
        FeedViewModel_Factory.create(MembersInjectors.<FeedViewModel>noOp());

    this.bindFeedViewModelProvider = (Provider) feedViewModelProvider;

    this.notificationViewModelProvider =
        NotificationViewModel_Factory.create(MembersInjectors.<NotificationViewModel>noOp());

    this.bindNotificationViewModelProvider = (Provider) notificationViewModelProvider;

    this.searchViewModelProvider =
        SearchViewModel_Factory.create(MembersInjectors.<SearchViewModel>noOp());

    this.bindSearchViewModelProvider = (Provider) searchViewModelProvider;

    this.favouriteProfileViewModelProvider =
        FavouriteProfileViewModel_Factory.create(
            MembersInjectors.<FavouriteProfileViewModel>noOp());

    this.bindFavouriteProfileViewModelProvider = (Provider) favouriteProfileViewModelProvider;

    this.adminMainViewModelProvider =
        AdminMainViewModel_Factory.create(
            MembersInjectors.<AdminMainViewModel>noOp(), userRepositoryProvider);

    this.bindAdminViewModelProvider = (Provider) adminMainViewModelProvider;

    this.mapOfClassOfAndProviderOfViewModelProvider =
        MapProviderFactory.<Class<? extends ViewModel>, ViewModel>builder(9)
            .put(LoginViewModel.class, bindLoginViewModelProvider)
            .put(SignUpViewModel.class, bindSignUpViewModelProvider)
            .put(UniversalViewModel.class, bindUniversalViewModelProvider)
            .put(UserViewModel.class, bindUserViewModelProvider)
            .put(FeedViewModel.class, bindFeedViewModelProvider)
            .put(NotificationViewModel.class, bindNotificationViewModelProvider)
            .put(SearchViewModel.class, bindSearchViewModelProvider)
            .put(FavouriteProfileViewModel.class, bindFavouriteProfileViewModelProvider)
            .put(AdminMainViewModel.class, bindAdminViewModelProvider)
            .build();

    this.appViewModelFactoryProvider =
        DoubleCheck.provider(
            AppViewModelFactory_Factory.create(mapOfClassOfAndProviderOfViewModelProvider));

    this.createViewModelFactoryProvider =
        DoubleCheck.provider((Provider) appViewModelFactoryProvider);
  }

  @Override
  public void inject(MyApplication arg0) {
    myApplicationMembersInjector.injectMembers(arg0);
  }

  private static final class Builder extends AppComponent.Builder {
    private NetModule netModule;

    private MyApplication seedInstance;

    @Override
    public AppComponent build() {
      if (netModule == null) {
        this.netModule = new NetModule();
      }
      if (seedInstance == null) {
        throw new IllegalStateException(MyApplication.class.getCanonicalName() + " must be set");
      }
      return new DaggerAppComponent(this);
    }

    @Override
    public void seedInstance(MyApplication arg0) {
      this.seedInstance = Preconditions.checkNotNull(arg0);
    }
  }

  private final class LoginActivitySubcomponentBuilder
      extends ActivityBuilder_BindLoginActivity.LoginActivitySubcomponent.Builder {
    private LoginActivity seedInstance;

    @Override
    public ActivityBuilder_BindLoginActivity.LoginActivitySubcomponent build() {
      if (seedInstance == null) {
        throw new IllegalStateException(LoginActivity.class.getCanonicalName() + " must be set");
      }
      return new LoginActivitySubcomponentImpl(this);
    }

    @Override
    public void seedInstance(LoginActivity arg0) {
      this.seedInstance = Preconditions.checkNotNull(arg0);
    }
  }

  private final class LoginActivitySubcomponentImpl
      implements ActivityBuilder_BindLoginActivity.LoginActivitySubcomponent {
    private MembersInjector<LoginActivity> loginActivityMembersInjector;

    private LoginActivitySubcomponentImpl(LoginActivitySubcomponentBuilder builder) {
      assert builder != null;
      initialize(builder);
    }

    @SuppressWarnings("unchecked")
    private void initialize(final LoginActivitySubcomponentBuilder builder) {

      this.loginActivityMembersInjector =
          LoginActivity_MembersInjector.create(
              DaggerAppComponent.this.dispatchingAndroidInjectorProvider6,
              DaggerAppComponent.this.dispatchingAndroidInjectorProvider3,
              DaggerAppComponent.this.createViewModelFactoryProvider,
              DaggerAppComponent.this.seedInstanceProvider);
    }

    @Override
    public void inject(LoginActivity arg0) {
      loginActivityMembersInjector.injectMembers(arg0);
    }
  }

  private final class SignUpActivitySubcomponentBuilder
      extends ActivityBuilder_BindSignUpActivity.SignUpActivitySubcomponent.Builder {
    private SignUpActivity seedInstance;

    @Override
    public ActivityBuilder_BindSignUpActivity.SignUpActivitySubcomponent build() {
      if (seedInstance == null) {
        throw new IllegalStateException(SignUpActivity.class.getCanonicalName() + " must be set");
      }
      return new SignUpActivitySubcomponentImpl(this);
    }

    @Override
    public void seedInstance(SignUpActivity arg0) {
      this.seedInstance = Preconditions.checkNotNull(arg0);
    }
  }

  private final class SignUpActivitySubcomponentImpl
      implements ActivityBuilder_BindSignUpActivity.SignUpActivitySubcomponent {
    private MembersInjector<SignUpActivity> signUpActivityMembersInjector;

    private SignUpActivitySubcomponentImpl(SignUpActivitySubcomponentBuilder builder) {
      assert builder != null;
      initialize(builder);
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SignUpActivitySubcomponentBuilder builder) {

      this.signUpActivityMembersInjector =
          SignUpActivity_MembersInjector.create(
              DaggerAppComponent.this.dispatchingAndroidInjectorProvider6,
              DaggerAppComponent.this.dispatchingAndroidInjectorProvider3,
              DaggerAppComponent.this.createViewModelFactoryProvider,
              DaggerAppComponent.this.seedInstanceProvider);
    }

    @Override
    public void inject(SignUpActivity arg0) {
      signUpActivityMembersInjector.injectMembers(arg0);
    }
  }

  private final class UniversalActivitySubcomponentBuilder
      extends ActivityBuilder_BindUniversalActivity.UniversalActivitySubcomponent.Builder {
    private UniversalActivity seedInstance;

    @Override
    public ActivityBuilder_BindUniversalActivity.UniversalActivitySubcomponent build() {
      if (seedInstance == null) {
        throw new IllegalStateException(
            UniversalActivity.class.getCanonicalName() + " must be set");
      }
      return new UniversalActivitySubcomponentImpl(this);
    }

    @Override
    public void seedInstance(UniversalActivity arg0) {
      this.seedInstance = Preconditions.checkNotNull(arg0);
    }
  }

  private final class UniversalActivitySubcomponentImpl
      implements ActivityBuilder_BindUniversalActivity.UniversalActivitySubcomponent {
    private Provider<FragmentBuilder_BindUserFragment.UserFragmentSubcomponent.Builder>
        userFragmentSubcomponentBuilderProvider;

    private Provider<AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>
        bindAndroidInjectorFactoryProvider;

    private Provider<FragmentBuilder_BindSearchFragment.SearchFragmentSubcomponent.Builder>
        searchFragmentSubcomponentBuilderProvider;

    private Provider<AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>
        bindAndroidInjectorFactoryProvider2;

    private Provider<
            FragmentBuilder_BindFavouriteProfileFragment.FavouriteProfileFragmentSubcomponent
                .Builder>
        favouriteProfileFragmentSubcomponentBuilderProvider;

    private Provider<AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>
        bindAndroidInjectorFactoryProvider3;

    private Provider<
            FragmentBuilder_BindNotificationFragment.NotificationFragmentSubcomponent.Builder>
        notificationFragmentSubcomponentBuilderProvider;

    private Provider<AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>
        bindAndroidInjectorFactoryProvider4;

    private Provider<FragmentBuilder_BindFeedFragment.FeedFragmentSubcomponent.Builder>
        feedFragmentSubcomponentBuilderProvider;

    private Provider<AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>
        bindAndroidInjectorFactoryProvider5;

    private Provider<
            Map<
                Class<? extends android.support.v4.app.Fragment>,
                Provider<AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>>>
        mapOfClassOfAndProviderOfFactoryOfProvider;

    private Provider<DispatchingAndroidInjector<android.support.v4.app.Fragment>>
        dispatchingAndroidInjectorProvider;

    private MembersInjector<UniversalActivity> universalActivityMembersInjector;

    private UniversalActivitySubcomponentImpl(UniversalActivitySubcomponentBuilder builder) {
      assert builder != null;
      initialize(builder);
    }

    @SuppressWarnings("unchecked")
    private void initialize(final UniversalActivitySubcomponentBuilder builder) {

      this.userFragmentSubcomponentBuilderProvider =
          new dagger.internal.Factory<
              FragmentBuilder_BindUserFragment.UserFragmentSubcomponent.Builder>() {
            @Override
            public FragmentBuilder_BindUserFragment.UserFragmentSubcomponent.Builder get() {
              return new UserFragmentSubcomponentBuilder();
            }
          };

      this.bindAndroidInjectorFactoryProvider = (Provider) userFragmentSubcomponentBuilderProvider;

      this.searchFragmentSubcomponentBuilderProvider =
          new dagger.internal.Factory<
              FragmentBuilder_BindSearchFragment.SearchFragmentSubcomponent.Builder>() {
            @Override
            public FragmentBuilder_BindSearchFragment.SearchFragmentSubcomponent.Builder get() {
              return new SearchFragmentSubcomponentBuilder();
            }
          };

      this.bindAndroidInjectorFactoryProvider2 =
          (Provider) searchFragmentSubcomponentBuilderProvider;

      this.favouriteProfileFragmentSubcomponentBuilderProvider =
          new dagger.internal.Factory<
              FragmentBuilder_BindFavouriteProfileFragment.FavouriteProfileFragmentSubcomponent
                  .Builder>() {
            @Override
            public FragmentBuilder_BindFavouriteProfileFragment.FavouriteProfileFragmentSubcomponent
                    .Builder
                get() {
              return new FavouriteProfileFragmentSubcomponentBuilder();
            }
          };

      this.bindAndroidInjectorFactoryProvider3 =
          (Provider) favouriteProfileFragmentSubcomponentBuilderProvider;

      this.notificationFragmentSubcomponentBuilderProvider =
          new dagger.internal.Factory<
              FragmentBuilder_BindNotificationFragment.NotificationFragmentSubcomponent.Builder>() {
            @Override
            public FragmentBuilder_BindNotificationFragment.NotificationFragmentSubcomponent.Builder
                get() {
              return new NotificationFragmentSubcomponentBuilder();
            }
          };

      this.bindAndroidInjectorFactoryProvider4 =
          (Provider) notificationFragmentSubcomponentBuilderProvider;

      this.feedFragmentSubcomponentBuilderProvider =
          new dagger.internal.Factory<
              FragmentBuilder_BindFeedFragment.FeedFragmentSubcomponent.Builder>() {
            @Override
            public FragmentBuilder_BindFeedFragment.FeedFragmentSubcomponent.Builder get() {
              return new FeedFragmentSubcomponentBuilder();
            }
          };

      this.bindAndroidInjectorFactoryProvider5 = (Provider) feedFragmentSubcomponentBuilderProvider;

      this.mapOfClassOfAndProviderOfFactoryOfProvider =
          MapProviderFactory
              .<Class<? extends android.support.v4.app.Fragment>,
                  AndroidInjector.Factory<? extends android.support.v4.app.Fragment>>
                  builder(5)
              .put(UserFragment.class, bindAndroidInjectorFactoryProvider)
              .put(SearchFragment.class, bindAndroidInjectorFactoryProvider2)
              .put(FavouriteProfileFragment.class, bindAndroidInjectorFactoryProvider3)
              .put(NotificationFragment.class, bindAndroidInjectorFactoryProvider4)
              .put(FeedFragment.class, bindAndroidInjectorFactoryProvider5)
              .build();

      this.dispatchingAndroidInjectorProvider =
          DispatchingAndroidInjector_Factory.create(mapOfClassOfAndProviderOfFactoryOfProvider);

      this.universalActivityMembersInjector =
          UniversalActivity_MembersInjector.create(
              dispatchingAndroidInjectorProvider,
              DaggerAppComponent.this.dispatchingAndroidInjectorProvider3,
              DaggerAppComponent.this.createViewModelFactoryProvider,
              DaggerAppComponent.this.seedInstanceProvider);
    }

    @Override
    public void inject(UniversalActivity arg0) {
      universalActivityMembersInjector.injectMembers(arg0);
    }

    private final class UserFragmentSubcomponentBuilder
        extends FragmentBuilder_BindUserFragment.UserFragmentSubcomponent.Builder {
      private UserFragment seedInstance;

      @Override
      public FragmentBuilder_BindUserFragment.UserFragmentSubcomponent build() {
        if (seedInstance == null) {
          throw new IllegalStateException(UserFragment.class.getCanonicalName() + " must be set");
        }
        return new UserFragmentSubcomponentImpl(this);
      }

      @Override
      public void seedInstance(UserFragment arg0) {
        this.seedInstance = Preconditions.checkNotNull(arg0);
      }
    }

    private final class UserFragmentSubcomponentImpl
        implements FragmentBuilder_BindUserFragment.UserFragmentSubcomponent {
      private MembersInjector<UserFragment> userFragmentMembersInjector;

      private UserFragmentSubcomponentImpl(UserFragmentSubcomponentBuilder builder) {
        assert builder != null;
        initialize(builder);
      }

      @SuppressWarnings("unchecked")
      private void initialize(final UserFragmentSubcomponentBuilder builder) {

        this.userFragmentMembersInjector =
            UserFragment_MembersInjector.create(
                UniversalActivitySubcomponentImpl.this.dispatchingAndroidInjectorProvider,
                DaggerAppComponent.this.createViewModelFactoryProvider);
      }

      @Override
      public void inject(UserFragment arg0) {
        userFragmentMembersInjector.injectMembers(arg0);
      }
    }

    private final class SearchFragmentSubcomponentBuilder
        extends FragmentBuilder_BindSearchFragment.SearchFragmentSubcomponent.Builder {
      private SearchFragment seedInstance;

      @Override
      public FragmentBuilder_BindSearchFragment.SearchFragmentSubcomponent build() {
        if (seedInstance == null) {
          throw new IllegalStateException(SearchFragment.class.getCanonicalName() + " must be set");
        }
        return new SearchFragmentSubcomponentImpl(this);
      }

      @Override
      public void seedInstance(SearchFragment arg0) {
        this.seedInstance = Preconditions.checkNotNull(arg0);
      }
    }

    private final class SearchFragmentSubcomponentImpl
        implements FragmentBuilder_BindSearchFragment.SearchFragmentSubcomponent {
      private MembersInjector<SearchFragment> searchFragmentMembersInjector;

      private SearchFragmentSubcomponentImpl(SearchFragmentSubcomponentBuilder builder) {
        assert builder != null;
        initialize(builder);
      }

      @SuppressWarnings("unchecked")
      private void initialize(final SearchFragmentSubcomponentBuilder builder) {

        this.searchFragmentMembersInjector =
            SearchFragment_MembersInjector.create(
                UniversalActivitySubcomponentImpl.this.dispatchingAndroidInjectorProvider,
                DaggerAppComponent.this.createViewModelFactoryProvider);
      }

      @Override
      public void inject(SearchFragment arg0) {
        searchFragmentMembersInjector.injectMembers(arg0);
      }
    }

    private final class FavouriteProfileFragmentSubcomponentBuilder
        extends FragmentBuilder_BindFavouriteProfileFragment.FavouriteProfileFragmentSubcomponent
            .Builder {
      private FavouriteProfileFragment seedInstance;

      @Override
      public FragmentBuilder_BindFavouriteProfileFragment.FavouriteProfileFragmentSubcomponent
          build() {
        if (seedInstance == null) {
          throw new IllegalStateException(
              FavouriteProfileFragment.class.getCanonicalName() + " must be set");
        }
        return new FavouriteProfileFragmentSubcomponentImpl(this);
      }

      @Override
      public void seedInstance(FavouriteProfileFragment arg0) {
        this.seedInstance = Preconditions.checkNotNull(arg0);
      }
    }

    private final class FavouriteProfileFragmentSubcomponentImpl
        implements FragmentBuilder_BindFavouriteProfileFragment
            .FavouriteProfileFragmentSubcomponent {
      private MembersInjector<FavouriteProfileFragment> favouriteProfileFragmentMembersInjector;

      private FavouriteProfileFragmentSubcomponentImpl(
          FavouriteProfileFragmentSubcomponentBuilder builder) {
        assert builder != null;
        initialize(builder);
      }

      @SuppressWarnings("unchecked")
      private void initialize(final FavouriteProfileFragmentSubcomponentBuilder builder) {

        this.favouriteProfileFragmentMembersInjector =
            FavouriteProfileFragment_MembersInjector.create(
                UniversalActivitySubcomponentImpl.this.dispatchingAndroidInjectorProvider,
                DaggerAppComponent.this.createViewModelFactoryProvider);
      }

      @Override
      public void inject(FavouriteProfileFragment arg0) {
        favouriteProfileFragmentMembersInjector.injectMembers(arg0);
      }
    }

    private final class NotificationFragmentSubcomponentBuilder
        extends FragmentBuilder_BindNotificationFragment.NotificationFragmentSubcomponent.Builder {
      private NotificationFragment seedInstance;

      @Override
      public FragmentBuilder_BindNotificationFragment.NotificationFragmentSubcomponent build() {
        if (seedInstance == null) {
          throw new IllegalStateException(
              NotificationFragment.class.getCanonicalName() + " must be set");
        }
        return new NotificationFragmentSubcomponentImpl(this);
      }

      @Override
      public void seedInstance(NotificationFragment arg0) {
        this.seedInstance = Preconditions.checkNotNull(arg0);
      }
    }

    private final class NotificationFragmentSubcomponentImpl
        implements FragmentBuilder_BindNotificationFragment.NotificationFragmentSubcomponent {
      private MembersInjector<NotificationFragment> notificationFragmentMembersInjector;

      private NotificationFragmentSubcomponentImpl(
          NotificationFragmentSubcomponentBuilder builder) {
        assert builder != null;
        initialize(builder);
      }

      @SuppressWarnings("unchecked")
      private void initialize(final NotificationFragmentSubcomponentBuilder builder) {

        this.notificationFragmentMembersInjector =
            NotificationFragment_MembersInjector.create(
                UniversalActivitySubcomponentImpl.this.dispatchingAndroidInjectorProvider,
                DaggerAppComponent.this.createViewModelFactoryProvider);
      }

      @Override
      public void inject(NotificationFragment arg0) {
        notificationFragmentMembersInjector.injectMembers(arg0);
      }
    }

    private final class FeedFragmentSubcomponentBuilder
        extends FragmentBuilder_BindFeedFragment.FeedFragmentSubcomponent.Builder {
      private FeedFragment seedInstance;

      @Override
      public FragmentBuilder_BindFeedFragment.FeedFragmentSubcomponent build() {
        if (seedInstance == null) {
          throw new IllegalStateException(FeedFragment.class.getCanonicalName() + " must be set");
        }
        return new FeedFragmentSubcomponentImpl(this);
      }

      @Override
      public void seedInstance(FeedFragment arg0) {
        this.seedInstance = Preconditions.checkNotNull(arg0);
      }
    }

    private final class FeedFragmentSubcomponentImpl
        implements FragmentBuilder_BindFeedFragment.FeedFragmentSubcomponent {
      private MembersInjector<FeedFragment> feedFragmentMembersInjector;

      private FeedFragmentSubcomponentImpl(FeedFragmentSubcomponentBuilder builder) {
        assert builder != null;
        initialize(builder);
      }

      @SuppressWarnings("unchecked")
      private void initialize(final FeedFragmentSubcomponentBuilder builder) {

        this.feedFragmentMembersInjector =
            FeedFragment_MembersInjector.create(
                UniversalActivitySubcomponentImpl.this.dispatchingAndroidInjectorProvider,
                DaggerAppComponent.this.createViewModelFactoryProvider);
      }

      @Override
      public void inject(FeedFragment arg0) {
        feedFragmentMembersInjector.injectMembers(arg0);
      }
    }
  }

  private final class AdminMainActivitySubcomponentBuilder
      extends ActivityBuilder_BindAdminMainActivity.AdminMainActivitySubcomponent.Builder {
    private AdminMainActivity seedInstance;

    @Override
    public ActivityBuilder_BindAdminMainActivity.AdminMainActivitySubcomponent build() {
      if (seedInstance == null) {
        throw new IllegalStateException(
            AdminMainActivity.class.getCanonicalName() + " must be set");
      }
      return new AdminMainActivitySubcomponentImpl(this);
    }

    @Override
    public void seedInstance(AdminMainActivity arg0) {
      this.seedInstance = Preconditions.checkNotNull(arg0);
    }
  }

  private final class AdminMainActivitySubcomponentImpl
      implements ActivityBuilder_BindAdminMainActivity.AdminMainActivitySubcomponent {
    private MembersInjector<AdminMainActivity> adminMainActivityMembersInjector;

    private AdminMainActivitySubcomponentImpl(AdminMainActivitySubcomponentBuilder builder) {
      assert builder != null;
      initialize(builder);
    }

    @SuppressWarnings("unchecked")
    private void initialize(final AdminMainActivitySubcomponentBuilder builder) {

      this.adminMainActivityMembersInjector =
          AdminMainActivity_MembersInjector.create(
              DaggerAppComponent.this.dispatchingAndroidInjectorProvider6,
              DaggerAppComponent.this.dispatchingAndroidInjectorProvider3,
              DaggerAppComponent.this.createViewModelFactoryProvider,
              DaggerAppComponent.this.seedInstanceProvider);
    }

    @Override
    public void inject(AdminMainActivity arg0) {
      adminMainActivityMembersInjector.injectMembers(arg0);
    }
  }
}
