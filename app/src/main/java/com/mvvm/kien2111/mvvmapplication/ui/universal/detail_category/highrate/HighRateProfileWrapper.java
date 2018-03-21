package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.HighRateProfileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 20/03/2018.
 */

public class HighRateProfileWrapper {
    @SerializedName("totalcount")
    @Expose
    private int totalcount;
    @SerializedName("profiles")
    @Expose
    private List<HighRateProfileModel> listprofile;
    @SerializedName("nextpage")
    @Expose
    private Integer nextpage;
    @SerializedName("query")
    @Expose
    private String query;
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }



    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public List<HighRateProfileModel> getListprofile() {
        return listprofile;
    }

    public void setListprofile(List<HighRateProfileModel> listprofile) {
        this.listprofile = listprofile;
    }

    public Integer getNextpage() {
        return nextpage;
    }

    public void setNextpage(Integer nextpage) {
        this.nextpage = nextpage;
    }

    public List<String> getProfilesId(){
        List<String> stringList = new ArrayList<>();
        for(HighRateProfileModel highRateProfileModel : listprofile){
            stringList.add(highRateProfileModel.getIdprofile());
        }
        return stringList;
    }


}
