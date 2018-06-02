package com.mvvm.kien2111.fastjob.ui.universal.search;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseAdapter;
import com.mvvm.kien2111.fastjob.base.BaseViewHolder;
import com.mvvm.kien2111.fastjob.binding.FragmentBindingComponent;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.fastjob.databinding.RecentSearchItemBinding;

/**
 * Created by WhoAmI on 06/05/2018.
 */

public class RecentSearchAdapter extends BaseAdapter<RecentSearch,RecentSearchItemBinding>{

    private final FragmentBindingComponent fragmentBindingComponent;
    private ListenClickRecentSearch callback;
    public interface ListenClickRecentSearch{
        void onClickRecentSearch(RecentSearch recentSearch);
    }


    public RecentSearchAdapter(FragmentBindingComponent fragmentBindingComponent,ListenClickRecentSearch callback){
        this.fragmentBindingComponent = fragmentBindingComponent;
        this.callback = callback;
    }

    @Override
    protected BaseViewHolder<RecentSearchItemBinding> instantiateViewHolder(RecentSearchItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected RecentSearchItemBinding createBinding(ViewGroup parent) {
        RecentSearchItemBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recent_search_item,parent,false,fragmentBindingComponent);
        mBinding.getRoot().setOnClickListener(v->{
            if(callback!=null){
                callback.onClickRecentSearch(mBinding.getRecentitem());
            }
        });
        return mBinding;
    }

    @Override
    protected void bind(RecentSearchItemBinding mBinding, RecentSearch item) {
        mBinding.setRecentitem(item);
        mBinding.executePendingBindings();
    }

    @Override
    protected boolean areContentsTheSame(RecentSearch olditem, RecentSearch newitem) {
        return false;
    }

    @Override
    protected boolean areItemsTheSame(RecentSearch olditem, RecentSearch newitem) {
        return false;
    }
}
