package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginRequest;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;

import io.reactivex.Single;
import retrofit2.http.POST;

/**
 * Created by WhoAmI on 23/01/2018.
 */

public interface UserService {
    @POST("/login")
    Single<Envelope<LoginResponse>> loginExpress(LoginRequest.ExpressLoginRequest expressLoginRequest);

    @POST("/google")
    Single<Envelope<LoginResponse>> loginGoogle(LoginRequest.GoogleLoginRequest googleLoginRequest);

    @POST("/facebook")
    Single<Envelope<LoginResponse>> loginFacebook(LoginRequest.FacebookLoginRequest facebookLoginRequest);

    @POST("/signup")
    Single<Envelope> SignUp(User user);
}
