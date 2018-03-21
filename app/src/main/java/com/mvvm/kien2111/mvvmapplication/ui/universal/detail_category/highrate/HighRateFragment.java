package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.HighRateProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentHighrateBinding;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;

import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 16/03/2018.
 */

public class HighRateFragment extends BaseFragment<HighRateViewModel,FragmentHighrateBinding> implements HighRateProfileAdapter.ProfileClickCallback {

    public final static String KEY_PICK_CATEGORY = "key_pick_category";

    @Inject
    HighRateProfileAdapter highRateProfileAdapter;

    @Inject
    NavigationController navigationController;

    @Override
    protected HighRateViewModel createViewModel() {
        return ViewModelProviders.of(this).get(HighRateViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_highrate;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    public static HighRateFragment newInstance(Category category){
        HighRateFragment highRateFragment = new HighRateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PICK_CATEGORY,category);
        highRateFragment.setArguments(bundle);
        return highRateFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null ){
            if(getArguments().getParcelable(KEY_PICK_CATEGORY)!=null){
                setUpProfileAdapter();
                mViewModel.setIdCategory(((Category)getArguments().getParcelable(KEY_PICK_CATEGORY)).getIdcategory());
            }
        }else{
            navigationController.navigateToFeed();
        }
    }

    private void setUpProfileAdapter() {
        mFragmentBinding.profileAdapterRecycleview.setAdapter(highRateProfileAdapter);
        mFragmentBinding.profileAdapterRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(lastPosition==highRateProfileAdapter.getItemCount()-1){
                    mViewModel.loadNextPage();
                }
            }
        });
        mViewModel.getResourceListProfileMutableLiveData().observe(this,listResource -> {
            highRateProfileAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
            mFragmentBinding.executePendingBindings();
        });
        mViewModel.getLoadMoreStateLiveData().observe(this,loadMoreState -> {
            if(loadMoreState==null){
                mFragmentBinding.setLoadingMoreState(false);
            }else{
                mFragmentBinding.setLoadingMoreState(loadMoreState.isRunning);
            }
            mFragmentBinding.executePendingBindings();
        });
    }

    @Override
    public void onClick(HighRateProfileModel highRateProfileModel) {

    }
}
