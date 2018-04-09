package com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class HistoryAppointmentViewModel extends BaseViewModel{
    @Inject
    public HistoryAppointmentViewModel(EventBus eventBus) {
        super(eventBus);
    }
}
