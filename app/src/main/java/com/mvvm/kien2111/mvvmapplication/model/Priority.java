package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 05/02/2018.
 */

public enum Priority {
    @SerializedName("0")
    BASIC(0),
    @SerializedName("1")
    MEDIUM(1),
    @SerializedName("2")
    PREMIUM(2);

    private final int type;
    Priority(int value){
        this.type = value;
    }

    public int getType() {
        return type;
    }
}
