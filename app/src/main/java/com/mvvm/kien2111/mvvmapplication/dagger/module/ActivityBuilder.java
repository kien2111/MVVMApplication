package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.searchresult.SearchActivity;
import com.mvvm.kien2111.mvvmapplication.ui.SplashActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.profile.AdminManageProfileActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.profile.AdminManageProfileModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.statistical.AdminStatisticalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.statistical.AdminStatisticalModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.ManageUserActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.ManageUserModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.addnewuser.AddUserActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.addnewuser.AddUserModule;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.edituser.UserEditProfileActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.edituser.UserEditProfileModule;
import com.mvvm.kien2111.mvvmapplication.ui.createappointment.CreateAppointmentActivity;
import com.mvvm.kien2111.mvvmapplication.ui.createappointment.CreateAppointmentModule;
import com.mvvm.kien2111.mvvmapplication.ui.depositfund.DepositFundActivity;
import com.mvvm.kien2111.mvvmapplication.ui.depositfund.DepositFundModule;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentActivity;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentModule;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginModule;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpActivity;
import com.mvvm.kien2111.mvvmapplication.ui.signup.SignUpModule;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalModule;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchModule;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.searchresult.SearchResultModule;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.businessupgrade.BusinessUpgradeActivity;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.businessupgrade.BusinessUpgradeModule;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.freelancerupgrade.FreelancerUpgradeActivity;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.freelancerupgrade.FreelancerUpgradeModule;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 27/01/2018.
 */

@Module
public abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = SignUpModule.class)
    abstract SignUpActivity bindSignUpActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {UniversalModule.class,FragmentBuilder.class})
    abstract UniversalActivity bindUniversalActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AdminMainModule.class})
    abstract  AdminMainActivity bindAdminMainActivity();

    @PerActivity
    @ContributesAndroidInjector (modules = {AdminStatisticalModule.class})
    abstract AdminStatisticalActivity binAdminStatisticalActivity();

    @PerActivity
    @ContributesAndroidInjector (modules = {AdminManageProfileModule.class})
    abstract AdminManageProfileActivity binAdminManageProfileActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AddUserModule.class})
    abstract AddUserActivity binAddUserActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {ManageUserModule.class,FragmentManageUserBuilder.class})
    abstract ManageUserActivity binManageUserActivity();

    @PerActivity
    @ContributesAndroidInjector (modules = {UserEditProfileModule.class})
    abstract UserEditProfileActivity binUserEditProfileActivity();

    @PerActivity
    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {ListAppointmentModule.class,ChildListAppointmentBuilder.class})
    abstract ListAppointmentActivity bindListAppointmentActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {UserProfileModule.class,ChildUserProfileBuilder.class})
    abstract UserProfileActivity bindUserProfileActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {CreateAppointmentModule.class})
    abstract CreateAppointmentActivity bindCreateAppointmentActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {DepositFundModule.class})
    abstract DepositFundActivity bindDepositFundActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {FreelancerUpgradeModule.class,ChildUpgradeProfileBuilder.class})
    abstract FreelancerUpgradeActivity bindFreelancerActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {BusinessUpgradeModule.class})
    abstract BusinessUpgradeActivity bindBusinessUpgradeActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {SearchResultModule.class})
    abstract SearchActivity bindSearchActivity();
}
