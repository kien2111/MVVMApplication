package com.mvvm.kien2111.mvvmapplication.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by WhoAmI on 02/02/2018.
 */

public abstract class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T mBinding;
    public BaseViewHolder(T mBinding) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
    }
}
