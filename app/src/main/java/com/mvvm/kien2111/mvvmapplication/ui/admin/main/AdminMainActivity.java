package com.mvvm.kien2111.mvvmapplication.ui.admin.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminHomeBinding;
import com.mvvm.kien2111.mvvmapplication.ui.admin.profile.AdminManageProfileActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.statistical.AdminStatisticalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.ManageUserActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.ManageUserViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by VAKHANHPR on 2/26/2018.
 */

public class AdminMainActivity extends BaseActivity<AdminMainViewModel,ActivityAdminHomeBinding > implements IAdminMainNavigator {


    @Override
    public void handleError(Throwable throwable) {

    }

    //go to activity Manage Account
    @Override
    public void gotomanagerAccount() {
        Intent mItent= new Intent(this, ManageUserActivity.class);
        startActivity(mItent);
    }

    //Go to activity Satistical
    @Override
    public void gotoSatisticalActivity() {

        Intent mItent= new Intent(this, AdminStatisticalActivity.class);
        startActivity(mItent);
    }

    //Go to activity edit myprofile
    @Override
    public void gotoManageMyProfile() {
        Intent mItent= new Intent(this, AdminManageProfileActivity.class);
        startActivity(mItent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof AdminMainViewModel.Message){

        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_home;
    }

    @Override
    protected AdminMainViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(AdminMainViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mViewModel.setmNavigator(this);
        mViewModel.getResourceMutableLiveData().observe(this,listResource -> {
            switch (listResource.status){
                case SUCCESS:
                    listResource.getData();
                    break;
                case LOADING:
                    break;
                case ERROR:
                    handleError(listResource.message);
                    break;
            }
        });

    }

    private void handleError(String message) {

    }
}
