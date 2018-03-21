package com.mvvm.kien2111.mvvmapplication.data.local.pref;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.model.Role;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public final class PreferenceHelper {
    private static final String KEY_USER_LOGGED_IN_MODE = "KEY_USER_LOGGED_IN_MODE";
    private static final String KEY_USER_DATA = "KEY_USER_DATA";


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
    public String getUserData(){
        return this.mSharedPreferences.getString(KEY_USER_DATA,"");
    }

    public void setUserData(LoginResponse loginResponse){
        mSharedPreferences.edit().putString(KEY_USER_DATA,gson.toJson(loginResponse)).apply();
    }

}
