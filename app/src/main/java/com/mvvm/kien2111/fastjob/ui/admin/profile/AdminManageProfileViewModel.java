package com.mvvm.kien2111.fastjob.ui.admin.profile;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AdminRepository;
import com.mvvm.kien2111.fastjob.model.User;
import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 3/11/2018.
 */

public class AdminManageProfileViewModel extends BaseViewModel {

    AdminRepository adminRepository;
    @Inject
    public  AdminManageProfileViewModel(EventBus eventBus, AdminRepository adminRepository){
        super(eventBus);
        this.adminRepository=adminRepository;
    }

    //update profie user
    void callUpdteProfile(User userUpdate){
        adminRepository.editProfileUser(userUpdate)
                .subscribe(() -> {
                    eventBus.post(new ResponseUpdateServer("Update success"));
                }, throwable -> {
                    eventBus.post(new ResponseUpdateServer(throwable));
                });
    }

    public static class TriggerUpdateServer extends BaseMessage {
    }
    public static class ResponseUpdateServer extends BaseMessage {
        String message;
        public ResponseUpdateServer(Throwable errorMess){
            super(errorMess);
        }
        public ResponseUpdateServer(String message){
            this.message = message;
        }
    }

}
