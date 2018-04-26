package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mvvm.kien2111.mvvmapplication.AppViewModelFactory;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.admin.profile.AdminManageProfileViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.admin.statistical.AdminStatisticalViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.ManageUserViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser.AllUserViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.upgradeuser.UngradeUserViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment.HistoryAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment.OnProgressAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.BottomSheetDialogFilterViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.DetailCategoryViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.DetailProfileViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.about.AboutViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate.ListRateViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category.CategoryViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.bussiness.BussinessProfileViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.IndividualProfileViewModel;

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
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AdminMainViewModel.class)
    abstract ViewModel bindAdminViewModel(AdminMainViewModel adminMainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ManageUserViewModel.class)
    abstract ViewModel bindManageUserViewModel(ManageUserViewModel manageUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AllUserViewModel.class)
    abstract ViewModel bindAllUserViewModel(AllUserViewModel allUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UngradeUserViewModel.class)
    abstract ViewModel bindUngradeUserViewModel(UngradeUserViewModel allUngradeUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AdminStatisticalViewModel.class)
    abstract ViewModel bindAdminStatisticalViewModel(AdminStatisticalViewModel allAdminStatisticalViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AdminManageProfileViewModel.class)
    abstract ViewModel bindAdminManageProfileViewModel(AdminManageProfileViewModel allAdminManageProfileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel.class)
    abstract ViewModel bindCatogoryViewModel(CategoryViewModel categoryViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(DetailCategoryViewModel.class)
    abstract ViewModel bindDetailCategoryViewModel(DetailCategoryViewModel detailCategoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailProfileViewModel.class)
    abstract ViewModel bindDetailProfileViewModel(DetailProfileViewModel detailProfileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListRateViewModel.class)
    abstract ViewModel bindListRateViewModel(ListRateViewModel listRateViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel.class)
    abstract ViewModel bindAboutViewModel(AboutViewModel aboutViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListAppointmentViewModel.class)
    abstract ViewModel bindListAppointmentViewModel(ListAppointmentViewModel listAppointmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OnProgressAppointmentViewModel.class)
    abstract ViewModel bindOnProgressAppointmentViewModel(OnProgressAppointmentViewModel onProgressAppointmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryAppointmentViewModel.class)
    abstract ViewModel bindHistoryAppointmentViewModel(HistoryAppointmentViewModel historyAppointmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel.class)
    abstract ViewModel bindUserProfileViewModel(UserProfileViewModel userProfileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(IndividualProfileViewModel.class)
    abstract ViewModel bindIndividualProfileViewModel(IndividualProfileViewModel individualProfileViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(BottomSheetDialogFilterViewModel.class)
    abstract ViewModel bindBottomSheetDialogFilterViewModel(BottomSheetDialogFilterViewModel bottomSheetDialogFilterViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BussinessProfileViewModel.class)
    abstract ViewModel bindBussinessProfileViewModel(BussinessProfileViewModel bussinessProfileViewModel);

    @Binds
    @Singleton
    abstract ViewModelProvider.Factory createViewModelFactory(AppViewModelFactory appViewModelFactory);

}
