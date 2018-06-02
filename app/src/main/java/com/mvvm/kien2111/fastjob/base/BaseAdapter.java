package com.mvvm.kien2111.fastjob.base;

import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

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
    public List<T> getLstData() {
        return lstData;
    }

    private List<T> lstData = Collections.emptyList();

    public void setLstData(List<T> lstData) {
        if(lstData==null)return;
        this.lstData = lstData;
        notifyDataSetChanged();
    }
    private int dataVersion = 0;
    @Override
    public int getItemCount() {
        return lstData==null?0:lstData.size();
    }

    @Override
    public final BaseViewHolder<VB> onCreateViewHolder(ViewGroup parent, int viewType) {
        VB binding = createBinding(parent);
        return instantiateViewHolder(binding);
    }
    protected abstract BaseViewHolder<VB> instantiateViewHolder(VB mBinding);
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

    @MainThread
    public void changeDataSet(List<T> updatelist){
        dataVersion++;
        if(lstData==null){
            if(updatelist==null){
                return;
            }
            lstData = updatelist;
            notifyDataSetChanged();
        }else if(updatelist==null || updatelist.size()==0){
            int oldsize = lstData.size();
            lstData = null;
            notifyItemRangeRemoved(0,oldsize);
        }else{
            final int startversion = dataVersion;
            final List<T> oldItems = lstData;
            new AsyncTask<Void,Void,DiffUtil.DiffResult>(){

                @Override
                protected DiffUtil.DiffResult doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldItems.size();
                        }
                        @Override
                        public int getNewListSize() {
                            return updatelist.size();
                        }
                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            T olditem = oldItems.get(oldItemPosition);
                            T newitem = updatelist.get(newItemPosition);
                            return BaseAdapter.this.areItemsTheSame(olditem,newitem);
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            T olditem = oldItems.get(oldItemPosition);
                            T newitem = updatelist.get(newItemPosition);
                            return BaseAdapter.this.areContentsTheSame(olditem,newitem);
                        }
                    });
                }
                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffresult){
                    if(startversion!=dataVersion){
                        return;
                    }
                    lstData=updatelist;
                    diffresult.dispatchUpdatesTo(BaseAdapter.this);
                };
            }.execute();
        }
    }

    protected abstract boolean areContentsTheSame(T olditem, T newitem);

    protected abstract boolean areItemsTheSame(T olditem, T newitem);


}
