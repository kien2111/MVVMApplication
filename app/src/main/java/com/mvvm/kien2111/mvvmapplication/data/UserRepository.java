package com.mvvm.kien2111.mvvmapplication.data;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.db.UserDao;
import com.mvvm.kien2111.mvvmapplication.interfaces.UserService;
import com.mvvm.kien2111.mvvmapplication.model.Credential;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;
import com.mvvm.kien2111.mvvmapplication.util.LimitFetch;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


/**
 * Created by WhoAmI on 23/01/2018.
 */

public class UserRepository {
    private final UserService userService;
    private final UserDao userDao;
    private AppExecutors appExecutors;

    LimitFetch<String> rateLimit = new LimitFetch<>(10, TimeUnit.MINUTES);
    @Inject
    public UserRepository(UserService userService,UserDao userDao,AppExecutors appExecutors){
        this.userService=userService;
        this.userDao=userDao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Credential>> requestLogin(Credential credential){
        return new EnvelopeBoundResource<Credential, Credential>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Credential item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable Credential data) {
                return data==null||rateLimit.shouldFetch("userrepository");
            }

            @NonNull
            @Override
            protected LiveData<Credential> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<Envelope<Credential>> createCall() {
                return userService.Login(credential);
            }
        }.toLiveData();
    }
    public LiveData<Resource<User>> requestSignUp(User user){
        return new EnvelopeBoundCompletable<User, User>() {
            @NonNull
            @Override
            protected LiveData<Envelope> createCompletableCall() {
                return userService.SignUp(user);
            }
        }.toLiveData();
    }

}
