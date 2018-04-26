package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateModel;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentListRateBinding;

import javax.inject.Inject;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

/**
 * Created by WhoAmI on 04/04/2018.
 */

public class ListRateFragment extends BaseFragment<ListRateViewModel,FragmentListRateBinding> implements RateAdapter.ClickRateCallBack{

    private static String KEY_PROFILE_MODEL = "key profile model";

    public static ListRateFragment newInstance(ProfileModel model){
        ListRateFragment fragment = new ListRateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROFILE_MODEL,model);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ListRateViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(ListRateViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_rate;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpListRateAdapter();
        mViewModel.setIdUser(((ProfileModel)getArguments().getParcelable(KEY_PROFILE_MODEL)).getIdprofile());
        return view;
    }

    @Inject
    RateAdapter rateAdapter;

    @Inject
    DividerItemDecoration dividerItemDecoration;

    private void setUpListRateAdapter() {
        mFragmentBinding.recycleViewRate.setAdapter(rateAdapter);
        mFragmentBinding.recycleViewRate.addItemDecoration(dividerItemDecoration);
        mFragmentBinding.recycleViewRate.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(lastPosition== rateAdapter.getItemCount()-1){
                    mViewModel.loadNextPage();
                }
            }
        });
        mViewModel.getResourceLiveData().observe(this,listResource -> {
            rateAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
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
    public void clickRateItem(RateModel rateModel) {

    }
}
