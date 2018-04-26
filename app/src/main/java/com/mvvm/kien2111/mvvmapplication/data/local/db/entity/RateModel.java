package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;
import com.mvvm.kien2111.mvvmapplication.model.User;

/**
 * Created by WhoAmI on 04/04/2018.
 */
@Entity(primaryKeys = "idrate",tableName = "rates")
@TypeConverters(AppConverter.class)
public class RateModel implements Parcelable {
    public RateModel(){}
    @NonNull
    @SerializedName("idrate")
    @Expose
    private Integer idrate;
    @SerializedName("user_create_rate")
    @Expose
    private User user;
    @SerializedName("average_point")
    @Expose
    private float ratepoint;
    @SerializedName("content")
    @Expose
    private String note;

    protected RateModel(Parcel in) {
        idrate = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        ratepoint = in.readFloat();
        note = in.readString();
    }

    public static final Creator<RateModel> CREATOR = new Creator<RateModel>() {
        @Override
        public RateModel createFromParcel(Parcel in) {
            return new RateModel(in);
        }

        @Override
        public RateModel[] newArray(int size) {
            return new RateModel[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idrate);
        dest.writeParcelable(user, flags);
        dest.writeFloat(ratepoint);
        dest.writeString(note);
    }
}
