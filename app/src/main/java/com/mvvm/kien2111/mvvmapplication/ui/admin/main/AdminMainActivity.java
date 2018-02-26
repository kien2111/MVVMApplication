package com.mvvm.kien2111.mvvmapplication.ui.admin.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminHomeBinding;

/**
 * Created by VAKHANHPR on 2/26/2018.
 */

public class AdminMainActivity extends BaseActivity<AdminMainViewModel,ActivityAdminHomeBinding> {

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void setupRecycleViewUser(){

    }
}
