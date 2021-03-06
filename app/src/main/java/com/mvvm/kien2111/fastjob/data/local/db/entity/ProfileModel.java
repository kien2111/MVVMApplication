package com.mvvm.kien2111.fastjob.data.local.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;
import com.mvvm.kien2111.fastjob.data.local.db.converter.AppConverter;
import com.mvvm.kien2111.fastjob.model.Profile;

/**
 * Created by WhoAmI on 17/03/2018.
 */
@Entity(primaryKeys = {"idprofile"},tableName = "profiles")
@TypeConverters(AppConverter.class)
public class ProfileModel extends Profile implements Parcelable,ClusterItem {
    @Override
    public LatLng getPosition() {
        return new LatLng(mLat,mLong);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return getSummary();
    }

    @Expose
    @SerializedName("lat")
    @ColumnInfo(name = "lat")
    private double mLat;

    @Expose
    @SerializedName("long")
    @ColumnInfo(name = "long")
    private double mLong;

    @Expose
    @SerializedName("avatar")
    private String avatar;

    @Expose
    @SerializedName("realname")
    private String name;

    @Expose
    @SerializedName("rating")
    private float rating;

    protected ProfileModel(Parcel in) {
        super(in);
        avatar = in.readString();
        name = in.readString();
        rating = in.readFloat();
        mLat = in.readDouble();
        mLong = in.readDouble();
    }
    public ProfileModel(){
        super();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(avatar);
        dest.writeString(name);
        dest.writeFloat(rating);
        dest.writeDouble(mLat);
        dest.writeDouble(mLong);
    }

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

    public static final Creator<ProfileModel> CREATOR = new Creator<ProfileModel>() {
        @Override
        public ProfileModel createFromParcel(Parcel in) {
            return new ProfileModel(in);
        }

        @Override
        public ProfileModel[] newArray(int size) {
            return new ProfileModel[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double mLat) {
        this.mLat = mLat;
    }

    public double getLong() {
        return mLong;
    }

    public void setLong(double mLong) {
        this.mLong = mLong;
    }
}
