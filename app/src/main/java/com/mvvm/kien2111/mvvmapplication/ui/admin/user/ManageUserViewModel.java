package com.mvvm.kien2111.mvvmapplication.ui.admin.user;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.AdminRepository;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 3/6/2018.
 */

public class ManageUserViewModel extends BaseViewModel{

    public AdminRepository adminRepository;


    @Inject
    public  ManageUserViewModel(EventBus eventBus,AdminRepository adminRepository){
        super(eventBus);
        this.adminRepository= adminRepository;
    }

    /*public  void gotoBlockUser(List<BlockUser> listblockUser){
        adminRepository.blockUser(listblockUser)
                .subscribe(success -> {
                    AdminBlockUserResponse response = success;
                    eventBus.post(new AllUserViewModel.ResponseBlockServer(response));
                }, throwable -> {
                    eventBus.post(new AllUserViewModel.ResponseBlockServer(throwable));
                });
    }*/
}
