package com.mvvm.kien2111.mvvmapplication.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by WhoAmI on 07/03/2018.
 */

public class EnvelopeObservable extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(returnType)!= Observable.class)
            return null;
        final CallAdapter<Object,Observable<Envelope<?>>> delegate =
                (CallAdapter<Object,Observable<Envelope<?>>>)retrofit.nextCallAdapter(this,returnType,annotations);
        return new CallAdapter<Object, Object>() {

            @Override
            public Type responseType() {
                return delegate.responseType();
            }

            @Override
            public Object adapt(Call<Object> call) {
                Observable<Envelope<?>> o = delegate.adapt(call);
                return o.flatMap(new Function<Envelope<?>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Envelope<?> envelope) throws Exception {
                        return Observable.error(new Exception(envelope.getErrorMessage()));
                    }
                });
            }
        };
    }

}
