package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;
import com.mvvm.kien2111.mvvmapplication.model.User;

/**
 * Created by WhoAmI on 04/04/2018.
 */
@Entity(primaryKeys = "idrate")
@TypeConverters(AppConverter.class)
public class RateModel {
    @SerializedName("idrate")
    @Expose
    private Integer idrate;
    @SerializedName("user_who_rate_this")
    @Expose
    private User user;
    @SerializedName("average_point")
    @Expose
    private float ratepoint;
    @SerializedName("content")
    @Expose
    private String note;
    public Integer getIdrate() {
        return idrate;
    }

    public void setIdrate(Integer idrate) {
        this.idrate = idrate;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getRatepoint() {
        return ratepoint;
    }

    public void setRatepoint(float ratepoint) {
        this.ratepoint = ratepoint;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
