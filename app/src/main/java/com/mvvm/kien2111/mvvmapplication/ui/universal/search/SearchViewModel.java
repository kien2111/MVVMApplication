package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class SearchViewModel extends BaseViewModel {
    @Inject
    public SearchViewModel(EventBus eventBus){
        super(eventBus);
    }
}
