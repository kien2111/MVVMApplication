package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 05/02/2018.
 */

public enum Priority {
    FREE(0),
    BASIC(1),
    MEDIUM(2),
    PREMIUM(3);

    private final int type;
    Priority(int value){
        this.type = value;
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
        return FREE;
    }
}
