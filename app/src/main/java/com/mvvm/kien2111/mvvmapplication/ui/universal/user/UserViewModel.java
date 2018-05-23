package com.mvvm.kien2111.mvvmapplication.ui.universal.user;

import android.accounts.Account;
import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalViewModel;

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
