package com.mvvm.kien2111.fastjob.ui.upgrade.businessupgrade;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class BusinessUpgradeViewModel extends BaseViewModel {
    @Inject
    public BusinessUpgradeViewModel(EventBus eventBus) {
        super(eventBus);
    }
}
