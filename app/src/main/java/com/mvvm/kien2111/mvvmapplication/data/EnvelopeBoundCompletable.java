package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public abstract class EnvelopeBoundCompletable<ResultType,RequestType> extends EnvelopeBound<ResultType> {

    @MainThread
    EnvelopeBoundCompletable(){
        result.setValue(Resource.loading(null));
        LiveData<Envelope> confirm = createCompletableCall();
        result.addSource(confirm,newdata->{//newdata is Envelope
            result.removeSource(confirm);//clear lastest value
            if(newdata.isSuccessful()){
                setValue(Resource.success(null));
            }else{
                setValue(Resource.error(newdata.getErrorMessage(),newdata.getResponseCode()));
            }
        });
    }
    @NonNull
    @MainThread
    protected abstract LiveData<Envelope> createCompletableCall();
}
