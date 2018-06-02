package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.edituser;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AdminRepository;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.model.Resource;
import android.arch.lifecycle.MutableLiveData;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 4/25/2018.
 */

public class UserEditProfileViewModel extends BaseViewModel {
    private AdminRepository adminRepository;
    private MutableLiveData<Resource<List<User>>> resourceMutableLiveData = new MutableLiveData<>();


    @Inject
    public UserEditProfileViewModel(EventBus eventBus,AdminRepository adminRepository) {
        super(eventBus);
        this.adminRepository=adminRepository;
    }

    //update profie user
    void callUpdteUser(User userUpdate){
        adminRepository.editProfileUser(userUpdate)
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
