package com.mvvm.kien2111.fastjob.data.remote.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by donki on 4/24/2018.
 */

public class UserFilterRequest {

    @SerializedName("status")
    @Expose
    int status;

    public UserFilterRequest(int status){
        this.status= status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
