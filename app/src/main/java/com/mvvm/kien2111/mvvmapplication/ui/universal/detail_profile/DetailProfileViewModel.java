package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailProfileViewModel extends BaseViewModel {
    @Inject
    public DetailProfileViewModel(EventBus eventBus) {
        super(eventBus);
    }
}
