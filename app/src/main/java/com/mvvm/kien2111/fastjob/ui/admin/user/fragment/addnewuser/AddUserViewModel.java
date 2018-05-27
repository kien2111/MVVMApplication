package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.addnewuser;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AdminRepository;
import com.mvvm.kien2111.fastjob.model.User;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 4/12/2018.
 */

public class AddUserViewModel extends BaseViewModel {
    AdminRepository adminRepository;

    @Inject
    public AddUserViewModel(EventBus eventBus, AdminRepository adminRepository) {
        super(eventBus);
        this.adminRepository = adminRepository;
    }

    public void callinsertUser() {
        eventBus.post(new TriggerInsertServer());
    }

    public void gotoinsertUser(User user) {
        adminRepository.insertUser(user)
                .subscribe(() -> {
                    eventBus.post(new ResponseInsertServer("Insert user success"));
                }, throwable -> {
                    eventBus.post(new ResponseInsertServer(throwable));
                });
    }

    public static class TriggerInsertServer extends BaseMessage {
    }
    public static class ResponseInsertServer extends BaseMessage {
        String message;
        public ResponseInsertServer(Throwable errorMess){
            super(errorMess);
        }
        public ResponseInsertServer(String message){
            this.message = message;
        }
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
