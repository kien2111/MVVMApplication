package com.mvvm.kien2111.fastjob.ui.admin.statistical;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;

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
