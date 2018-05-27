package com.mvvm.kien2111.fastjob.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 30/03/2018.
 */

public class Role implements Parcelable {
    @SerializedName("idrole")
    @Expose
    private int idrole;

    public Role(int idrole, String rolename, RoleStatus status) {
        this.idrole = idrole;
        this.rolename = rolename;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s",this.getRolename());
    }

    @SerializedName("rolename")
    @Expose
    private String rolename;

    @SerializedName("status")
    @Expose
    private RoleStatus status;

    public Role(Parcel in) {
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

    public enum RoleMap {
        ADMIN("admin",0),USER("user",1);
        private final String rolename;
        private final int type;
        RoleMap(String rolename, int type){
            this.rolename = rolename;this.type=type;
        }

        public int getType() {
            return type;
        }

        public static RoleMap mapRoleType(int type){
            for(RoleMap roleName : values()){
                if(type==roleName.getType()){
                    return roleName;
                }
            }
            return USER;
        }
        public static RoleMap mapRoleName(String name){
            for(RoleMap roleName : values()){
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
