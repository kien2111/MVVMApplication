package com.mvvm.kien2111.mvvmapplication.base;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class BaseMessage {
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public BaseMessage(){}

    public BaseMessage(Throwable throwable){this.throwable=throwable;}

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    private Throwable throwable;
    private String errorMessage;
    public BaseMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

}
