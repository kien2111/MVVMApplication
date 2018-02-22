package com.mvvm.kien2111.mvvmapplication.retrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class AttachTokenLoggingInterceptor implements Interceptor {
    private String authToken;
    private AttachTokenLoggingInterceptor(Builder builder) {
        this.authToken = builder.authToken;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request().newBuilder().header("Authorization",authToken).build();
        return chain.proceed(original);
    }
    public static class Builder{
        String authToken;
        public AttachTokenLoggingInterceptor build(){
            return new AttachTokenLoggingInterceptor(this);
        }
        public Builder setToken(String token){
            this.authToken = token;
            return this;
        }
    }
}
