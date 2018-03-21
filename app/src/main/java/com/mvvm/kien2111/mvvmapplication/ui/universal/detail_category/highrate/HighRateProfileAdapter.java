package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.HighRateProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.HighrateprofileItemBinding;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class HighRateProfileAdapter extends BaseAdapter<HighRateProfileModel,HighrateprofileItemBinding> {

    FragmentBindingComponent dataBindingComponent;
    private final ProfileClickCallback callback;
    interface ProfileClickCallback{
        void onClick(HighRateProfileModel highRateProfileModel);
    }
    public HighRateProfileAdapter(FragmentBindingComponent dataBindingComponent,ProfileClickCallback callback){
        this.callback = callback;
        this.dataBindingComponent = dataBindingComponent;
    }
    @Override
    protected BaseViewHolder<HighrateprofileItemBinding> instantiateViewHolder(HighrateprofileItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected HighrateprofileItemBinding createBinding(ViewGroup parent) {
        HighrateprofileItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.category_item,parent,false,dataBindingComponent);
        binding.getRoot().setOnClickListener(view->{
            if(callback!=null && binding.getProfile()!=null){
                callback.onClick(binding.getProfile());
            }
        });
        return binding;
    }

    @Override
    protected void bind(HighrateprofileItemBinding mBinding, HighRateProfileModel item) {
        mBinding.setProfile(item);
    }

    @Override
    protected boolean areContentsTheSame(HighRateProfileModel olditem, HighRateProfileModel newitem) {
        return olditem.getName().equals(newitem.getName());
    }

    @Override
    protected boolean areItemsTheSame(HighRateProfileModel olditem, HighRateProfileModel newitem) {
        return olditem.getName().equals(newitem.getName()) && olditem.getAvatar().equals(newitem.getAvatar())
                && olditem.getRating()==newitem.getRating();
    }
}
