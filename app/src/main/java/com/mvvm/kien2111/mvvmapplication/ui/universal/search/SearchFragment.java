package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Injectable;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentSearchBinding;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class SearchFragment extends BaseFragment<SearchViewModel,FragmentSearchBinding> implements Injectable {
    @Override
    protected SearchViewModel createViewModel() {
        return ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMsearch;
    }
}
