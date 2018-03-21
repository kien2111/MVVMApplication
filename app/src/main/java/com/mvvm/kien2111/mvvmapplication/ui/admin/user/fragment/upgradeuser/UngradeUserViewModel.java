package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.upgradeuser;

import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 3/7/2018.
 */

public class UngradeUserViewModel extends BaseViewModel {
    @Inject
    public UngradeUserViewModel(EventBus eventBus){
        super(eventBus);
    }
}
