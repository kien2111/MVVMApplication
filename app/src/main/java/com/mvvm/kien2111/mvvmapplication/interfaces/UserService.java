package com.mvvm.kien2111.mvvmapplication.interfaces;

import android.arch.lifecycle.LiveData;

import com.mvvm.kien2111.mvvmapplication.model.Credential;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;
import retrofit2.http.POST;

/**
 * Created by WhoAmI on 23/01/2018.
 */

public interface UserService {
    @POST("/login")
    LiveData<Envelope<Credential>> Login(Credential credential);

    @POST("/signup")
    LiveData<Envelope> SignUp(User user);
}
