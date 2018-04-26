package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;
import com.mvvm.kien2111.mvvmapplication.model.Profile;

/**
 * Created by WhoAmI on 17/03/2018.
 */
@Entity(primaryKeys = {"idprofile"},tableName = "profiles")
@TypeConverters(AppConverter.class)
public class ProfileModel extends Profile implements Parcelable {
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



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
