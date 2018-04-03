package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.skilledfreelancer;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentSkilledfreelancerBinding;

/**
 * Created by WhoAmI on 25/03/2018.
 */

public class SkilledFreelancerFragment extends BaseFragment<SkilledFreelancerViewModel,FragmentSkilledfreelancerBinding> {
    private static final String KEY_CATEGORY = "KEY_CATEGORY";
    @Override
    protected SkilledFreelancerViewModel createViewModel() {
        return ViewModelProviders.of(this).get(SkilledFreelancerViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_skilledfreelancer;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMskilledfreelancer;
    }

    public static SkilledFreelancerFragment newInstance(Category category) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CATEGORY,category);
        SkilledFreelancerFragment fragment = new SkilledFreelancerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
