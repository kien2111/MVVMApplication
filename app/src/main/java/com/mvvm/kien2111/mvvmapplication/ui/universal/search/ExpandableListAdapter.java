package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.databinding.ChildCategoryItemExpandableBinding;
import com.mvvm.kien2111.mvvmapplication.databinding.ChildProfileItemExpandableBinding;
import com.mvvm.kien2111.mvvmapplication.databinding.HeaderItemExpandableBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 01/04/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private final static String KEY_HEADER_CATEGORY = "Categories";
    private final static String KEY_HEADER_PROFILE = "Profiles";
    private final Context _context;
    private final List<String> _listHeader;
    private final HashMap<String,List<? extends ExpandableChildItem>> _listChild;
    private final DataBindingComponent dataBindingComponent;
    public ExpandableListAdapter(Context context,DataBindingComponent bindingComponent){
        this._context = context;
        this._listHeader = Arrays.asList(KEY_HEADER_CATEGORY,KEY_HEADER_PROFILE);
        this._listChild = new HashMap<>();
        this.dataBindingComponent = bindingComponent;
        this._listChild.put(KEY_HEADER_CATEGORY,new ArrayList<CategoryItem>());
        this._listChild.put(KEY_HEADER_PROFILE,new ArrayList<ProfileItem>());
    }

    public void refreshDataSet(SearchResult result){
        //Iterator<Map.Entry<String,List<? extends ExpandableChildItem>>> iterator = this._listChild.entrySet().iterator();
        Observable.fromIterable(this._listChild.entrySet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringListEntry -> {
                    if(stringListEntry.getKey().equals(KEY_HEADER_CATEGORY)){
                        stringListEntry.setValue(result.getCategoryItemList());
                    }else{
                        stringListEntry.setValue(result.getProfileItemList());
                    }
                    this.notifyDataSetChanged();
                });
    }

    @Override
    public int getGroupCount() {
        return this._listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listChild.get(this._listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listChild.get(this._listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeaderItemExpandableBinding binding = null;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            binding = DataBindingUtil.inflate(inflater,R.layout.header_item_expandable,parent,false,dataBindingComponent);
            binding.setHeadername((String)getGroup(groupPosition));
            convertView = binding.getRoot();
            binding.executePendingBindings();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewDataBinding binding = null;
        LayoutInflater inflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(getChild(groupPosition,childPosition) instanceof CategoryItem){
            binding = DataBindingUtil.inflate(inflater,R.layout.child_category_item_expandable,parent,false,dataBindingComponent);
            binding.setVariable(BR.categoryitem,getChild(groupPosition,childPosition));
        }else{
            binding = DataBindingUtil.inflate(inflater,R.layout.child_profile_item_expandable,parent,false,dataBindingComponent);
            binding.setVariable(BR.profileitem,getChild(groupPosition,childPosition));
        }
        if(binding!=null){
            binding.executePendingBindings();
            convertView = binding.getRoot();
        }

        return convertView;
    }


    public void expandAll(ExpandableListView expandableListView){
        for(int groupposition=0;groupposition<this.getGroupCount();groupposition++){
            expandableListView.expandGroup(groupposition,false);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
