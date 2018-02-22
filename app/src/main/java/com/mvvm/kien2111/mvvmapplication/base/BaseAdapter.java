package com.mvvm.kien2111.mvvmapplication.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by WhoAmI on 02/02/2018.
 */
/*
    T itemType
    VB databindingType
* */
public abstract class BaseAdapter <T,VB extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<VB>>{
    private List<T> lstData = Collections.emptyList();

    public void setLstData(List<T> lstData) {
        this.lstData = lstData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lstData==null?0:lstData.size();
    }

    @Override
    public final BaseViewHolder<VB> onCreateViewHolder(ViewGroup parent, int viewType) {
        VB binding = createBinding(parent);
        return instantiateViewHolder(binding);
    }
    abstract int getItemViewId();
    abstract BaseViewHolder<VB> instantiateViewHolder(VB mBinding);
    protected abstract VB createBinding(ViewGroup parent);
    protected abstract void bind(VB mBinding,T item);

    @Override
    public void onBindViewHolder(BaseViewHolder<VB> holder, int position) {
        bind(holder.mBinding,lstData.get(position));
        holder.mBinding.executePendingBindings();
    }
    T getItem(int position){
        return lstData.get(position);
    }
}
