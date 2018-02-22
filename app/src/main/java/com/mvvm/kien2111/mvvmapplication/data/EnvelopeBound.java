package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.model.Resource;

import java.util.Objects;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public abstract class EnvelopeBound<ResultType> {
    protected AppExecutors appExecutors;
    protected final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    public EnvelopeBound(AppExecutors appExecutors){
        this.appExecutors = appExecutors;
    }
    public EnvelopeBound(){}

    @MainThread
    protected void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {//check whether newValue is new or old
            result.setValue(newValue);//if yes do this
        }
    }
    public LiveData<Resource<ResultType>> toLiveData() {
        return result;
    }//convert mediatorlivedata to live data
}
