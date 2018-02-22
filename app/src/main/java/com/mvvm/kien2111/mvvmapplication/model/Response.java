package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by WhoAmI on 30/01/2018.
 */

public class Response<T> {
    private T data;
    private boolean success;

    public String getMessage() {
        return message;
    }

    private String message;
    private int resultcode;
    public int getResultcode() {
        return resultcode;
    }

    public T getData(){
        return data;
    }
    public boolean getSuccess(){
        return success;
    }
    public void setData(T data){
        this.data = data;
    }
}
