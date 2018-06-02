package com.mvvm.kien2111.fastjob.model;

/**
 * Created by WhoAmI on 18/04/2018.
 */

public class LoadMoreState {
    public final Boolean isRunning;
    public final String errorMessage;
    public LoadMoreState(Boolean isRunning,String errorMessage){
        this.isRunning = isRunning;
        this.errorMessage = errorMessage;
    }
}
