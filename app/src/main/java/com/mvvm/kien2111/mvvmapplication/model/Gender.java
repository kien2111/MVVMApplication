package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 26/02/2018.
 */

public enum  Gender {
    MALE(0),
    FEMALE(1),
    UNKNOWN(2);
    private final int type;
    Gender(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static Gender mapGender(int type){
        for(Gender g : values()){
            if(g.type == type){
                return g;
            }
        }
        return Gender.MALE;
    }
}
