package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class Employee {


    @Expose
    @SerializedName("idemployee")
    private String idemployee;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("gender")
    private Gender gender;

    @Expose
    @SerializedName("birthday")
    private java.sql.Date birthday;

    @Expose
    @SerializedName("phonenumber")
    private String phonenumber;

    @Expose
    @SerializedName("about")
    private String about;

    @Expose
    @SerializedName("profile")
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getEmployee() {
        return idemployee;
    }

    public void setEmployee(String employee) {
        this.idemployee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


}
