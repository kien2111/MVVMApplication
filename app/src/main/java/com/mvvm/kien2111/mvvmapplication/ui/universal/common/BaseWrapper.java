package com.mvvm.kien2111.mvvmapplication.ui.universal.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 06/04/2018.
 */

public abstract class BaseWrapper {
    @SerializedName("totalcount")
    @Expose
    private int totalcount;
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

    public Integer getNextpage() {
        return nextpage;
    }

    public void setNextpage(Integer nextpage) {
        this.nextpage = nextpage;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }
}
