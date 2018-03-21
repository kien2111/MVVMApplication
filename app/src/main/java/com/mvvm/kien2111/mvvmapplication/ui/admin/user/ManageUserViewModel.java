package com.mvvm.kien2111.mvvmapplication.ui.admin.user;

import android.databinding.ObservableField;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 3/6/2018.
 */

public class ManageUserViewModel extends BaseViewModel{
    @Inject
    public  ManageUserViewModel(EventBus eventBus){
        super(eventBus);
    }
}
