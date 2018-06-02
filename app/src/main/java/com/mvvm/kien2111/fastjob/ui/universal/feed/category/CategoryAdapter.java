package com.mvvm.kien2111.fastjob.ui.universal.feed.category;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mvvm.kien2111.fastjob.BuildConfig;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseAdapter;
import com.mvvm.kien2111.fastjob.base.BaseViewHolder;
import com.mvvm.kien2111.fastjob.binding.FragmentBindingComponent;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.databinding.CategoryItemBinding;
import com.mvvm.kien2111.fastjob.util.GlideApp;
import com.mvvm.kien2111.fastjob.util.PaletteBitmap;

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
        GlideApp.with(mBinding.getRoot().getContext())
                .as(PaletteBitmap.class)
                .load(BuildConfig.IMG_URL+item.getImage_path())
                .apply(new RequestOptions()
                        .error(R.drawable.errorimg)
                        .placeholder(R.drawable.defaultimage)
                        .dontAnimate())
                .into(new SimpleTarget<PaletteBitmap>() {
                    @Override
                    public void onResourceReady(@NonNull PaletteBitmap resource, @Nullable Transition<? super PaletteBitmap> transition) {
                        mBinding.imageCategory.setBackgroundColor(resource.palette.getDarkMutedColor(ContextCompat.getColor(mBinding.getRoot().getContext(),android.R.color.white)));
                        mBinding.imageCategory.setImageBitmap(resource.bitmap);
                        mBinding.categoryContainer.setCardBackgroundColor(resource.palette.getDarkMutedColor(ContextCompat.getColor(mBinding.getRoot().getContext(),android.R.color.white)));
                        if(resource.palette.getDarkVibrantSwatch()!=null){
                            mBinding.categoryNum.setBackgroundColor(resource.palette.getDarkVibrantSwatch().getRgb());
                            mBinding.categoryNum.setTextColor(resource.palette.getDarkVibrantSwatch().getTitleTextColor());
                            mBinding.categoryTitle.setBackgroundColor(resource.palette.getDarkVibrantSwatch().getRgb());
                            mBinding.categoryTitle.setTextColor(resource.palette.getDarkVibrantSwatch().getTitleTextColor());
                        }

                    }
                });
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
