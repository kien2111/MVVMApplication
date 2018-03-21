package com.mvvm.kien2111.mvvmapplication.ui.universal.user;

import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public class UserViewModel extends BaseViewModel {
    @Inject
    public UserViewModel(EventBus eventBus){
        super(eventBus);
    }
}
