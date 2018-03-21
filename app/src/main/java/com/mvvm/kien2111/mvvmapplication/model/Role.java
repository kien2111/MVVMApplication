package com.mvvm.kien2111.mvvmapplication.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 27/02/2018.
 */

public class Role {
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleid) {
        this.roleId = roleId;
    }
    @SerializedName("rolename")
    @Expose
    private String rolename;
    @SerializedName("roleId")
    @Expose
    private int roleId;
    public Role(int roleId,String rolename){
        this.roleId = roleId;
        this.rolename = rolename;
    }

    @Override
    public boolean equals(Object obj) {
        Role role = (Role)obj;
        return role.equals(role.rolename);
    }
}
