package com.mvvm.kien2111.mvvmapplication.ui.universal.feed;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentFeedBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category.CategoryFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class FeedFragment extends BaseFragment<FeedViewModel,FragmentFeedBinding> {
    @Inject
    ViewPagerAdapter adapter;



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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpTabLayout();
        setUpToolbar();
        return view;
    }

    private void setUpToolbar(){
       UniversalActivity activity = (UniversalActivity)getActivity();
       activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
       activity.getSupportActionBar().setTitle("MVVMApplication");
       activity.getDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter.addFragment(new CategoryFragment(),"Categories");
    }


    private void setUpTabLayout(){

        mFragmentBinding.viewPager.setAdapter(adapter);
        mFragmentBinding.tabLayout.setupWithViewPager(mFragmentBinding.viewPager);
    }

}
