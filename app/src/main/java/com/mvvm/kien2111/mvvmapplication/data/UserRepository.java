package com.mvvm.kien2111.mvvmapplication.data;


import android.content.SharedPreferences;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginRequest;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.db.UserDao;
import com.mvvm.kien2111.mvvmapplication.data.remote.UserService;
import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;
import com.mvvm.kien2111.mvvmapplication.util.LimitFetch;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by WhoAmI on 23/01/2018.
 */

public class UserRepository {
    private final UserService userService;
    private final UserDao userDao;
    private AppExecutors appExecutors;
    private final PreferenceHelper preferenceHelper;
    LimitFetch<String> rateLimit = new LimitFetch<>(10, TimeUnit.MINUTES);
    @Inject
    public UserRepository(UserService userService, UserDao userDao, AppExecutors appExecutors,PreferenceHelper preferenceHelper){
        this.userService=userService;
        this.userDao=userDao;
        this.appExecutors = appExecutors;
        this.preferenceHelper = preferenceHelper;
    }
    public Single<Envelope<LoginResponse>> loginServer(LoginRequest.ExpressLoginRequest expressLoginRequest){
        return userService.loginExpress(expressLoginRequest);
    }
    public Single<Envelope<LoginResponse>> loginFb(LoginRequest.FacebookLoginRequest facebookLoginRequest){
        return userService.loginFacebook(facebookLoginRequest);
    }
    public Single<Envelope<LoginResponse>> loginGoogle(LoginRequest.GoogleLoginRequest googleLoginRequest){
        return userService.loginGoogle(googleLoginRequest);
    }

    public void updateInfo(int userId,String accessToken,String username,String urlAvatar,String accessTokenType,LoggedInMode mode){
        preferenceHelper.setCurrentUserId(userId);
        preferenceHelper.setAccessToken(accessToken);
        preferenceHelper.setCurrentUsername(username);
        preferenceHelper.setCurrentLoggedInMode(mode);
        preferenceHelper.setAccessTokenType(accessTokenType);
        preferenceHelper.setCurrentUserProfilePicUrl(urlAvatar);
    }
    public void updateAccessTokenOnly(String accessTokenType,String accessToken){
        preferenceHelper.setAccessToken(accessToken);
        preferenceHelper.setAccessTokenType(accessTokenType);
    }

}
