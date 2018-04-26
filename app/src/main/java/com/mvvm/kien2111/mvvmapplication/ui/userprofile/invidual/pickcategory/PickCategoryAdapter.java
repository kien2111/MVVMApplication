package com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.pickcategory;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.databinding.PickCategoryItemBinding;

/**
 * Created by WhoAmI on 23/04/2018.
 */

public class PickCategoryAdapter extends BaseAdapter<Category,PickCategoryItemBinding> {

    private final FragmentBindingComponent dataBindingComponent;

    public PickCategoryAdapter(FragmentBindingComponent dataBindingComponent,ListenerClickCategory listenerClickCategory){
        this.dataBindingComponent = dataBindingComponent;
        this.callBack = listenerClickCategory;
        this.setHasStableIds(true);
    }
    @Override
    protected BaseViewHolder<PickCategoryItemBinding> instantiateViewHolder(PickCategoryItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    private ListenerClickCategory callBack;

    public interface ListenerClickCategory{
        public void onClickCategory(Category category);
    }

    @Override
    protected PickCategoryItemBinding createBinding(ViewGroup parent) {
        PickCategoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.pick_category_item,parent,false,dataBindingComponent);
        binding.getRoot().setOnClickListener(view->{
            if(callBack!=null && binding.getCategory()!=null){
                callBack.onClickCategory(binding.getCategory());
            }
        });
        return binding;
    }

    @Override
    public long getItemId(int position) {
        return getLstData().get(position).hashCode();
    }

    @Override
    protected void bind(PickCategoryItemBinding mBinding, Category item) {
        mBinding.setCategory(item);
        mBinding.executePendingBindings();
    }

    @Override
    protected boolean areContentsTheSame(Category olditem, Category newitem) {
        return false;
    }

    @Override
    protected boolean areItemsTheSame(Category olditem, Category newitem) {
        return false;
    }
}
