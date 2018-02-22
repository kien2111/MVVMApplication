package com.mvvm.kien2111.mvvmapplication.ui.universal.feed;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Injectable;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentFeedBinding;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class FeedFragment extends BaseFragment<FeedViewModel,FragmentFeedBinding> implements Injectable {
    @Override
    protected FeedViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(FeedViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMfeed;
    }
}
