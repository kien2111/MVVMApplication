package com.mvvm.kien2111.fastjob.model;

/**
 * Created by WhoAmI on 25/03/2018.
 */

public enum  Approve_Publish {
    NOT_DO_ANYTHING(0),ACCEPT(1),CONFLICT(2),ADMIN_BLOCKED(3);
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
        return NOT_DO_ANYTHING;
    }
}
