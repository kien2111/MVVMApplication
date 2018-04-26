package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverters;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

/**
 * Created by WhoAmI on 19/04/2018.
 */
@Entity(primaryKeys = {"distid"},
        tableName = "districts",
        foreignKeys = @ForeignKey(entity = City.class,
        parentColumns = "cityid",
        childColumns = "cityid",
        onDelete = SET_NULL))
@TypeConverters(AppConverter.class)
public class District extends BaseObservable implements Parcelable {
    @NonNull
    private String distid;
    @NonNull
    private String namedist;
    @NonNull
    private String cityid;

    public District(@NonNull String distid, String namedist, String cityid) {
        this.distid = distid;
        this.namedist = namedist;
        this.cityid = cityid;
    }

    protected District(Parcel in) {
        distid = in.readString();
        namedist = in.readString();
        cityid = in.readString();
    }

    public static final Creator<District> CREATOR = new Creator<District>() {
        @Override
        public District createFromParcel(Parcel in) {
            return new District(in);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };

    @NonNull
    @Bindable
    public String getDistid() {
        return distid;
    }
    @Bindable
    public String getNamedist() {
        return namedist;
    }
    @Bindable
    public String getCityid() {
        return cityid;
    }

    public void setIddistrict(@NonNull String distid) {
        this.distid = distid;
        notifyPropertyChanged(BR.distid);
    }

    public void setNamedistrict(String namedist) {
        this.namedist = namedist;
        notifyPropertyChanged(BR.namedist);
    }

    public void setIdcity(String cityid) {
        this.cityid = cityid;
        notifyPropertyChanged(BR.cityid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(distid);
        dest.writeString(namedist);
        dest.writeString(cityid);
    }

    @Override
    public String toString() {
        return namedist;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
