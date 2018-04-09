package com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class OnProgressAppointmentViewModel extends BaseViewModel {
    @Inject
    public OnProgressAppointmentViewModel(EventBus eventBus){
        super(eventBus);
    }
}
