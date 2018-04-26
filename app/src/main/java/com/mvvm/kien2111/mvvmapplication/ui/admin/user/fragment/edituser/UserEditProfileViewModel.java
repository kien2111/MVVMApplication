package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.edituser;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.AdminRepository;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 4/25/2018.
 */

public class UserEditProfileViewModel extends BaseViewModel {
    AdminRepository adminRepository;
    @Inject
    public UserEditProfileViewModel(EventBus eventBus) {
        super(eventBus);
    }

    void callUpdteUser(User user){
        adminRepository.insertUser(user)
                .subscribe(() -> {
                    eventBus.post(new ResponseUpdateServer("Update success"));
                }, throwable -> {
                    eventBus.post(new ResponseUpdateServer(throwable));
                });
    }

    public static class ResponseUpdateServer extends BaseMessage {
        public String message;
        public ResponseUpdateServer(Throwable errorMess){
            super(errorMess);
        }
        public ResponseUpdateServer(String message){
            this.message = message;
        }
    }
}
