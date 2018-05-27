package com.mvvm.kien2111.fastjob.data.remote.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by donki on 4/13/2018.
 */

public class AdminCreatedUserResponse {

    @SerializedName("message")
    @Expose
    private String message;


    public AdminCreatedUserResponse(String message) {
        this.message = message;
    }
}
