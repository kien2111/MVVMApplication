package com.mvvm.kien2111.mvvmapplication.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;


import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by WhoAmI on 07/03/2018.
 */

public class EnvelopeSingle extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(returnType)!= Single.class)
            return null;
        final CallAdapter<Object,Single<Envelope<?>>> delegate =
                (CallAdapter<Object,Single<Envelope<?>>>)retrofit.nextCallAdapter(this,returnType,annotations);
        return new CallAdapter<Object, Object>() {

            @Override
            public Type responseType() {
                return delegate.responseType();
            }

            @Override
            public Object adapt(Call<Object> call) {
                Single<Envelope<?>> o = delegate.adapt(call);
                return o.flatMap(new Function<Envelope<?>, SingleSource<?>>() {
                    @Override
                    public SingleSource<?> apply(Envelope<?> envelope) throws Exception {
                        return Single.error(new Exception(envelope.getErrorMessage()));
                    }
                });
            }
        };
    }

}
