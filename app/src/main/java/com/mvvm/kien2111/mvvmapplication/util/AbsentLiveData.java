package com.mvvm.kien2111.mvvmapplication.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by WhoAmI on 05/02/2018.
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData(){
        postValue(null);
    }
    public static <T> LiveData<T> create(){
        return new AbsentLiveData();
    }
}
