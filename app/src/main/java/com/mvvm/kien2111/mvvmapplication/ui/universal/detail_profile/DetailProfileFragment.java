package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentDetailprofileBinding;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailProfileFragment extends BaseFragment<DetailProfileViewModel,FragmentDetailprofileBinding> {

    @Override
    protected DetailProfileViewModel createViewModel() {
        return ViewModelProviders.of(this).get(DetailProfileViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detailprofile;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }
}
