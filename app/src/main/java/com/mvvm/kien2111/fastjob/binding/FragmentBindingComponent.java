package com.mvvm.kien2111.fastjob.binding;

import android.support.v4.app.Fragment;

/**
 * Created by WhoAmI on 16/02/2018.
 */

public class FragmentBindingComponent implements android.databinding.DataBindingComponent {
    private final FragmentBindingAdapter fragmentBindingAdapter;
    private Fragment fragment;
    public FragmentBindingComponent(Fragment fragment){
        this.fragment = fragment;
        this.fragmentBindingAdapter = new FragmentBindingAdapter(fragment);
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public FragmentBindingAdapter getFragmentBindingAdapter() {
        return fragmentBindingAdapter;
    }

}
