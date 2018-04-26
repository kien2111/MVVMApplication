package com.mvvm.kien2111.mvvmapplication.ui.universal.common;

import android.databinding.DataBindingComponent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 05/03/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> lstFragment;
    private List<String> lstTitle;
    private int mCurrentPosition = -1;
    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
        lstFragment = new ArrayList<>();
        lstTitle = new ArrayList<>();
    }
    public void addFragment(Fragment fragment,String tittle){
        lstFragment.add(fragment);
        lstTitle.add(tittle);
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitle.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            if(container instanceof WrapHeightViewPager){
                WrapHeightViewPager pager = (WrapHeightViewPager) container;
                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.measureCurrentView(fragment.getView());
                }
            }
        }
    }
}
