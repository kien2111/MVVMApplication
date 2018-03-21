package com.mvvm.kien2111.mvvmapplication.ui.signup;

import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 30/01/2018.
 */

public class SignUpViewModel extends BaseViewModel {
    @Inject
    public SignUpViewModel(EventBus eventBus){
        super(eventBus);
    }
}
