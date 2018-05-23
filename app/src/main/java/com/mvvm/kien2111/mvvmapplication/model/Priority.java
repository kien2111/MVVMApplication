package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 05/02/2018.
 */

public enum Priority {
    FREE(0,"Free Profile"),
    BASIC(1,"Basic Profile"),
    MEDIUM(2,"Medium Profile"),
    PREMIUM(3,"Premium Profile"),
    ALL(4,"All");

    private final int type;
    private final String name;
    Priority(int value,String name){
        this.type = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static Priority mapPriority(int type){
        for(Priority priority : values()){
            if(type == priority.getType()){
                return priority;
            }
        }
        return ALL;
    }

    @Override
    public String toString() {
        return name;
    }
}
