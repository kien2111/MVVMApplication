package com.mvvm.kien2111.mvvmapplication.ui.userprofile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.mvvmapplication.model.Approve_Publish;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class UserProfileViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    private final Boolean[] imageChange = {false,false};
    private final PreferenceLiveData mPreferenceLiveData;
    public Boolean[] getImageChange(){
        return imageChange;
    }
    @Inject
    public UserProfileViewModel(EventBus eventBus,
                                PreferenceLiveData preferenceLiveData,
                                UserRepository userRepository) {
        super(eventBus);
        this.userRepository = userRepository;
        this.mPreferenceLiveData = preferenceLiveData;
    }
    public void updateProfileWithoutImage(final User user){
        compositeDisposable.add(userRepository
                .updateProfileWithoutImage(user)
                .subscribe(() -> {
                    eventBus.post(new UserActionMessage("Cập nhật tài khoản thành công"));
                },throwable -> {
                    //TODO HANDLE ERROR
                }));
    }

    public LiveData<User> getPreferenceLiveData() {
        return mPreferenceLiveData;
    }

    public void updateAvatarOnly(final User user){
        compositeDisposable.add(userRepository
                .updateAvatarOnly(user)
                .subscribe(() -> {
                    eventBus.post(new UserActionMessage("Cập nhật tài khoản thành công"));
                },throwable -> {
                    //TODO HANDLE ERROR
                    throwable.printStackTrace();
                }));
    }

    public void updateLogoOnly(final User user){
        compositeDisposable.add(userRepository
                .updateLogoOnly(user)
                .subscribe(() -> {
                    eventBus.post(new UserActionMessage("cập nhật tài khoản thành công"));
                },throwable -> {
                    //TODO HANDLE ERROR
                    throwable.printStackTrace();
                }));
    }

    public void updateBothImage(final User user){
        compositeDisposable.add(userRepository
                .updateBothImage(user)
                .subscribe(() -> {
                    eventBus.post(new UserActionMessage("cập nhật tài khoản thành công"));
                },throwable -> {
                    //TODO HANDLE ERROR
                    eventBus.post(BaseMessage.error(throwable));
                }));
    }



    public IMAGE_UPDATE_STRATEGY getStrategy() {
        if(imageChange[0] && imageChange[1]){
            return IMAGE_UPDATE_STRATEGY.UPDATE_BOTH_IMAGE;
        }else if(imageChange[0] || imageChange[1]){
            if(imageChange[0]){
                return IMAGE_UPDATE_STRATEGY.UPDATE_AVATAR_ONLY;
            }else{
                return IMAGE_UPDATE_STRATEGY.UPDATE_LOGO_ONLY;
            }
        }else{
            return IMAGE_UPDATE_STRATEGY.NONE_UPDATE;
        }
    }

    public void resetStategyAfterUpdate(){
        imageChange[0] = false;
        imageChange[1] = false;
    }

    public enum IMAGE_UPDATE_STRATEGY{
        UPDATE_BOTH_IMAGE,UPDATE_AVATAR_ONLY,UPDATE_LOGO_ONLY,NONE_UPDATE;
    }

    public static class UserActionMessage extends BaseMessage {
        public String message;
        public UserActionMessage(String message){
            this.message = message;
        }
    }
}
