package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.edituser;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminEditProfileUserBinding;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by donki on 4/25/2018.
 */

public class UserEditProfileActivity extends BaseActivity<UserEditProfileViewModel, ActivityAdminEditProfileUserBinding> {
    User user= new User();
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_edit_profile_user;
    }

    @Override
    protected UserEditProfileViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(UserEditProfileViewModel.class);
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
        mActivityBinding.setUser( getDataBundle());
    }

    //using event bus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {

    }
    void callUpdateUser(){
        user.setUserName(String.valueOf(mActivityBinding.edtUsername.getText()));
        user.setUserName(String.valueOf(mActivityBinding.edtEmail.getText()));
        user.setUserName(String.valueOf(mActivityBinding.edtPassword.getText()));
        user.setUserName(String.valueOf(mActivityBinding.tvBirthday.getText()));

        mViewModel.callUpdteUser(user);
    }
    public User getDataBundle(){
        User user= new User();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        user= bundle.getParcelable("user");
        Log.d("data",user.getBirthday().toString());
        return  user;
    }
}
