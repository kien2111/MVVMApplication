package com.mvvm.kien2111.fastjob.exception;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class CustomException extends Exception {
    public CustomException(String message,Throwable throwable){
        super(message,throwable);
    }
}
