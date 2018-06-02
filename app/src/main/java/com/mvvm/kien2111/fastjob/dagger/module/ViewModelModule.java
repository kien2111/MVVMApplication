package com.mvvm.kien2111.fastjob.dagger.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mvvm.kien2111.fastjob.AppViewModelFactory;
import com.mvvm.kien2111.fastjob.ui.admin.main.AdminMainViewModel;
import com.mvvm.kien2111.fastjob.ui.admin.profile.AdminManageProfileViewModel;
import com.mvvm.kien2111.fastjob.ui.admin.statistical.AdminStatisticalViewModel;
import com.mvvm.kien2111.fastjob.ui.admin.user.ManageUserViewModel;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser.AllUserViewModel;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.upgradeuser.UngradeUserViewModel;
import com.mvvm.kien2111.fastjob.ui.createappointment.CreateAppointmentViewModel;
import com.mvvm.kien2111.fastjob.ui.depositfund.DepositFundViewModel;
import com.mvvm.kien2111.fastjob.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.fastjob.ui.listappointment.historyappointment.HistoryAppointmentViewModel;
import com.mvvm.kien2111.fastjob.ui.listappointment.onprogressappointment.OnProgressAppointmentViewModel;
import com.mvvm.kien2111.fastjob.ui.login.LoginViewModel;
import com.mvvm.kien2111.fastjob.ui.signup.SignUpViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.UniversalViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.BottomSheetDialogFilterViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.DetailCategoryViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.DetailProfileViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.about.AboutViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.rate.ListRateViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.feed.FeedViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.feed.category.CategoryViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.feed.map.MapViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.search.SearchViewModel;
import com.mvvm.kien2111.fastjob.ui.universal.user.UserViewModel;
import com.mvvm.kien2111.fastjob.ui.upgrade.businessupgrade.BusinessUpgradeViewModel;
import com.mvvm.kien2111.fastjob.ui.upgrade.freelancerupgrade.FreelancerUpgradeViewModel;
import com.mvvm.kien2111.fastjob.ui.userprofile.UserProfileViewModel;
import com.mvvm.kien2111.fastjob.ui.userprofile.bussiness.BussinessProfileViewModel;
import com.mvvm.kien2111.fastjob.ui.userprofile.invidual.IndividualProfileViewModel;

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
    @IntoMap
    @ViewModelKey(CreateAppointmentViewModel.class)
    abstract ViewModel bindCreateAppointmentViewModel(CreateAppointmentViewModel createAppointmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DepositFundViewModel.class)
    abstract ViewModel bindDepositFundViewModel(DepositFundViewModel depositFundViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FreelancerUpgradeViewModel.class)
    abstract ViewModel bindFreelancerUpgradeViewModel(FreelancerUpgradeViewModel freelancerUpgradeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BusinessUpgradeViewModel.class)
    abstract ViewModel bindBusinessUpgradeViewModel(BusinessUpgradeViewModel businessUpgradeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel.class)
    abstract ViewModel bindMapViewModel(MapViewModel mapViewModel);

    @Binds
    @Singleton
    abstract ViewModelProvider.Factory createViewModelFactory(AppViewModelFactory appViewModelFactory);

}
