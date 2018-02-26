package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public enum  LoggedInMode {
    LOGGED_IN_MODE_LOGGED_OUT(0),
    LOGGED_IN_MODE_GOOGLE(1),
    LOGGED_IN_MODE_FB(2),
    LOGGED_IN_MODE_EXPRESS(3)
    ;
    private final int mType;
    LoggedInMode(int type){
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
