package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser;

import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 3/7/2018.
 */

public class AllUserViewModel extends BaseViewModel {
    @Inject
    public AllUserViewModel(EventBus eventBus){
        super(eventBus);

    }
}
