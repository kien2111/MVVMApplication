package com.mvvm.kien2111.mvvmapplication.ui.userprofile;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.model.Approve_Publish;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class UserProfileViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    @Inject
    public UserProfileViewModel(EventBus eventBus,UserRepository userRepository) {
        super(eventBus);
        this.userRepository = userRepository;
        userMutableLiveData.setValue(userRepository.getUserData().getUser());
    }
    public void updateProfileWithoutImage(final User user){
        compositeDisposable.add(userRepository
                .updateProfileWithoutImage(user)
                .subscribe(() -> {
                    eventBus.post(new UserActionMessage("Success update profile"));
                    userMutableLiveData.setValue(userRepository.getUserData().getUser());//sync UI data when success
                },throwable -> {
                    //TODO HANDLE ERROR
                }));
    }

    public User getStaticUserData(){
        return userRepository.getUserData().getUser();
    }

    public void updateProfile(final User user){
        compositeDisposable.add(userRepository
                .updateProfile(user)
                .subscribe(() -> {
                    eventBus.post(new UserActionMessage("Success update profile"));
                    userMutableLiveData.setValue(userRepository.getUserData().getUser());//sync UI data when success
                },throwable -> {
                    //TODO HANDLE ERROR
                    throwable.printStackTrace();
                }));
    }

    public void publishProfile(Approve_Publish approve_publish){
        compositeDisposable.add(userRepository
                .publishProfile(approve_publish)
                .subscribe(() -> {
                    eventBus.post(new UserActionMessage("Success publish profile"));
                },throwable -> {
                    //TODO HANDLE ERROR
                    throwable.printStackTrace();
                }));
    }
    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }
    public static class UserActionMessage extends BaseMessage {
        public String message;
        public UserActionMessage(String message){
            this.message = message;
        }
    }
}
