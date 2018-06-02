package com.mvvm.kien2111.mvvmapplication.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by donki on 5/3/2018.
 */

public class Income extends BaseObservable {
    @Expose
    @SerializedName("dates")
    private String date;

    @Expose
    @SerializedName("total")
    private Float total;

    public Income(String date, Float total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
