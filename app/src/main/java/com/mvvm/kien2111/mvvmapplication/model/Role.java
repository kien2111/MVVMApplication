package com.mvvm.kien2111.mvvmapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WhoAmI on 30/03/2018.
 */

public class Role implements Parcelable {
    @SerializedName("idrole")
    @Expose
    private int idrole;

    protected Role(Parcel in) {
        idrole = in.readInt();
        rolename = in.readString();
        status = RoleStatus.mapRole(in.readInt());
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };

    public int getIdrole() {
        return idrole;
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public RoleStatus getStatus() {
        return status;
    }

    public void setStatus(RoleStatus status) {
        this.status = status;
    }

    @SerializedName("rolename")
    @Expose
    private String rolename;

    @SerializedName("status")
    @Expose
    private RoleStatus status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idrole);
        dest.writeString(rolename);
        dest.writeInt(status.getType());
    }

    public enum RoleName{
        ADMIN("admin"),USER("user");
        private final String rolename;
        RoleName(String rolename){
            this.rolename = rolename;
        }

        public static RoleName mapRoleName(String name){
            for(RoleName roleName : values()){
                if(name.equals(roleName.getRolename())){
                    return roleName;
                }
            }
            return USER;
        }

        public String getRolename() {
            return rolename;
        }
    }

    public enum RoleStatus{
        ACTIVE(1),BLOCKED(0);
        private final int type;
        RoleStatus(int usertype){
            this.type = usertype;
        }
        public static RoleStatus mapRole(int type){
            for(RoleStatus role:values()){
                if(type==role.type){
                    return role;
                }
            }
            return RoleStatus.BLOCKED;
        }
        public int getType() {
            return type;
        }
    }
}
