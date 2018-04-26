package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.data.remote.model.admin.AdminCreatedUserResponse;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.admin.UserFilterRequest;
import com.mvvm.kien2111.mvvmapplication.model.BlockUser;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by donki on 3/21/2018.
 */

public interface AdminService {
    @GET("/Admins/fetchuser")
    Single<List<User>> getAllUser();

    @POST("/Admins/getuserblock")
    Single<List<User>> getUserBlock(@Body UserFilterRequest status);

    @POST("/Admins/getuserblock")
    Single<AdminCreatedUserResponse> updateUser(@Body User user);

    @Multipart
    @POST("/Admins/insertuser")
    Completable insertUser(@Part("username") RequestBody username,
                                                @Part("email") RequestBody email,
                                                @Part("password") RequestBody password,
                                                @Part("birthday") RequestBody birthday,
                                                @Part("role") RequestBody role,
                                                @Part MultipartBody.Part avatar);

    @POST("/Admins/blockuser")
    Completable blockuser(@Body List<BlockUser> adminBlockUserRequest);
}

