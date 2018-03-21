package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;

import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 06/02/2018.
 */

public abstract class EnvelopeBoundResource<ResultType,RequestType> {
    private AppExecutors appExecutors;
    protected final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
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


    @MainThread
    protected void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {//check whether newValue is new or old
            result.setValue(newValue);//if yes do this
        }
    }
    public LiveData<Resource<ResultType>> toLiveData() {
        return result;
    }//convert mediatorlivedata to live data


    private void fetchFromNetwork(final LiveData<ResultType> roomSource) {
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(roomSource, newData -> setValue(Resource.loading(newData)));//this line means we want to get the lastest value of livedata roomSource
        createCall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(res->{

            appExecutors.getDiskIO().execute(()->{
                saveCallResult(res.getData());
                appExecutors.getMainThread().execute(()->{
                    result.removeSource(roomSource);
                    result.addSource(loadFromDb(),newdata->result.setValue(Resource.success(newdata)));
                });
            });
        },err->{
            onFetchFailed();
            appExecutors.getMainThread().execute(()->{
                result.removeSource(roomSource);
                result.addSource(roomSource,newdata->result.setValue(Resource.error(err.getMessage(),newdata)));
            });
        });
    }

    protected void onFetchFailed() {
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
    protected abstract Single<Envelope<RequestType>> createCall();//create call with retrofit2


}
