package com.mvvm.kien2111.fastjob.data.local.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 06/03/2018.
 */

@Entity(tableName = "categories")
public class Category implements Parcelable{
    @SerializedName("idcategory")
    @Expose
    @NonNull
    @PrimaryKey
    private String idcategory;

    @NonNull
    @SerializedName("category_name")
    @Expose
    @ColumnInfo(name = "category_name")
    private String namecategory;

    @NonNull
    @SerializedName("image_path")
    @Expose
    @ColumnInfo(name = "image_path")
    private String image_path;

    @SerializedName("num_profile")
    @Expose
    @NonNull
    private int num_profile;

    public int getNum_profile() {
        return num_profile;
    }

    public void setNum_profile(int num_profile) {
        this.num_profile = num_profile;
    }

    protected Category(Parcel in) {
        idcategory = in.readString();
        namecategory = in.readString();
        image_path = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }



    public String getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getNamecategory() {
        return namecategory;
    }

    public void setNamecategory(String namecategory) {
        this.namecategory = namecategory;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public Category(){

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idcategory);
        dest.writeString(namecategory);
        dest.writeString(image_path);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31* result + idcategory.hashCode();
        result = 31* result + namecategory.hashCode();
        result = 31* result + image_path.hashCode();
        return result;
    }
}