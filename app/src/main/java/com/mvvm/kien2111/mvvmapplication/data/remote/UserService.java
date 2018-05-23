package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginRequest;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.SignUpRequest;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.DetailProfileWithPoint;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @POST("/Users/SignUp")
    Completable signUp(@Body SignUpRequest mSignUpEmployer);

    @GET("/Categories/GetAllCategories")
    Single<List<Category>> getListCategory();

    @GET("/Users/Test")
    Single<Category> getTest();

    @GET("/Users/getdetailprofile")
    Single<User> getDetailProfile(@Query("iduser") String iduser);

    @GET("/Users/syncdata")
    Single<LoginResponse> syncData(@Query("iduser") String iduser);

}
