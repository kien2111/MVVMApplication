package com.mvvm.kien2111.mvvmapplication.ui.universal;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityUniversalBinding;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by WhoAmI on 31/01/2018.
 */

public class UniversalActivity extends BaseActivity<UniversalViewModel,ActivityUniversalBinding> {

    @Inject
    NavigationController navigationController;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_universal;
    }

    @Override
    protected UniversalViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(UniversalViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
    }

    @Override
    protected int getBindVariable() {
        return BR.VMuniversal;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            navigationController.navigateToFeed();
        }

        setupNavigationBottomBar();
    }

    private void setupNavigationBottomBar() {
        mActivityBinding.bnve.enableAnimation(false);
        mActivityBinding.bnve.enableItemShiftingMode(false);
        mActivityBinding.bnve.enableShiftingMode(false);
        mActivityBinding.bnve.setOnNavigationItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case R.id.action_home:navigationController.navigateToFeed();return true;
                case R.id.action_favourite:navigationController.navigateToFavouriteProfile();return true;
                case R.id.action_notification:navigationController.navigateToNotification();return true;
                case R.id.action_search:navigationController.navigateToSearch();return true;
                case R.id.action_setting:navigationController.navigateToUser();return true;
            }
            return false;
        });
    }




}
