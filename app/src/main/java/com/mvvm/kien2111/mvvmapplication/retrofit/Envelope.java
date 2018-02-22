package com.mvvm.kien2111.mvvmapplication.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.exception.CustomException;

import java.io.IOException;

import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class Envelope<T> {
    @Nullable
    private final String errorMessage;

    @NonNull
    private final int responseCode;

    @Nullable
    private final T data;
    public Envelope(Throwable throwable){
        responseCode = 500;
        data = null;
        errorMessage = throwable.getMessage();
    }
    public Envelope(Response<T> response){
        responseCode = response.code();
        if(response.isSuccessful()){
            data = response.body();
            errorMessage = null;
        }else{
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Timber.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            data = null;
        }

    }
    public boolean isSuccessful() {
        return responseCode >= 200 && responseCode < 300;
    }

    @NonNull
    public String getErrorMessage() {
        return errorMessage;
    }

    @NonNull
    public int getResponseCode() {
        return responseCode;
    }

    @NonNull
    public T getData() {
        return data;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Envelope<?> envelope = (Envelope<?>) o;

        if (responseCode != envelope.responseCode) {
            return false;
        }
        if (errorMessage != null ? !errorMessage.equals(envelope.errorMessage) : envelope.errorMessage != null) {
            return false;
        }
        return data != null ? data.equals(envelope.data) : envelope.data == null;
    }
    @Override
    public String toString() {
        return "Resource{" +
                "code=" + responseCode +
                ", message='" + errorMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
