package com.mvvm.kien2111.fastjob.ui.admin.user;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.ActivityAdminManageUserBinding;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.addnewuser.AddUserActivity;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser.AllUserFragment;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.upgradeuser.UngradeUserFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 3/6/2018.
 */

public class ManageUserActivity extends BaseActivity<ManageUserViewModel, ActivityAdminManageUserBinding> implements IManageUserNagivator, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    public AllUserFragment allUserFragment;
    public UngradeUserFragment ungradeUserFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_manage_user;
    }

    @Override
    protected ManageUserViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(ManageUserViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBottomnagivation();
        setupTabLayout();
    }

    //using event bus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {

    }

    //setup bottom nagivationview
    public void setupBottomnagivation() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mActivityBinding.mbottomNavigation.getChildAt(0);
        Field shiftingMode = null;
        try {
            shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        shiftingMode.setAccessible(true);
        try {
            shiftingMode.setBoolean(menuView, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mActivityBinding.mbottomNavigation.getChildAt(0).findViewById(R.id.action_schedules).setVisibility(View.VISIBLE);
        mActivityBinding.mbottomNavigation.getChildAt(0).findViewById(R.id.action_unlock).setVisibility(View.GONE);
        shiftingMode.setAccessible(false);
        mActivityBinding.mbottomNavigation.setOnNavigationItemSelectedListener(this);
        //mActivityBinding.mbottomNavigationUnblock.setOnNavigationItemSelectedListener(this);
    }

    //setup viewpager and tablayout
    public void setupTabLayout() {
        /*if (mActivityBinding.toolbar != null) {
            setSupportActionBar(mActivityBinding.toolbar);
        }*/
        allUserFragment = new AllUserFragment();
        ungradeUserFragment = new UngradeUserFragment();
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(allUserFragment);
        sectionPagerAdapter.addFragment(ungradeUserFragment);
        mActivityBinding.mpager.setAdapter(sectionPagerAdapter);
        mActivityBinding.mtabLayout.setupWithViewPager(mActivityBinding.mpager);
        //mActivityBinding.toolbar.setVisibility(View.GONE);
        mActivityBinding.mpager.addOnPageChangeListener(this);//onpage view pager
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_schedules:
                //item.setIcon(R.drawable.icon_admin_block);
                callBlockUser();
                break;
            case R.id.action_unlock:
                //item.setIcon(R.drawable.icon_admin_unblock);
                callUnlockUer();
                break;
        }
        return false;
    }

    //fun callUnlockUser
    void callUnlockUer(){
        if(allUserFragment!=null){
            ungradeUserFragment.callUnlockUser();
        }
    }

    //function block user
    void callBlockUser() {
        //mViewModel.gotoBlockUser();
        if (allUserFragment != null) {
            allUserFragment.callBlockUser();
        }
    }

    //event scroll viewpager
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position==0)
        {
            mActivityBinding.mbottomNavigation.getChildAt(0).findViewById(R.id.action_schedules).setVisibility(View.VISIBLE);
            mActivityBinding.mbottomNavigation.getChildAt(0).findViewById(R.id.action_unlock).setVisibility(View.GONE);
            /*mActivityBinding.mbottomNavigation.setVisibility(View.VISIBLE);
            mActivityBinding.mbottomNavigationUnblock.setVisibility(View.GONE);*/
        }
        else{
            mActivityBinding.mbottomNavigation.getChildAt(0).findViewById(R.id.action_schedules).setVisibility(View.GONE);
            mActivityBinding.mbottomNavigation.getChildAt(0).findViewById(R.id.action_unlock).setVisibility(View.VISIBLE);
            /*mActivityBinding.mbottomNavigation.setVisibility(View.GONE);
            mActivityBinding.mbottomNavigationUnblock.setVisibility(View.VISIBLE);*/
        }
    }

    @Override
    public void onPageSelected(int position) {}//scroll viewpager

    @Override
    public void onPageScrollStateChanged(int state) {}//event viewpager

    //Custom viewpager and tablayout
    static class SectionPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;

        public SectionPagerAdapter(FragmentManager fm ) {
            super(fm);
            fragmentList = new ArrayList<>();
        }

        public void addFragment(Fragment fragment){
            if(fragment!=null){
                fragmentList.add(fragment);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All Account";
                case 1:
                default:
                    return "Block Account";
            }
        }
    }

    //set new user
    public void gotoAddnewUser(View view) {
        Intent inten = new Intent(this, AddUserActivity.class);
        startActivityForResult(inten,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111){
            allUserFragment.getData();
        }
    }

    //finish activity
    public void finish(View view){
        finish();
    }

}
