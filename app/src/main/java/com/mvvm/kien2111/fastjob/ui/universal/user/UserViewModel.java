package com.mvvm.kien2111.fastjob.ui.universal.user;

import android.accounts.Account;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceLiveData;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public class UserViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    private final PreferenceLiveData preferenceLiveData;
    @Inject
    public UserViewModel(EventBus eventBus,
                         PreferenceLiveData preferenceLiveData,
                         UserRepository userRepository){
        super(eventBus);
        this.userRepository = userRepository;
        this.preferenceLiveData = preferenceLiveData;
        compositeDisposable.add(this.userRepository.syncLocalVersusPromoteData()
                .subscribe(this.preferenceLiveData::syncData, throwable -> {
            //TODO
        }));
    }

    public Account getCurrentAccount(){
        return userRepository.getCurrentAccount();
    }

    public void cleaUserData(){
        userRepository.clearUserData();
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }
}
