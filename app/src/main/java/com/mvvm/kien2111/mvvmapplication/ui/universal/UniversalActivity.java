package com.mvvm.kien2111.mvvmapplication.ui.universal;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityUniversalBinding;
import com.mvvm.kien2111.mvvmapplication.exception.ApiException;
import com.mvvm.kien2111.mvvmapplication.model.ErrorResponse;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.DetailCategoryFragment;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by WhoAmI on 31/01/2018.
 */

public class UniversalActivity extends BaseActivity<UniversalViewModel,ActivityUniversalBinding> {

    @Inject
    NavigationController navigationController;

    private ActionBarDrawerToggle drawerToggle;

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
        setupToolbar();
        setupNavigationBottomBar();
    }

    public void onClickViewAppointment(View v){
        Intent intent = new Intent(this, ListAppointmentActivity.class);
        startActivity(intent);
    }

    private void setupToolbar() {
        setSupportActionBar(mActivityBinding.toolbar);
        drawerToggle = new ActionBarDrawerToggle(this,mActivityBinding.drawerlayout,mActivityBinding.toolbar,R.string.drawer_open,R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
        drawerToggle.setToolbarNavigationClickListener(v -> {
            onBackPressed();
        });
        mActivityBinding.drawerlayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //Toast.makeText(this,"shit",Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private void setupNavigationBottomBar() {
        mActivityBinding.bnve.setOnNavigationItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case R.id.action_home:navigationController.navigateToFeed(); return true;
                case R.id.action_search:navigationController.navigateToSearch();return true;
                case R.id.action_setting:navigationController.navigateToUser();return true;
            }
            return false;
        });
    }

}
