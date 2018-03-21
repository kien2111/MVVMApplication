package com.mvvm.kien2111.mvvmapplication.ui.admin.user;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminManageUserBinding;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser.AllUserFragment;
import com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.upgradeuser.UngradeUserFragment;

import java.lang.reflect.Field;

/**
 * Created by donki on 3/6/2018.
 */

public class ManageUserActivity extends BaseActivity<ManageUserViewModel,ActivityAdminManageUserBinding> implements IManageUserNagivator {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_manage_user;
    }

    @Override
    protected ManageUserViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(ManageUserViewModel.class);
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
        setupBottomnagivation();
        setupTabLayout();

    }

    //setup bottom nagivationview
    public void setupBottomnagivation() {

        Menu mmenu = mActivityBinding.mbottomNavigation.getMenu();

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mActivityBinding.mbottomNavigation.getChildAt(0);
            Field shiftingMode = null;
            try
            {
                shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
            shiftingMode.setAccessible(true);
            try
            {
                shiftingMode.setBoolean(menuView, false);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            shiftingMode.setAccessible(false);

        mActivityBinding.mbottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.action_favorites:
                                item.setIcon(R.drawable.icon_admin_delete);
                                break;
                            case R.id.action_schedules:
                                item.setIcon(R.drawable.icon_admin_block);
                                break;
                            case  R.id.action_promoted:
                                item.setIcon(R.drawable.icon_admin_promoted);
                                break;
                        }
                        return true;
                    }
                });
    }
    //setup viewpager and tablayout
    public void setupTabLayout()
    {
        if(mActivityBinding.toolbar!=null)
        {
            setSupportActionBar(mActivityBinding.toolbar);
        }
        mActivityBinding.mpager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        mActivityBinding.mtabLayout.setupWithViewPager(mActivityBinding.mpager);
        mActivityBinding.toolbar.setVisibility(View.GONE);
    }
    //Custom viewpager and tablayout
    static class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AllUserFragment();
                case 1:
                default:
                    return new UngradeUserFragment();
            }
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All Account";
                case 1:
                default:
                    return "Ungrade Account";
            }
        }
    }

}
