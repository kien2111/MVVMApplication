package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginRequest;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.SignUpRequest;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by WhoAmI on 23/01/2018.
 */

public interface UserService {
    @POST("/Users/Login")
    Single<LoginResponse> loginExpress(@Body LoginRequest.ExpressLoginRequest expressLoginRequest);

    @POST("/google")
    Single<LoginResponse> loginGoogle(@Body LoginRequest.GoogleLoginRequest googleLoginRequest);

    @POST("/facebook")
    Single<LoginResponse> loginFacebook(@Body LoginRequest.FacebookLoginRequest facebookLoginRequest);

    @POST("/Users/signupemployer")
    Completable SignUpEmployer(@Body SignUpRequest mSignUpEmployer);

    @POST("/Accounts/signupemployee")
    Completable SignUpEmployee(@Body SignUpRequest mSignUpEmployee);

    @GET("/Categories/GetAllCategories")
    Single<List<Category>> getListCategory();

}
