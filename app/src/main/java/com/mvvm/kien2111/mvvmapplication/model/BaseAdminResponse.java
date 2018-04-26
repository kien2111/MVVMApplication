package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by donki on 4/16/2018.
 */

public class BaseAdminResponse<T> {
    public enum State{
        SUCESS,FAIL
    }
    String message;
    State state;
    T data;
    Throwable throwable;

    public BaseAdminResponse(State state,String message,Throwable throwable) {
        this.message = message;
        this.state = state;
        this.throwable = throwable;
    }
    public BaseAdminResponse(State state,String message) {
        this.message = message;
        this.state = state;
    }

    public BaseAdminResponse(State state, T data) {
        this.message = message;
        this.state = state;
        this.data = data;
    }

    public static  <T> BaseAdminResponse<T> error(String message){
        return new BaseAdminResponse<T>(State.FAIL,message);
    }

    public static  <T> BaseAdminResponse<T> error(String message,Throwable throwable){
        return new BaseAdminResponse<T>(State.FAIL,message,throwable);
    }

    public static <T> BaseAdminResponse<T> success(T data){
        return new BaseAdminResponse<T>(State.SUCESS,data);
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
