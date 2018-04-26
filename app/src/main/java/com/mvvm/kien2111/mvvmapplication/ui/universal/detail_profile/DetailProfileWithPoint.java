package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.model.User;

/**
 * Created by WhoAmI on 24/04/2018.
 */

public class DetailProfileWithPoint extends User {
    @Expose
    @SerializedName("point")
    private float point;

    public DetailProfileWithPoint(Parcel in, float point) {
        super(in);
        this.point = point;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
}
