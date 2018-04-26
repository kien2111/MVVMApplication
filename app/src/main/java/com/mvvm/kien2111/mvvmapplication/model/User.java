package com.mvvm.kien2111.mvvmapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 21/01/2018.
 */

public class User implements Parcelable {

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getPhone_individual() {
        return phone_individual;
    }

    public void setPhone_individual(String phone_individual) {
        this.phone_individual = phone_individual;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public double getBudget_from() {
        return budget_from;
    }

    public void setBudget_from(double budget_from) {
        this.budget_from = budget_from;
    }

    public double getBudget_to() {
        return budget_to;
    }

    public void setBudget_to(double budget_to) {
        this.budget_to = budget_to;
    }

    public String getPhone_company() {
        return phone_company;
    }

    public void setPhone_company(String phone_company) {
        this.phone_company = phone_company;
    }

    public String getLogo_company() {
        return logo_company;
    }

    public void setLogo_company(String logo_company) {
        this.logo_company = logo_company;
    }

    public String getAbout_company() {
        return about_company;
    }

    public void setAbout_company(String about_company) {
        this.about_company = about_company;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Expose
    @SerializedName("realname")
    private String realname;

    @Expose
    @SerializedName("gender")
    private Gender gender;

    @Expose
    @SerializedName("birthday")
    private java.util.Date birthday;

    @Expose
    @SerializedName("phone_individual")
    private String phone_individual;

    @Expose
    @SerializedName("profile")
    private Profile profile;


    @Expose
    @SerializedName("brand_name_company")
    private String brandname;

    @Expose
    @SerializedName("budget_from")
    private double budget_from;

    @Expose
    @SerializedName("budget_to")
    private double budget_to;

    @Expose
    @SerializedName("phone_company")
    private String phone_company;

    @Expose
    @SerializedName("logo_company")
    private String logo_company;

    @Expose
    @SerializedName("about_company")
    private String about_company;



    @Expose
    @SerializedName("role_list")
    private List<Role> role_list;

    @Expose
    @SerializedName("account_balance")
    private double account_balance;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("avatar")
    private String avatar;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("iduser")
    private String userId;


    protected User(Parcel in) {
        realname = in.readString();
        phone_individual = in.readString();
        brandname = in.readString();
        budget_from = in.readDouble();
        budget_to = in.readDouble();
        phone_company = in.readString();
        logo_company = in.readString();
        about_company = in.readString();

        account_balance = in.readDouble();
        username = in.readString();
        password = in.readString();
        avatar = in.readString();
        email = in.readString();
        userId = in.readString();
    }

    public List<Role> getRole_list() {
        return role_list;
    }

    public void setRole_list(List<Role> role_list) {
        this.role_list = role_list;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };



    public List<String> getListRoleName(){
        List<String> lst_string = new ArrayList<>();
        for(Role role : role_list){
            lst_string.add(role.getRolename());
        }
        return lst_string;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(realname);
        dest.writeString(phone_individual);
        dest.writeString(brandname);
        dest.writeDouble(budget_from);
        dest.writeDouble(budget_to);
        dest.writeString(phone_company);
        dest.writeString(logo_company);
        dest.writeString(about_company);
        dest.writeTypedList(role_list);
        dest.writeDouble(account_balance);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(avatar);
        dest.writeString(email);
        dest.writeString(userId);
    }

    public User(){

    }


}
