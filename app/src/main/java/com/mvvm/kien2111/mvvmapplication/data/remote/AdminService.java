package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by donki on 3/21/2018.
 */

public interface AdminService {
    @GET("/Admins/fetchuser")
    Single<List<User>> getAllUser();

}
