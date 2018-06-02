package com.mvvm.kien2111.fastjob.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.data.local.db.converter.AppConverter;

/**
 * Created by WhoAmI on 19/04/2018.
 */
@Entity(primaryKeys = {"cityid"},
        tableName = "cities")
@TypeConverters(AppConverter.class)
public class City extends BaseObservable implements Parcelable {
    @NonNull
    private String cityid;
    @NonNull
    private String namecity;

    public City(String cityid, String namecity) {
        this.cityid = cityid;
        this.namecity = namecity;
    }

    protected City(Parcel in) {
        cityid = in.readString();
        namecity = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @NonNull
    @Bindable
    public String getCityid() {
        return cityid;
    }

    public void setIdcity(String idcity) {
        this.cityid = idcity;
        notifyPropertyChanged(BR.cityid);
    }
    @Bindable
    public String getNamecity() {
        return namecity;
    }

    public void setNamecity(String namecity) {
        this.namecity = namecity;
        notifyPropertyChanged(BR.namecity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityid);
        dest.writeString(namecity);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return namecity;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof City))return false;
        return ((City) obj).cityid.equals(cityid) || ((City) obj).namecity.equals(this.namecity);
    }
}
