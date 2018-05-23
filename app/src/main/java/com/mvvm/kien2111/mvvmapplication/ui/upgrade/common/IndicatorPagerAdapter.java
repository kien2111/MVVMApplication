package com.mvvm.kien2111.mvvmapplication.ui.upgrade.common;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class IndicatorPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> lst_fragment;

    public IndicatorPagerAdapter(FragmentManager fm) {
        super(fm);
        lst_fragment = new ArrayList<>();
    }

    public void addFragment(Fragment fragment){
        lst_fragment.add(fragment);
    }



    @Override
    public Fragment getItem(int position) {
        return lst_fragment.get(position);
    }

    @Override
    public int getCount() {
        return lst_fragment.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


}
