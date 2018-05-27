package com.mvvm.kien2111.fastjob.data.remote;

import com.mvvm.kien2111.fastjob.data.remote.model.TopUpRequest;
import com.mvvm.kien2111.fastjob.model.TableExchangeModel;
import com.mvvm.kien2111.fastjob.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by WhoAmI on 21/04/2018.
 */

public interface UserLoggedInService {
    @Multipart
    @PUT("/Users/updateprofile")
    Completable updateProfile(@Part("user") RequestBody user,
                              @Part List<MultipartBody.Part> parts);

    @GET("/Users/fetchtableexchange")
    Single<List<TableExchangeModel>> fetchTableExchange();

    @PUT("/Users/topupmoney")
    Completable topUpMoney(@Body TopUpRequest topUpRequest);

    @PUT("/Users/updateprofilewithoutimage")
    Completable updateProfileWithOutImage(@Body User user);

    @GET("/Users/fetchremoteuserdata")
    Single<User> fetchRemoteData(@Query("iduser")String iduser);
}
