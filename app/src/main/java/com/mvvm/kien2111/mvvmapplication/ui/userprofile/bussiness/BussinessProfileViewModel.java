package com.mvvm.kien2111.mvvmapplication.ui.userprofile.bussiness;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 26/04/2018.
 */

public class BussinessProfileViewModel extends BaseViewModel {
    @Inject
    public BussinessProfileViewModel(EventBus eventBus) {
        super(eventBus);
    }
}
