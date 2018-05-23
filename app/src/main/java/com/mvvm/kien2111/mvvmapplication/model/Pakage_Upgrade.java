package com.mvvm.kien2111.mvvmapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 05/05/2018.
 */

public class Pakage_Upgrade implements Parcelable{

    @SerializedName("idpakage_update")
    @Expose
    private int idpakage_update;

    @SerializedName("pakage_name")
    @Expose
    private String pakage_name;

    @SerializedName("pakage_fee")
    @Expose
    private double pakage_fee;

    public Pakage_Upgrade(){}

    protected Pakage_Upgrade(Parcel in) {
        idpakage_update = in.readInt();
        pakage_name = in.readString();
        pakage_fee = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idpakage_update);
        dest.writeString(pakage_name);
        dest.writeDouble(pakage_fee);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pakage_Upgrade> CREATOR = new Creator<Pakage_Upgrade>() {
        @Override
        public Pakage_Upgrade createFromParcel(Parcel in) {
            return new Pakage_Upgrade(in);
        }

        @Override
        public Pakage_Upgrade[] newArray(int size) {
            return new Pakage_Upgrade[size];
        }
    };

    public int getIdpakage_update() {
        return idpakage_update;
    }

    public void setIdpakage_update(int idpakage_update) {
        this.idpakage_update = idpakage_update;
    }

    public String getPakage_name() {
        return pakage_name;
    }

    public void setPakage_name(String pakage_name) {
        this.pakage_name = pakage_name;
    }

    public double getPakage_fee() {
        return pakage_fee;
    }

    public void setPakage_fee(double pakage_fee) {
        this.pakage_fee = pakage_fee;
    }
}
