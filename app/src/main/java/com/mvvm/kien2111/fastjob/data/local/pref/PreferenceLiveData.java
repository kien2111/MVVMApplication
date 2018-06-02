package com.mvvm.kien2111.fastjob.data.local.pref;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.remote.model.LoginResponse;
import com.mvvm.kien2111.fastjob.model.User;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 28/04/2018.
 */

public class PreferenceLiveData extends LiveData<User> {
    private PreferenceHelper preferenceHelper;
    private PreferenceHelper.LocalDataListener mListener = new PreferenceHelper.LocalDataListener() {
        @Override
        public void onChanged(@Nullable User user) {
            postValue(user);
        }
    };

    @Inject
    public PreferenceLiveData(PreferenceHelper preferenceHelper, UserRepository repository){
        this.preferenceHelper = preferenceHelper;
        repository.syncLocalVersusPromoteData().subscribeOn(Schedulers.io())
                .subscribe(loginResponse -> {
                    this.setUser(loginResponse.getUser());
                },throwable -> {
                    this.setUser(preferenceHelper.getUserData().getUser());
                });

    }

    @MainThread
    public void setUser(User user){
        postValue(user);
    }

    @MainThread
    public void syncData(LoginResponse loginResponse){
        postValue(loginResponse.getUser());
        preferenceHelper.setUserData(loginResponse);
    }

    @Override
    protected void onActive() {
        preferenceHelper.setOnDataListener(mListener);
    }

    @Override
    protected void onInactive() {
        preferenceHelper.removeOnDataListener(mListener);
    }
}
