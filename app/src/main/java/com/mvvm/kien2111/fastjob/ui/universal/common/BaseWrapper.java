package com.mvvm.kien2111.fastjob.ui.universal.common;

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
