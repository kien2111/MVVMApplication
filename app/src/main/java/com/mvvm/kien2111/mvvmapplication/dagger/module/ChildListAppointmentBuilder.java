package com.mvvm.kien2111.mvvmapplication.dagger.module;

import com.mvvm.kien2111.mvvmapplication.dagger.Scope.PerChildListAppointment;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment.HistoryAppointmentFragment;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment.HistoryAppointmentModule;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment.OnProgressAppointmentFragment;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment.OnProgressAppointmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by WhoAmI on 07/04/2018.
 */
@Module
public abstract class ChildListAppointmentBuilder {
    @PerChildListAppointment
    @ContributesAndroidInjector(modules = {OnProgressAppointmentModule.class})
    abstract OnProgressAppointmentFragment bindOnProgressAppointmentFragment();

    @PerChildListAppointment
    @ContributesAndroidInjector(modules = {HistoryAppointmentModule.class})
    abstract HistoryAppointmentFragment bindHistoryAppointmentFragment();
}
