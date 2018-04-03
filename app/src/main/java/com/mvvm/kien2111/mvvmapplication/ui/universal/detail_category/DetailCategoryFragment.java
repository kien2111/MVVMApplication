package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentDetailcategoryBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.skilledfreelancer.SkilledFreelancerFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.ViewPagerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category.CategoryFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailCategoryFragment extends BaseFragment<DetailCategoryViewModel,FragmentDetailcategoryBinding> {

    private static final String KEY_PICK_CATEGORY = "KEY_PICK_CATEGORY";
    public static DetailCategoryFragment newInstance(Category category){
        DetailCategoryFragment detailCategoryFragment = new DetailCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PICK_CATEGORY,category);
        detailCategoryFragment.setArguments(bundle);
        return detailCategoryFragment;
    }

    @Override
    protected DetailCategoryViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(DetailCategoryViewModel.class);
    }

    @Inject
    NavigationController navigationController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null && getArguments().getParcelable(KEY_PICK_CATEGORY)!=null){

        }else{
            navigationController.navigateToFeed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        setUpToolBar(getArguments().getParcelable(KEY_PICK_CATEGORY));
        setUpTabLayout(getArguments().getParcelable(KEY_PICK_CATEGORY));
        return view;
    }

    @Inject
    ViewPagerAdapter adapter;
    private void setUpToolBar(Category category){
        AppCompatActivity activity = (BaseActivity)getActivity();
        activity.setSupportActionBar(mFragmentBinding.toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmentBinding.toolbar.setTitle(category.getNamecategory());
    }
    private void setUpTabLayout(Category category){
        adapter.addFragment(HighRateFragment.newInstance(category),"High Rate");
        adapter.addFragment(SkilledFreelancerFragment.newInstance(category),"Skilled Freelancer");
        mFragmentBinding.viewPager.setAdapter(adapter);
        mFragmentBinding.tabLayout.setupWithViewPager(mFragmentBinding.viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detailcategory;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }
}
