package com.mvvm.kien2111.fastjob.ui.universal.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 01/04/2018.
 */

public class CategoryItem implements ExpandableChildItem{
    @SerializedName("idcategory")
    @Expose
    private String idcategory;

    @SerializedName("category_name")
    @Expose
    private String namecategory;

    @SerializedName("image_path")
    @Expose
    private String image_path;

    @SerializedName("num_count")
    @Expose
    private int number_profile;

    public int getNumber_profile() {
        return number_profile;
    }

    public void setNumber_profile(int number_profile) {
        this.number_profile = number_profile;
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

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

}
