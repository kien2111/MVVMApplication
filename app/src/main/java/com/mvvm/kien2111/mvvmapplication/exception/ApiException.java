package com.mvvm.kien2111.mvvmapplication.exception;

import android.util.Log;

import com.mvvm.kien2111.mvvmapplication.model.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public final class ApiException extends RuntimeException{
    private final ErrorType errorType;
    private final String url;
    private final Retrofit retrofit;
    private final Response response;

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getUrl() {
        return url;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Response getResponse() {
        return response;
    }


    public enum ErrorType{
        BlockedAccount,
        UnAuthorized,
        NotPermit,
        UnExpected,
        NetWorkProblem,
        TaskFailed,
        Test
    }
    public ApiException(String message,String url,Response response,ErrorType errorType,Throwable cause,Retrofit retrofit){
        super(message,cause);
        this.errorType = errorType;
        this.url = url;
        this.retrofit = retrofit;
        this.response = response;
    }
    public static ApiException httpError(String url, Response response, Retrofit retrofit) throws IOException {
        String message = response.errorBody().string();
        ErrorType localErrorType = ErrorType.UnExpected;
        switch (response.code()){
            case 301: localErrorType = ErrorType.BlockedAccount;break;
            case 302: localErrorType = ErrorType.UnAuthorized;break;
            case 303: localErrorType = ErrorType.NotPermit;break;
            case 304: localErrorType = ErrorType.TaskFailed;break;
            case 305: localErrorType = ErrorType.Test;break;
            case 511: localErrorType = ErrorType.BlockedAccount;break;
            case 512: localErrorType = ErrorType.BlockedAccount;break;
        }
        return new ApiException(message,url,response,localErrorType,null,retrofit);
    }
    public static ApiException unExpectedError(Throwable cause){
        return new ApiException(cause.getMessage(),null,null,ErrorType.UnExpected,cause,null);
    }
    public static ApiException networkError(IOException exception){
        return new ApiException(exception.getMessage(),null,null,ErrorType.NetWorkProblem,exception,null);
    }

    /*public <T> ErrorResponse getErrorBody(Class<T> clazz) throws IOException {
        if(response == null || response.errorBody()==null || retrofit == null){
            return null;
        }
        Converter<ResponseBody,ErrorResponse> converter = retrofit.responseBodyConverter(clazz,new Annotation[0]);
        return converter.convert(response.errorBody());
    }*/

}
