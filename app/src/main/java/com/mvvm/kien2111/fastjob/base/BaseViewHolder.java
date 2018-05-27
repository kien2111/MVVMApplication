package com.mvvm.kien2111.fastjob.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by WhoAmI on 02/02/2018.
 */

public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T mBinding;
    public BaseViewHolder(T mBinding) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
    }
}
