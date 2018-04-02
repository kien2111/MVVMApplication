package com.mvvm.kien2111.mvvmapplication.ui.admin.profile;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminEditMyinforBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by donki on 3/11/2018.
 */

public class AdminManageProfileActivity extends BaseActivity<AdminManageProfileViewModel,ActivityAdminEditMyinforBinding>{
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_edit_myinfor;
    }

    @Override
    protected AdminManageProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(AdminManageProfileViewModel.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }
    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
