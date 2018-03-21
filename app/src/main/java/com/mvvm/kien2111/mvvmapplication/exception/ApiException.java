package com.mvvm.kien2111.mvvmapplication.exception;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class ApiException extends RuntimeException {
    public ApiException(String message){
        super(message);
    }
}
