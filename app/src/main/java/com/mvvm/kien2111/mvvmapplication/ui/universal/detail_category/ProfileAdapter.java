package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.ProfileItemBinding;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class ProfileAdapter extends BaseAdapter<ProfileModel,ProfileItemBinding> {

    FragmentBindingComponent dataBindingComponent;
    private final ProfileClickCallback callback;
    interface ProfileClickCallback{
        void onClick(ProfileModel profileModel, ImageView sharedImageView);
    }
    public ProfileAdapter(FragmentBindingComponent dataBindingComponent, ProfileClickCallback callback){
        this.callback = callback;
        this.dataBindingComponent = dataBindingComponent;
    }
    @Override
    protected BaseViewHolder<ProfileItemBinding> instantiateViewHolder(ProfileItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected ProfileItemBinding createBinding(ViewGroup parent) {
        ProfileItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.profile_item,parent,false,dataBindingComponent);
        binding.getRoot().setOnClickListener(view->{
            if(callback!=null && binding.getProfile()!=null){
                callback.onClick(binding.getProfile(),binding.imageprofile);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ProfileItemBinding mBinding, ProfileModel item) {
        mBinding.setProfile(item);
        mBinding.executePendingBindings();
    }

    @Override
    protected boolean areContentsTheSame(ProfileModel olditem, ProfileModel newitem) {
        return olditem.getName().equals(newitem.getName());
    }

    @Override
    protected boolean areItemsTheSame(ProfileModel olditem, ProfileModel newitem) {
        return olditem.getName()!=null && newitem.getName()!=null && olditem.getName().equals(newitem.getName()) && olditem.getAvatar().equals(newitem.getAvatar())
                && olditem.getRating()==newitem.getRating();
    }
}
