package com.mvvm.kien2111.mvvmapplication.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.exception.PermitException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by WhoAmI on 27/02/2018.
 */
@Singleton
public final class RoleCollection {
    public enum RoleIndex{
        ADMIN(1),EMPLOYER(2),EMPLOYEE(3);
        private final int type;
        RoleIndex(int index){
            this.type = index;
        }
        public int getType() {
            return type;
        }
    }
    public List<Role> getRoleList() {
        return roleList;
    }
    private List<Role> roleList =Arrays.asList(
            new Role(1,"admin"),
            new Role(2,"employer"),
            new Role(3,"employee"));
    public void checkAdminRole(List<Role> roles) throws PermitException {
        if(!roles.contains(roleList.get(RoleIndex.ADMIN.getType()))){
            throw new PermitException("You are not admin");
        }
    }
    public void checkEmployeeRole(List<Role> roles) throws PermitException {
        if(!roles.contains(roleList.get(RoleIndex.EMPLOYEE.getType()))){
            throw new PermitException("You are not employee");
        }
    }
    public void checkEmployerRole(List<Role> roles)throws PermitException {
        if(!roles.contains(roleList.get(RoleIndex.EMPLOYER.getType()))){
            throw new PermitException("You are not employer");
        }
    }
}
