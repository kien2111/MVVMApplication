package com.mvvm.kien2111.mvvmapplication.retrofit;

import android.arch.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by WhoAmI on 05/02/2018.
 */

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    private static LiveDataCallAdapterFactory factory;
    public static LiveDataCallAdapterFactory create(){
        if(factory==null){
            factory = new LiveDataCallAdapterFactory();
        }
        return factory;
    }
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(returnType)!= LiveData.class){
            return null;
        }
        Type observableType = getParameterUpperBound(0,(ParameterizedType)returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if(rawObservableType!=Envelope.class){
            throw new IllegalArgumentException("type must be a resource");
        }
        if(!(observableType instanceof  ParameterizedType)){
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0,(ParameterizedType)observableType);
        return new LiveDataCallAdapter<>(bodyType);
    }
}
