package com.mvvm.kien2111.mvvmapplication.data.local.pref;

import android.accounts.Account;
import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by WhoAmI on 24/02/2018.
 */
@Singleton
public final class PreferenceHelper {
    private static final String KEY_USER_LOGGED_IN_MODE = "KEY_USER_LOGGED_IN_MODE";
    private static final String KEY_USER_DATA = "KEY_USER_DATA";
    private static final String KEY_ACCOUNT_NAME = "KEY_ACCOUNT_NAME";
    private static final String KEY_CURRENT_ACCOUNT = "KEY_CURRENT_ACCOUNT";
    private List<LocalDataListener> mListListener = new ArrayList<>();

    public void setOnDataListener(LocalDataListener listener){
        if(listener!=null){
            mListListener.add(listener);
        }

    }
    public void removeOnDataListener(LocalDataListener listener){
        if(listener!=null){
            mListListener.remove(listener);
        }
    }

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
        for(LocalDataListener listener : mListListener){
            listener.onChanged(loginResponse.getUser());
        }
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
    public void saveCurrentAccount(Account account){
        mSharedPreferences.edit().putString(KEY_CURRENT_ACCOUNT,gson.toJson(account)).apply();
    }
    public Account getCurrentAccount(){
        return gson.fromJson(mSharedPreferences.getString(KEY_CURRENT_ACCOUNT,null),Account.class);
    }

    public interface LocalDataListener extends Observer<User>{
    }

    private static final String KEY_CITIES_POSITION_PICK = "KEY CITIES POSITION PICK";
    private static final String KEY_DISTRICTS_POSITION_PICK = "KEY DISTRICTS POSITION PICK";

    public void saveCitiesPositionPick(int position){
        mSharedPreferences.edit().putInt(KEY_CITIES_POSITION_PICK,position).apply();
    }
    public int getCitiesPositionPick(){
        return mSharedPreferences.getInt(KEY_CITIES_POSITION_PICK,0);
    }

    public void saveDistrictPositionPick(int position){
        mSharedPreferences.edit().putInt(KEY_DISTRICTS_POSITION_PICK,position).apply();
    }
    public int getDistrictPositionPick(){
        return mSharedPreferences.getInt(KEY_DISTRICTS_POSITION_PICK,0);
    }

}
