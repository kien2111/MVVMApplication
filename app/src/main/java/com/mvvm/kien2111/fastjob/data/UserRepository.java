package com.mvvm.kien2111.fastjob.data;


import android.accounts.Account;

import com.google.gson.Gson;
import com.mvvm.kien2111.fastjob.AppExecutors;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.fastjob.data.remote.UserLoggedInService;
import com.mvvm.kien2111.fastjob.data.remote.model.LoginRequest;
import com.mvvm.kien2111.fastjob.data.remote.model.LoginResponse;
import com.mvvm.kien2111.fastjob.data.local.db.dao.UserDao;
import com.mvvm.kien2111.fastjob.data.remote.UserService;
import com.mvvm.kien2111.fastjob.data.remote.model.SignUpRequest;
import com.mvvm.kien2111.fastjob.data.remote.model.TopUpRequest;
import com.mvvm.kien2111.fastjob.model.LoggedInMode;
import com.mvvm.kien2111.fastjob.model.TableExchangeModel;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.util.LimitFetch;
import com.mvvm.kien2111.fastjob.util.StringUtil;

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

    public Single<User> fetchRemoteData(final String iduser){
        return userLoggedInService.fetchRemoteData(iduser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public void updateUser(LoginResponse response){
        preferenceHelper.setUserData(response);
    }

    public Completable signUp(final SignUpRequest signUpRequest){
        return userService.signUp(signUpRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<LoginResponse> syncLocalVersusPromoteData(){
        return userService.syncData(preferenceHelper.getUserData().getUser().getUserId())
                .doOnSuccess(preferenceHelper::setUserData)
                .subscribeOn(Schedulers.io());
    }

    public LoginResponse getUserData(){
        return preferenceHelper.getUserData();
    }

    public Flowable<User> getDetailProfile(String userid){
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

    public Completable updateBothImage(final User user){
        RequestBody userDetail = RequestBody.create(MultipartBody.FORM,gson.toJson(user));
        List<MultipartBody.Part> partList = new ArrayList<>();
        File avatarfile = new File(user.getAvatar());
        RequestBody avatarRequest = RequestBody.create(MediaType.parse("image/*"),avatarfile);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar",avatarfile.getName(),avatarRequest);
        partList.add(avatarPart);
        user.setAvatar(StringUtil.getFileNameFromPath(user.getAvatar()));
        File logofile = new File(user.getLogo_company());
        RequestBody logoRequest = RequestBody.create(MediaType.parse("image/*"),logofile);
        MultipartBody.Part logoPart = MultipartBody.Part.createFormData("logo",logofile.getName(),logoRequest);
        partList.add(logoPart);
        user.setLogo_company(StringUtil.getFileNameFromPath(user.getLogo_company()));
        return userLoggedInService
                .updateProfile(userDetail,partList)
                .doOnComplete(() -> syncLocalRemoteData(user))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    public Completable updateLogoOnly(final User user){
        RequestBody userDetail = RequestBody.create(MultipartBody.FORM,gson.toJson(user));
        List<MultipartBody.Part> partList = new ArrayList<>();
        File logofile = new File(user.getLogo_company());
        RequestBody logoRequest = RequestBody.create(MediaType.parse("image/*"),logofile);
        MultipartBody.Part logoPart = MultipartBody.Part.createFormData("logo",logofile.getName(),logoRequest);
        partList.add(logoPart);
        user.setLogo_company(StringUtil.getFileNameFromPath(user.getLogo_company()));
        return userLoggedInService
                .updateProfile(userDetail,partList)
                .doOnComplete(() -> syncLocalRemoteData(user))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
    public Completable updateAvatarOnly(final User user){
        RequestBody userDetail = RequestBody.create(MultipartBody.FORM,gson.toJson(user));
        List<MultipartBody.Part> partList = new ArrayList<>();
        File avatarfile = new File(user.getAvatar());
        RequestBody avatarRequest = RequestBody.create(MediaType.parse("image/*"),avatarfile);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar",avatarfile.getName(),avatarRequest);
        partList.add(avatarPart);
        user.setAvatar(StringUtil.getFileNameFromPath(user.getAvatar()));
        return userLoggedInService
                .updateProfile(userDetail,partList)
                .doOnComplete(() -> syncLocalRemoteData(user))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private void syncLocalRemoteData(User user){
        LoginResponse loginResponse = preferenceHelper.getUserData();
        loginResponse.setUser(user);
        preferenceHelper.setUserData(loginResponse);
    }

    public Completable topUpMoney(TopUpRequest topUpRequest){
        return userLoggedInService.topUpMoney(topUpRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
    }

    public Single<List<TableExchangeModel>> fetchTableExchange(){
        return userLoggedInService.fetchTableExchange()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
    }

    public void clearUserData(){
        preferenceHelper.clearPreference();
    }

    public Account getCurrentAccount(){
        return preferenceHelper.getCurrentAccount();
    }

}
