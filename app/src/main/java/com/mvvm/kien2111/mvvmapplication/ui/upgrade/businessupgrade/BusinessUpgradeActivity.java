package com.mvvm.kien2111.mvvmapplication.ui.upgrade.businessupgrade;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityBusinessUpgradeBinding;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class BusinessUpgradeActivity extends BaseActivity<BusinessUpgradeViewModel,ActivityBusinessUpgradeBinding> {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_business_upgrade;
    }

    @Override
    protected BusinessUpgradeViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(BusinessUpgradeViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
