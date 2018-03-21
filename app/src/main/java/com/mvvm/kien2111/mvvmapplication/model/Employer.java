package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class Employer {
    @Expose
    @SerializedName("idemployer")
    private String idemployer;

    @Expose
    @SerializedName("brandname")
    private String brandname;

    @Expose
    @SerializedName("budget")
    private int budget;

    @Expose
    @SerializedName("about")
    private String about;

    @Expose
    @SerializedName("phonenumber")
    private String phonenumber;

    public String getIdemployer() {
        return idemployer;
    }

    public void setIdemployer(String idemployer) {
        this.idemployer = idemployer;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


}
