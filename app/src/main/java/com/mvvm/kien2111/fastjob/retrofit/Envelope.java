package com.mvvm.kien2111.fastjob.retrofit;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class Envelope<T> {
    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(@Nullable String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    @SerializedName("message")
    @Expose
    private String errorMessage;

    @SerializedName("data")
    @Expose
    private T data;


}
