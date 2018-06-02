package com.mvvm.kien2111.mvvmapplication.model;

import android.databinding.BaseObservable;

import java.util.Date;

/**
 * Created by donki on 5/14/2018.
 */

public class UpgradeAccount extends BaseObservable {
    private String profile_id;
    private int idrequest_update_profile;
    private int approve;
    private int level_expected;
    private Date created_at;
    private Date updated_at;
    private  int idpakage;
    private String realname;
    private String phone_company;
    private String brand_name_company;
    private String username;
    private String avatar;
    private String email;

    public UpgradeAccount(String profile_id, int idrequest_update_profile,
                          int approve, int level_expected, Date created_at, Date updated_at,
                          int idpakage, String realname, String phone_company,
                          String brand_name_company, String username, String avatar, String email) {
        this.profile_id = profile_id;
        this.idrequest_update_profile = idrequest_update_profile;
        this.approve = approve;
        this.level_expected = level_expected;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.idpakage = idpakage;
        this.realname = realname;
        this.phone_company = phone_company;
        this.brand_name_company = brand_name_company;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public int getIdrequest_update_profile() {
        return idrequest_update_profile;
    }

    public void setIdrequest_update_profile(int idrequest_update_profile) {
        this.idrequest_update_profile = idrequest_update_profile;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }

    public int getLevel_expected() {
        return level_expected;
    }

    public void setLevel_expected(int level_expected) {
        this.level_expected = level_expected;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getIdpakage() {
        return idpakage;
    }

    public void setIdpakage(int idpakage) {
        this.idpakage = idpakage;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone_company() {
        return phone_company;
    }

    public void setPhone_company(String phone_company) {
        this.phone_company = phone_company;
    }

    public String getBrand_name_company() {
        return brand_name_company;
    }

    public void setBrand_name_company(String brand_name_company) {
        this.brand_name_company = brand_name_company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
