package com.mvvm.kien2111.mvvmapplication.exception;

/**
 * Created by WhoAmI on 30/01/2018.
 */

public class TokenExpiredException extends Exception {
    TokenExpiredException(String message){
        super(message);
    }
}
