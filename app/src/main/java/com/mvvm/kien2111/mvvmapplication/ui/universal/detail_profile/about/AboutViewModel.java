package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.about;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 04/04/2018.
 */

public class AboutViewModel extends BaseViewModel {
    @Inject
    public AboutViewModel(EventBus eventBus) {
        super(eventBus);
    }
}
