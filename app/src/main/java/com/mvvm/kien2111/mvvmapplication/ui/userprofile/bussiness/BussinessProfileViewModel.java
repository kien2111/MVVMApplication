package com.mvvm.kien2111.mvvmapplication.ui.userprofile.bussiness;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 26/04/2018.
 */

public class BussinessProfileViewModel extends BaseViewModel {
    private final PreferenceLiveData preferenceLiveData;
    private final UserRepository userRepository;
    @Inject
    public BussinessProfileViewModel(EventBus eventBus,
                                     UserRepository userRepository,
                                     PreferenceLiveData preferenceLiveData) {
        super(eventBus);
        this.preferenceLiveData = preferenceLiveData;
        this.userRepository = userRepository;
        this.preferenceLiveData.setUser(userRepository.getUserData().getUser());
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }
}
