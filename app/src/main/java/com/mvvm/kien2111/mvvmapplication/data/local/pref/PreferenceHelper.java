package com.mvvm.kien2111.mvvmapplication.data.local.pref;

import android.content.SharedPreferences;

import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public final class PreferenceHelper {
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private static final String KEY_CURRENT_USERNAME = "KEY_CURRENT_USERNAME";
    private static final String KEY_CURRENT_USER_ID = "KEY_CURRENT_USER_ID";
    private static final String KEY_CURRENT_USER_PROFILE_PIC_URL = "KEY_CURRENT_USER_PROFILE_PIC_URL";
    private static final String KEY_USER_LOGGED_IN_MODE = "KEY_USER_LOGGED_IN_MODE";
    private static final String KEY_ACCESS_TOKEN_TYPE = "KEY_ACCESS_TOKEN_TYPE";

    private final SharedPreferences mSharedPreferences;
    @Inject
    public PreferenceHelper(SharedPreferences mSharedPreferences){
        this.mSharedPreferences = mSharedPreferences;
    }

    public String getAccessToken() {
        return mSharedPreferences.getString(KEY_ACCESS_TOKEN,"");
    }
    public String getCurrentUserProfilePicUrl(){
        return mSharedPreferences.getString(KEY_CURRENT_USER_PROFILE_PIC_URL,"");
    }
    public String getCurrentUsername(){
        return mSharedPreferences.getString(KEY_CURRENT_USERNAME,"");
    }
    public int getCurrentUserId(){
        return mSharedPreferences.getInt(KEY_CURRENT_USER_ID,0);
    }
    public String getAccessTokenType(){return mSharedPreferences.getString(KEY_ACCESS_TOKEN_TYPE,"");}
    public int getUserLoggedInMode(){
        return mSharedPreferences.getInt(KEY_USER_LOGGED_IN_MODE, LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    public void setAccessToken(String accessToken){
        mSharedPreferences.edit().putString(KEY_ACCESS_TOKEN,accessToken).apply();
    }
    public void setCurrentUsername(String username){
        mSharedPreferences.edit().putString(KEY_CURRENT_USERNAME,username).apply();
    }
    public void setCurrentUserId(int userId){
        mSharedPreferences.edit().putInt(KEY_CURRENT_USER_ID,userId).apply();
    }
    public void setCurrentLoggedInMode(LoggedInMode loggedInMode){
        mSharedPreferences.edit().putInt(KEY_USER_LOGGED_IN_MODE,loggedInMode.getType()).apply();
    }
    public void setCurrentUserProfilePicUrl(String url){
        mSharedPreferences.edit().putString(KEY_CURRENT_USER_PROFILE_PIC_URL,url).apply();
    }
    public void setAccessTokenType(String accessTokenType){
        mSharedPreferences.edit().putString(KEY_ACCESS_TOKEN_TYPE,accessTokenType).apply();
    }

}
