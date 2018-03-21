package com.mvvm.kien2111.mvvmapplication.ui.admin.profile;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;

import javax.inject.Inject;

/**
 * Created by donki on 3/11/2018.
 */

public class AdminManageProfileViewModel extends BaseViewModel {

    @Inject
    public  AdminManageProfileViewModel(UserRepository userRepository){
    }
}
