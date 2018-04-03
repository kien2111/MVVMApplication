package com.mvvm.kien2111.mvvmapplication.data.local.pref;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public final class PreferenceHelper {
    private static final String KEY_USER_LOGGED_IN_MODE = "KEY_USER_LOGGED_IN_MODE";
    private static final String KEY_USER_DATA = "KEY_USER_DATA";
    private static final String KEY_ACCOUNT_NAME = "KEY_ACCOUNT_NAME";


    private final Gson gson;
    private final SharedPreferences mSharedPreferences;
    @Inject
    public PreferenceHelper(SharedPreferences mSharedPreferences,Gson gson){
        this.mSharedPreferences = mSharedPreferences;
        this.gson = gson;
    }

    public int getUserLoggedInMode(){
        return mSharedPreferences.getInt(KEY_USER_LOGGED_IN_MODE, LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    public void setCurrentLoggedInMode(LoggedInMode loggedInMode){
        mSharedPreferences.edit().putInt(KEY_USER_LOGGED_IN_MODE,loggedInMode.getType()).apply();
    }
    public LoginResponse getUserData(){
        return gson.fromJson(this.mSharedPreferences.getString(KEY_USER_DATA,""),LoginResponse.class);
    }

    public void setUserData(LoginResponse loginResponse){
        mSharedPreferences.edit().putString(KEY_USER_DATA,gson.toJson(loginResponse)).apply();
    }

    public String getAccountName() {
        return mSharedPreferences.getString(KEY_ACCOUNT_NAME,"");
    }

    public void setKeyAccountName(String accountName) {
        mSharedPreferences.edit().putString(KEY_ACCOUNT_NAME,accountName).apply();
    }
    public void clearPreference(){
        mSharedPreferences.edit().clear().apply();
    }
}
