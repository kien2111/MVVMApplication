package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 01/04/2018.
 */

public class ProfileItem implements ExpandableChildItem{
    @Expose
    @SerializedName("idprofile")
    private String profile_id;

    @Expose
    @SerializedName("realname")
    private String profile_name;

    @Expose
    @SerializedName("avatar")
    private String profile_avatar;

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getProfile_avatar() {
        return profile_avatar;
    }

    public void setProfile_avatar(String profile_avatar) {
        this.profile_avatar = profile_avatar;
    }
}
