package com.mvvm.kien2111.mvvmapplication.data.remote.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by donki on 4/19/2018.
 */

public class AdminBlockUserRequest {
    @SerializedName("iduser")
    @Expose
    String iduser;
    @SerializedName("status")
    @Expose
    int status;
    public AdminBlockUserRequest(String userId,int status){
        this.iduser = userId;
        this.status= status;
    }
}
