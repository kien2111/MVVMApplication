package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.List;

import io.reactivex.Completable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by WhoAmI on 21/04/2018.
 */

public interface UserLoggedInService {
    @Multipart
    @PUT("/Users/updateprofile")
    Completable updateProfile(@Part("user") RequestBody user,
                              @Part List<MultipartBody.Part> parts);

    @PUT("/Users/publishprofile")
    Completable publishProfile(int acceptable);

    @PUT("/Users/updateprofilewithoutimage")
    Completable updateProfileWithOutImage(@Body User user);
}
