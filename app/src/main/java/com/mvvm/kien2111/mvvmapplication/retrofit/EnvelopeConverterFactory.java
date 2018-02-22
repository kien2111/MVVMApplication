package com.mvvm.kien2111.mvvmapplication.retrofit;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public final class EnvelopeConverterFactory extends Converter.Factory{
    public static EnvelopeConverterFactory create(){
        return new EnvelopeConverterFactory();
    }
    private EnvelopeConverterFactory(){}
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type,
            Annotation[] annotations,
            Retrofit retrofit) {
        Type envelopeType = Types.newParameterizedType(Envelope.class, type);
        Converter<ResponseBody, Envelope> delegate =
                retrofit.nextResponseBodyConverter(this, envelopeType, annotations);
        return new EnvelopeConverter(delegate);
    }
}
