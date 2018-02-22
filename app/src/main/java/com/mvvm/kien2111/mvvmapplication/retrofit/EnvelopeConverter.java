package com.mvvm.kien2111.mvvmapplication.retrofit;

import com.mvvm.kien2111.mvvmapplication.exception.ApiException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class EnvelopeConverter<T> implements Converter<ResponseBody,T> {
    final Converter<ResponseBody,Envelope<T>> delegate;

    public EnvelopeConverter(Converter<ResponseBody,Envelope<T>> delegate){
        this.delegate = delegate;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        Envelope<T> envelope = delegate.convert(value);
        return envelope.getData();

    }
}
