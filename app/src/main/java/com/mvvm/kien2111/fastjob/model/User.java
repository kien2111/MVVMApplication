package com.mvvm.kien2111.fastjob.model;

import android.arch.persistence.room.Ignore;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.fastjob.BR;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 21/01/2018.
 */

public class User extends BaseObservable implements Parcelable {

    protected User(Parcel in) {
        realname = in.readString();
        rate_point = in.readFloat();
        phone_individual = in.readString();
        profile = in.readParcelable(Profile.class.getClassLoader());
        brandname = in.readString();
        budget_from = in.readDouble();
        budget_to = in.readDouble();
        phone_company = in.readString();
        logo_company = in.readString();
        about_company = in.readString();
        role_list = in.createTypedArrayList(Role.CREATOR);
        account_balance = in.readDouble();
        username = in.readString();
        password = in.readString();
        avatar = in.readString();
        email = in.readString();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(realname);
        dest.writeFloat(rate_point);
        dest.writeString(phone_individual);
        dest.writeParcelable(profile, flags);
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

    @Override
    public int describeContents() {
        return 0;
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

    @Bindable
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
        notifyPropertyChanged(BR.realname);
    }
    @Bindable
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }
    @Bindable
    public java.util.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }
    @Bindable
    public String getPhone_individual() {
        if(phone_individual!=null){
            return phone_individual;
        }
        return "User not supply";
    }

    public void setPhone_individual(String phone_individual) {
        this.phone_individual = phone_individual;
        notifyPropertyChanged(BR.phone_individual);
    }
    @Bindable
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        notifyPropertyChanged(BR.profile);
    }
    @Bindable
    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
        notifyPropertyChanged(BR.brandname);
    }
    @Bindable
    public double getBudget_from() {
        return budget_from;
    }

    public void setBudget_from(double budget_from) {
        this.budget_from = budget_from;
        notifyPropertyChanged(BR.budget_from);
    }
    @Bindable
    public double getBudget_to() {
        return budget_to;
    }

    public void setBudget_to(double budget_to) {
        this.budget_to = budget_to;
        notifyPropertyChanged(BR.budget_to);
    }
    @Bindable
    public String getPhone_company() {
        return phone_company;
    }

    public void setPhone_company(String phone_company) {
        this.phone_company = phone_company;
        notifyPropertyChanged(BR.phone_company);
    }
    @Bindable
    public String getLogo_company() {
        return logo_company;
    }

    public void setLogo_company(String logo_company) {
        this.logo_company = logo_company;
        notifyPropertyChanged(BR.logo_company);
    }

    @Bindable
    public String getAbout_company() {
        return about_company;
    }

    public void setAbout_company(String about_company) {
        this.about_company = about_company;
        notifyPropertyChanged(BR.about_company);
    }
    @Bindable
    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
        notifyPropertyChanged(BR.account_balance);
    }
    @Bindable
    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
        notifyPropertyChanged(BR.userName);
    }
    @Bindable
    public String getPassword() {
        return password;

    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }
    @Bindable
    public String getEmail() {
        if(email!=null){
            return email;
        }
        return "User not supply";
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }

    @Bindable
    public float getRate_point() {
        return rate_point;
    }

    public void setRate_point(float rate_point) {
        this.rate_point = rate_point;
        notifyPropertyChanged(BR.rate_point);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.userName);
    }

    @Expose
    @SerializedName("realname")
    private String realname;

    @Expose
    @SerializedName("gender")
    private Gender gender;

    @Expose
    @SerializedName("rate_point")
    private float rate_point;

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


    @Bindable
    public List<Role> getRole_list() {
        return role_list;
    }

    public void setRole_list(List<Role> role_list) {
        this.role_list = role_list;
        notifyPropertyChanged(BR.role_list);
    }

    public List<String> getListRoleName(){
        List<String> lst_string = new ArrayList<>();
        for(Role role : role_list){
            lst_string.add(role.getRolename());
        }
        return lst_string;
    }


    public User(){

    }

    @Ignore
    private User(Builder builder){
        this.realname = builder.realname;
        this.gender = builder.gender;
        this.birthday = builder.birthday;
        this.phone_individual = builder.phone_individual;
        this.profile = builder.profile;
        this.brandname = builder.brandname;
        this.budget_to = builder.budget_to;
        this.budget_from = builder.budget_from;
        this.phone_company = builder.phone_company;
        this.logo_company = builder.logo_company;
        this.about_company = builder.about_company;
        this.role_list = builder.role_list;
        this.account_balance = builder.account_balance;
        this.username = builder.username;
        this.userId = builder.userId;
        this.password = builder.password;
        this.avatar = builder.avatar;
        this.email = builder.email;
    }

    public static class Builder{

        private String realname;

        private Gender gender;

        private java.util.Date birthday;

        private String phone_individual;

        private Profile profile;

        private String brandname;

        private double budget_from;

        private double budget_to;

        private String phone_company;

        private String logo_company;

        private String about_company;

        private List<Role> role_list;

        private double account_balance;

        private String username;

        private String password;

        private String avatar;

        private String email;

        private String userId;

        public User build(){
            return new User(this);
        }

        public Builder setRealname(String realname) {
            this.realname = realname;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder setBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setPhone_individual(String phone_individual) {
            this.phone_individual = phone_individual;
            return this;
        }

        public Builder setProfile(Profile profile) {
            this.profile = profile;
            return this;
        }

        public Builder setBrandname(String brandname) {
            this.brandname = brandname;
            return this;
        }

        public Builder setBudget_from(double budget_from) {
            this.budget_from = budget_from;
            return this;
        }

        public Builder setBudget_to(double budget_to) {
            this.budget_to = budget_to;
            return this;
        }

        public Builder setPhone_company(String phone_company) {
            this.phone_company = phone_company;
            return this;
        }

        public Builder setLogo_company(String logo_company) {
            this.logo_company = logo_company;
            return this;
        }

        public Builder setAbout_company(String about_company) {
            this.about_company = about_company;
            return this;
        }

        public Builder setRole_list(List<Role> role_list) {
            this.role_list = role_list;return this;
        }

        public Builder setAccount_balance(double account_balance) {
            this.account_balance = account_balance;return this;
        }

        public Builder setUsername(String username) {
            this.username = username;return this;
        }

        public Builder setPassword(String password) {
            this.password = password;return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;return this;
        }

        public Builder setEmail(String email) {
            this.email = email;return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;return this;
        }
    }

}
