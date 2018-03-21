package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailCategoryViewModel extends BaseViewModel {
    @Inject
    public DetailCategoryViewModel(EventBus eventBus) {
        super(eventBus);
    }
}
