package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public enum  Permission {
    BASIC("basic"),MEDIUM("medium"),PREMIUM("premium")
    ;
    private final String typePermission;
    Permission(String permission){
        this.typePermission=permission;
    }

    public String getTypePermission() {
        return typePermission;
    }
}
