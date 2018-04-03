package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by WhoAmI on 25/03/2018.
 */

public enum  Approve_Publish {
    ON_PROGRESS(0),DECLINE(1),ACCEPT(2),CONFLICT(3);
    final int type;
    Approve_Publish(int type){
        this.type = type;
    }
    public int getType() {
        return type;
    }
    public static Approve_Publish mapApprovePublish(int type){
        for(Approve_Publish approve_publish : values()){
            if(type == approve_publish.getType()){
                return approve_publish;
            }
        }
        return ON_PROGRESS;
    }
}