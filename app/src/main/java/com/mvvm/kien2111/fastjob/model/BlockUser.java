package com.mvvm.kien2111.fastjob.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by donki on 4/19/2018.
 */


public class BlockUser implements Parcelable {


    public BlockUser(){

    }

    @Expose
    @SerializedName("iduser")
    private String iduser;

    @Expose
    @SerializedName("status")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static final Creator<BlockUser> CREATOR = new Creator<BlockUser>() {
        @Override
        public BlockUser createFromParcel(Parcel in) {
            return new BlockUser(in);
        }

        @Override
        public BlockUser[] newArray(int size) {
            return new BlockUser[size];
        }
    };

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

protected BlockUser(Parcel in){
        iduser = in.readString();
}
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iduser);
        dest.writeInt(status);
    }
}
