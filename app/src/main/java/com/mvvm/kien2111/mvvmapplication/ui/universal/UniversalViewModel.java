package com.mvvm.kien2111.mvvmapplication.ui.universal;

import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 31/01/2018.
 */

public class UniversalViewModel extends BaseViewModel<UniversalNavigator> {
    private final UserRepository userRepository;
    @Inject
    UniversalViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void updateAccessToken(String authTokenType,String authToken){
        userRepository.updateAccessTokenOnly(authTokenType,authToken);
    }
}
