package com.mvvm.kien2111.fastjob.ui.universal.feed;

import android.accounts.Account;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.CategoryRepository;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceLiveData;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class FeedViewModel extends BaseViewModel {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PreferenceLiveData preferenceLiveData;
    @Inject
    public FeedViewModel(EventBus eventBus,
                         UserRepository userRepository,
                         PreferenceLiveData preferenceLiveData,
                         CategoryRepository categoryRepository){
        super(eventBus);
        this.userRepository = userRepository;
        this.preferenceLiveData = preferenceLiveData;
        this.categoryRepository = categoryRepository;
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }

    public Account getCurrentAccout(){
        return userRepository.getCurrentAccount();
    }
}
