package com.mvvm.kien2111.fastjob.ui.admin.profile;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.UserRepository;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 3/11/2018.
 */

public class AdminManageProfileViewModel extends BaseViewModel {

    @Inject
    public  AdminManageProfileViewModel(EventBus eventBus, UserRepository userRepository){
        super(eventBus);
    }
}
