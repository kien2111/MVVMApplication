package com.mvvm.kien2111.mvvmapplication.data;


import android.accounts.Account;

import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.remote.UserLoggedInService;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginRequest;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.UserDao;
import com.mvvm.kien2111.mvvmapplication.data.remote.UserService;
import com.mvvm.kien2111.mvvmapplication.model.Approve_Publish;
import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.DetailProfileWithPoint;
import com.mvvm.kien2111.mvvmapplication.util.LimitFetch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by WhoAmI on 23/01/2018.
 */

public class UserRepository {
    private final UserService userService;
    private final UserDao userDao;
    private final AppExecutors appExecutors;
    private final PreferenceHelper preferenceHelper;
    private final UserLoggedInService userLoggedInService;
    private final Gson gson;
    LimitFetch<String> rateLimit = new LimitFetch<>(10, TimeUnit.MINUTES);
    @Inject
    public UserRepository(UserService userService,
                          UserLoggedInService userLoggedInService,
                          UserDao userDao,
                          AppExecutors appExecutors,
                          Gson gson,
                          PreferenceHelper preferenceHelper){
        this.userService=userService;
        this.userDao=userDao;
        this.gson = gson;
        this.userLoggedInService = userLoggedInService;
        this.appExecutors = appExecutors;
        this.preferenceHelper = preferenceHelper;
    }
    public Single<LoginResponse> loginServer(LoginRequest.ExpressLoginRequest expressLoginRequest){
        return userService.loginExpress(expressLoginRequest);
    }
    public Single<LoginResponse> loginFb(LoginRequest.FacebookLoginRequest facebookLoginRequest){
        return userService.loginFacebook(facebookLoginRequest);
    }
    public Single<LoginResponse> loginGoogle(LoginRequest.GoogleLoginRequest googleLoginRequest){
        return userService.loginGoogle(googleLoginRequest);
    }
    public void updateInfo(LoginResponse response,
                           LoggedInMode mode,
                           Account account){
        preferenceHelper.setUserData(response);
        preferenceHelper.setCurrentLoggedInMode(mode);
        preferenceHelper.saveCurrentAccount(account);
    }

    public LoginResponse getUserData(){
        return preferenceHelper.getUserData();
    }

    public Flowable<DetailProfileWithPoint> getDetailProfile(String userid){
        return userService
                .getDetailProfile(userid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    public Single<Category> getCategory(){
        return userService.getTest().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Completable updateProfileWithoutImage(final User user){
        LoginResponse loginResponse = preferenceHelper.getUserData();
        loginResponse.setUser(user);
        return userLoggedInService
                .updateProfileWithOutImage(user)
                .doOnComplete(() -> {
                    preferenceHelper.setUserData(loginResponse);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable updateProfile(final User user){
        RequestBody userDetail = RequestBody.create(MultipartBody.FORM,gson.toJson(user));
        List<MultipartBody.Part> partList = new ArrayList<>();
        if(user.getAvatar()!=null){
            File avatarfile = new File(user.getAvatar());
            RequestBody avatarRequest = RequestBody.create(MediaType.parse("image/*"),avatarfile);
            MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar",avatarfile.getName(),avatarRequest);
            partList.add(avatarPart);
        }
        if(user.getLogo_company()!=null){
            File logofile = new File(user.getLogo_company());
            RequestBody logoRequest = RequestBody.create(MediaType.parse("image/*"),logofile);
            MultipartBody.Part logoPart = MultipartBody.Part.createFormData("logo",logofile.getName(),logoRequest);
            partList.add(logoPart);
        }
        LoginResponse loginResponse = preferenceHelper.getUserData();
        loginResponse.setUser(user);
        return userLoggedInService
                .updateProfile(userDetail,partList)
                .doOnComplete(() -> {
                    preferenceHelper.setUserData(loginResponse);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
    public Completable publishProfile(final Approve_Publish approve_publish){
        return userLoggedInService
                .publishProfile(approve_publish.getType())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
