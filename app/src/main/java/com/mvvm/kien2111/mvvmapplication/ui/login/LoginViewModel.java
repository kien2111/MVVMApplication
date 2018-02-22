package com.mvvm.kien2111.mvvmapplication.ui.login;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.exception.UnauthorizedException;
import com.mvvm.kien2111.mvvmapplication.model.Credential;

import javax.inject.Inject;


/**
 * Created by WhoAmI on 21/01/2018.
 */

public class LoginViewModel extends ViewModel {

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


    public void onRequestLogin(Credential credential) throws UnauthorizedException{

    }

}
