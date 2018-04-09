package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 01/04/2018.
 */

public class SearchResult {
    @SerializedName("lst_category")
    @Expose
    List<CategoryItem> categoryItemList;
    @SerializedName("lst_profile")
    @Expose
    List<ProfileItem> profileItemList;
    public List<? extends ExpandableChildItem> getCategoryItemList() {
        return categoryItemList;
    }

    public List<? extends ExpandableChildItem> getProfileItemList() {
        return profileItemList;
    }

    public List<ExpandableChildItem> mergeList(){
        List<ExpandableChildItem> mergelist = new ArrayList<>();
        if(categoryItemList.size()==0 && profileItemList.size()==0){

        }else{
            if(categoryItemList.size()!=0){
                mergelist.add(new HeaderItem("Categories"));
                mergelist.addAll(categoryItemList);
            }
            if(profileItemList.size()!=0){
                mergelist.add(new HeaderItem("Profiles"));
                mergelist.addAll(profileItemList);
            }
        }

        return mergelist;
    }
}
