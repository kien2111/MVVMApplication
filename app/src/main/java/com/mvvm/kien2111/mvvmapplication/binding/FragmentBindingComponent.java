package com.mvvm.kien2111.mvvmapplication.binding;

import android.databinding.DataBindingComponent;
import android.support.v4.app.Fragment;

/**
 * Created by WhoAmI on 16/02/2018.
 */

public class FragmentBindingComponent implements android.databinding.DataBindingComponent {
    private final FragmentBindingAdapter fragmentBindingAdapter;
    public FragmentBindingComponent(Fragment fragment){
        this.fragmentBindingAdapter = new FragmentBindingAdapter(fragment);
    }
    @Override
    public FragmentBindingAdapter getFragmentBindingAdapter() {
        return fragmentBindingAdapter;
    }

}
