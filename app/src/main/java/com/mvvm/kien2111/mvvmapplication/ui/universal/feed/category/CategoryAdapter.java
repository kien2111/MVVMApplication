package com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.databinding.CategoryItemBinding;

import java.util.UUID;

/**
 * Created by WhoAmI on 07/03/2018.
 */

public class CategoryAdapter extends BaseAdapter<Category,CategoryItemBinding> {

    private final FragmentBindingComponent dataBindingComponent;
    private final CategoryClickCallBack callBack;
    public CategoryAdapter(FragmentBindingComponent dataBindingComponent, CategoryClickCallBack callback){
        this.dataBindingComponent = dataBindingComponent;
        this.callBack = callback;
        this.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getLstData().get(position).hashCode();
    }

    @Override
    protected BaseViewHolder<CategoryItemBinding> instantiateViewHolder(CategoryItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected CategoryItemBinding createBinding(ViewGroup parent) {
        CategoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.category_item,parent,false,dataBindingComponent);
        binding.getRoot().setOnClickListener(view->{
            if(callBack!=null && binding.getCategory()!=null){
                callBack.onClick(binding.getCategory());
            }
        });
        return binding;
    }

    @Override
    protected void bind(CategoryItemBinding mBinding, Category item) {
        mBinding.setCategory(item);
    }

    @Override
    protected boolean areContentsTheSame(Category olditem, Category newitem) {
        return olditem.getNamecategory().equals(newitem.getNamecategory());
    }

    @Override
    protected boolean areItemsTheSame(Category olditem, Category newitem) {
        return olditem.getIdcategory().equals(newitem.getIdcategory()) &&
                olditem.getImage_path().equals(newitem.getImage_path());
    }

    public interface CategoryClickCallBack{
        void onClick(Category category);
    }
}
