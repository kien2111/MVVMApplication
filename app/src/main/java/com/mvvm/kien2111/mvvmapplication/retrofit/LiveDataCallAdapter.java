package com.mvvm.kien2111.mvvmapplication.retrofit;


import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import android.arch.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WhoAmI on 05/02/2018.
 */

public class LiveDataCallAdapter<R> implements CallAdapter<R,LiveData<Envelope<R>>> {
    private final Type responseType;

    public LiveDataCallAdapter(Type responseType){
        this.responseType = responseType;
    }
    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<Envelope<R>> adapt(Call<R> call) {
        return new LiveData<Envelope<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive(){
                super.onActive();
                if(started.compareAndSet(false,true)){
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            //postValue(new Envelope<>(response));
                        }
                        @Override
                        public void onFailure(Call<R> call, Throwable throwable) {
                            //postValue(new Envelope<>(throwable));
                        }
                    });
                }
            }

        };
    }
}
