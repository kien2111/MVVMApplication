package com.mvvm.kien2111.mvvmapplication.ui.admin.statistical;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by donki on 3/9/2018.
 */

public class AdminStatisticalViewModel extends BaseViewModel {
    @Inject
    public  AdminStatisticalViewModel(EventBus eventBus){
        super(eventBus);
    }
    public  void finishActivity()
    {
        //getNavigator().finishActivity();
    }
}
