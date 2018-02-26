package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mvvm.kien2111.mvvmapplication.AppViewModelFactory;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by WhoAmI on 05/02/2018.
 */
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpViewModel(SignUpViewModel signUpViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UniversalViewModel.class)
    abstract ViewModel bindUniversalViewModel(UniversalViewModel universalViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel.class)
    abstract ViewModel bindFeedViewModel(FeedViewModel feedViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel.class)
    abstract ViewModel bindNotificationViewModel(NotificationViewModel notificationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteProfileViewModel.class)
    abstract ViewModel bindFavouriteProfileViewModel(FavouriteProfileViewModel favouriteProfileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AdminMainViewModel.class)
    abstract ViewModel bindAdminViewModel(AdminMainViewModel adminMainViewModel);

    @Binds
    @Singleton
    abstract ViewModelProvider.Factory createViewModelFactory(AppViewModelFactory appViewModelFactory);
}
