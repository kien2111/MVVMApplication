package com.mvvm.kien2111.mvvmapplication.ui.universal;

import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.model.Role;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 31/01/2018.
 */

public class UniversalViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    @Inject
    UniversalViewModel(EventBus eventBus, UserRepository userRepository){
        super(eventBus);
        this.userRepository = userRepository;
    }
    public void updateAccessToken(String authTokenType, String authToken, Priority priority, List<Role> roles){
        userRepository.updateAccessTokenOnly(authTokenType,authToken,priority,roles);
    }
}
