package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;

import java.util.Objects;

/**
 * Created by WhoAmI on 06/02/2018.
 */

public abstract class EnvelopeBoundResource<ResultType,RequestType> extends EnvelopeBound<ResultType> {
    private AppExecutors appExecutors;

    @MainThread
    EnvelopeBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));//set livedata loading sync
        LiveData<ResultType> dbSource = loadFromDb();//load from room database
        result.addSource(dbSource, data -> {//this data have type of ResultType
            result.removeSource(dbSource);//remove listen change from dbsource
            if (shouldFetch(data)) {//should fetch from network
                fetchFromNetwork(dbSource);//put local data as parameter
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));//newdata is result type
            }
        });
    }





    private void fetchFromNetwork(final LiveData<ResultType> roomSource) {
        LiveData<Envelope<RequestType>> networkResponse = createCall();//create call with retrofit2
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(roomSource, newData -> setValue(Resource.loading(newData)));//this line means we want to get the lastest value of livedata roomSource
        result.addSource(networkResponse, response -> {//this response is Envelope<RequestType>
            result.removeSource(networkResponse);//stop listen livedata networkResponse
            result.removeSource(roomSource);//stop listen livedata roomSource
            //noinspection ConstantConditions
            if (response.isSuccessful()) {//check call network is success or not
                appExecutors.getDiskIO().execute(() -> {//execute diskIO in another thread
                    saveCallResult(processResponse(response));//save it to room database
                    appExecutors.getMainThread().execute(() ->//begin do something in main thread
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(),//load from room database
                                    newData -> setValue(Resource.success(newData)))//set data from local to livedata
                    );
                });
            } else {
                onFetchFailed();//if fail call this method
                result.addSource(roomSource,//get lastest value from roomSource
                        newData -> setValue(Resource.error(response.getErrorMessage(), newData)));//
            }
        });
    }

    protected void onFetchFailed() {
    }



    @WorkerThread
    protected RequestType processResponse(Envelope<RequestType> response) {
        return response.getData();//get core data
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);//save the result from network to room database

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);//where fetch or not

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();//load from room database

    @NonNull
    @MainThread
    protected abstract LiveData<Envelope<RequestType>> createCall();//create call with retrofit2


}
