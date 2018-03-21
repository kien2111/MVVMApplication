package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.model.Profile;

/**
 * Created by WhoAmI on 17/03/2018.
 */
@Entity(primaryKeys = {"idprofile"})
public class HighRateProfileModel extends Profile {
    @Expose
    @SerializedName("avatar")
    private String avatar;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("rating")
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
