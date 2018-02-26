package com.mvvm.kien2111.mvvmapplication.ui.login;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginRequest;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.exception.UnauthorizedException;
import com.mvvm.kien2111.mvvmapplication.model.Credential;
import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;
import com.mvvm.kien2111.mvvmapplication.model.Resource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by WhoAmI on 21/01/2018.
 */

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private final UserRepository userRepository;
    @Inject
    LoginViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public MutableLiveData<Credential> getCredentialMutableLiveData() {
        if(mCredentialMutableLiveData==null){
            mCredentialMutableLiveData = new MutableLiveData<>();
        }
        return mCredentialMutableLiveData;
    }

    private MutableLiveData<Credential> mCredentialMutableLiveData;


    public void login(String username,String password) throws UnauthorizedException{
        setShowLoading(true);
        compositeDisposable.add(
                userRepository.loginServer(new LoginRequest.ExpressLoginRequest(username,password))
                .doOnSuccess(response->{
                    LoginResponse loginResponse = response.getData();
                    userRepository.updateInfo(loginResponse.getUserId()
                            ,loginResponse.getAccessToken()
                            ,loginResponse.getUserName()
                            ,loginResponse.getServerProfilePicUrl()
                            ,loginResponse.getAuth_token_type()
                            ,LoggedInMode.LOGGED_IN_MODE_EXPRESS);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    setShowLoading(false);
                },throwable -> getNavigator().handleError(throwable)));
    }

}
