package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 06/04/2018.
 */

public class ErrorResponse {
    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String message;

    public ErrorResponse(String message,int code){
        this.message = message;
        this.statusCode = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;

    }
}
