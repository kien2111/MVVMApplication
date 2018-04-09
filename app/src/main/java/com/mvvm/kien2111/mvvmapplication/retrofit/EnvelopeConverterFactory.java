package com.mvvm.kien2111.mvvmapplication.retrofit;

import com.mvvm.kien2111.mvvmapplication.exception.ApiException;

import org.apache.commons.lang3.reflect.TypeUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by WhoAmI on 19/03/2018.
 */

public class EnvelopeConverterFactory extends Converter.Factory{
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type envelopeType = TypeUtils.parameterize(Envelope.class,type);
        return new EnvelopeConverter<>(retrofit.nextResponseBodyConverter(this,envelopeType,annotations));
    }
    static class EnvelopeConverter<T> implements Converter<ResponseBody,T>{
        private final Converter<ResponseBody,Envelope<T>> delegate;
        EnvelopeConverter(Converter<ResponseBody,Envelope<T>> delegate){
            this.delegate = delegate;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            Envelope<T> envelope = delegate.convert(value);
            if(envelope.getData()==null){
                if(envelope.getErrorMessage()==null){
                    throw new IllegalArgumentException("Don't know what happened");
                }else{
                    throw ApiException.httpError(null,null,null);
                }
            }
            return envelope.getData();
        }
    }
}
