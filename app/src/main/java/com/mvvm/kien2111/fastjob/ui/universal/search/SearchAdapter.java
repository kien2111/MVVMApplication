package com.mvvm.kien2111.fastjob.ui.universal.search;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.MainThread;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.databinding.ChildCategoryItemExpandableBinding;
import com.mvvm.kien2111.fastjob.databinding.ChildNodataItemExpandableBinding;
import com.mvvm.kien2111.fastjob.databinding.ChildProfileItemExpandableBinding;
import com.mvvm.kien2111.fastjob.databinding.HeaderItemExpandableBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 02/04/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int CATEGORY_VIEWTYPE = 3;
    private final static int PROFILE_VIEWTYPE = 2;
    private final static int HEADER_VIEWTYPE = 1;
    private final static int NODATA_VIEWTYPE = 0;

    public interface ListenerClickSearchResult{
        void onClickSearchResult(ExpandableChildItem childItem, Bitmap bitmap);
    }


    private ListenerClickSearchResult callback;

    private List<ExpandableChildItem> arrayListItem;
    private int dataVersion = 0;
    public SearchAdapter(ListenerClickSearchResult callback){
        arrayListItem = new ArrayList<>();
        this.callback = callback;
        this.setHasStableIds(true);
    }
    @Override
    public int getItemViewType(int position) {
        if(arrayListItem.get(position) instanceof HeaderItem){
            return HEADER_VIEWTYPE;
        }else if(arrayListItem.get(position) instanceof CategoryItem){
            return CATEGORY_VIEWTYPE;
        }else if(arrayListItem.get(position) instanceof ProfileItem){
            return PROFILE_VIEWTYPE;
        }else{
            return NODATA_VIEWTYPE;
        }

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case HEADER_VIEWTYPE:return new HeaderViewHolder(createHeaderBinding(parent));
            case CATEGORY_VIEWTYPE:return new CategoryItemViewHolder(createCategoryBinding(parent));
            case PROFILE_VIEWTYPE:return new ProfileItemViewHolder(createProfileBinding(parent));
            default:return new NoDataItemViewHolder(createNoDataBinding(parent));
        }
    }

    private ChildNodataItemExpandableBinding createNoDataBinding(ViewGroup parent) {
        ChildNodataItemExpandableBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_nodata_item_expandable,parent,false);
        return binding;
    }

    private ChildProfileItemExpandableBinding createProfileBinding(ViewGroup parent) {
        ChildProfileItemExpandableBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_profile_item_expandable,parent,false);
        binding.getRoot().setOnClickListener(v->{
            callback.onClickSearchResult(binding.getProfileitem(),((BitmapDrawable)binding.imgCircle.getDrawable()).getBitmap());
        });
        return binding;
    }

    private ChildCategoryItemExpandableBinding createCategoryBinding(ViewGroup parent) {
        ChildCategoryItemExpandableBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_category_item_expandable,parent,false);
        binding.getRoot().setOnClickListener(v->{
            callback.onClickSearchResult(binding.getCategoryitem(),((BitmapDrawable)binding.imgCircle.getDrawable()).getBitmap());
        });
        return binding;
    }


    private HeaderItemExpandableBinding createHeaderBinding(ViewGroup parent) {
        HeaderItemExpandableBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.header_item_expandable,parent,false);
        return binding;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(this.arrayListItem.get(position) instanceof HeaderItem){
            ((HeaderViewHolder)holder).binding.setHeadername(((HeaderItem)arrayListItem.get(position)).getNameheaderitem());
            ((HeaderViewHolder) holder).binding.executePendingBindings();
        }else if(this.arrayListItem.get(position) instanceof CategoryItem){
            ((CategoryItemViewHolder)holder).binding.setCategoryitem(((CategoryItem)arrayListItem.get(position)));
            ((CategoryItemViewHolder) holder).binding.executePendingBindings();
        }else if(this.arrayListItem.get(position) instanceof ProfileItem){
            ((ProfileItemViewHolder)holder).binding.setProfileitem(((ProfileItem)arrayListItem.get(position)));
            ((ProfileItemViewHolder) holder).binding.executePendingBindings();
        }else{
            ((NoDataItemViewHolder)holder).binding.executePendingBindings();
        }

    }

    @MainThread
    public void refreshDataSet(SearchResult searchResult){
        dataVersion++;
        List<ExpandableChildItem> updatelist = searchResult.mergeList();
        if(this.arrayListItem==null){
            if(searchResult.getCategoryItemList()==null && searchResult.getProfileItemList()==null){
                return;
            }
            this.arrayListItem = updatelist;
            notifyDataSetChanged();
        }else if(searchResult==null){
            int oldsize = this.arrayListItem.size();
            this.arrayListItem = null;
            notifyItemRangeRemoved(0,oldsize);
        }else{
            final int startVersion = dataVersion;
            final List<? extends ExpandableChildItem> oldList = arrayListItem;
            Observable.fromCallable(new Callable<DiffUtil.DiffResult>() {
                @Override
                public DiffUtil.DiffResult call() throws Exception {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldList.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return updatelist.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return SearchAdapter.this.areItemTheSame(oldList.get(oldItemPosition),updatelist.get(newItemPosition));
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            return SearchAdapter.this.areContentTheSame(oldList.get(oldItemPosition),updatelist.get(newItemPosition));
                        }
                    });
                }
            }).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(diffResult -> {
                if(startVersion!=dataVersion){
                    return;
                }
                this.arrayListItem=updatelist;
                diffResult.dispatchUpdatesTo(this);
            }, Throwable::printStackTrace);
        }

    }

    private boolean areContentTheSame(ExpandableChildItem olditem, ExpandableChildItem newitem) {
        if(olditem instanceof CategoryItem && newitem instanceof CategoryItem){
            return ((CategoryItem) olditem).getNamecategory().equals(((CategoryItem) newitem).getNamecategory());
        }else if(olditem instanceof ProfileItem && newitem instanceof ProfileItem){
            return ((ProfileItem) olditem).getProfile_name().equals(((ProfileItem) newitem).getProfile_name());
        }
        return false;
    }

    private boolean areItemTheSame(ExpandableChildItem olditem, ExpandableChildItem newitem) {
        return ((olditem instanceof CategoryItem && newitem instanceof CategoryItem) || (olditem instanceof ProfileItem && newitem instanceof ProfileItem)) && olditem.equals(newitem);
    }

    @Override
    public int getItemCount() {
        return this.arrayListItem.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        private HeaderItemExpandableBinding binding;
        HeaderViewHolder(HeaderItemExpandableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public static class CategoryItemViewHolder extends RecyclerView.ViewHolder{
        private ChildCategoryItemExpandableBinding binding;
        CategoryItemViewHolder(ChildCategoryItemExpandableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public static class ProfileItemViewHolder extends RecyclerView.ViewHolder{
        private ChildProfileItemExpandableBinding binding;
        ProfileItemViewHolder(ChildProfileItemExpandableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public static class NoDataItemViewHolder extends RecyclerView.ViewHolder{
        private ChildNodataItemExpandableBinding binding;
        NoDataItemViewHolder(ChildNodataItemExpandableBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
