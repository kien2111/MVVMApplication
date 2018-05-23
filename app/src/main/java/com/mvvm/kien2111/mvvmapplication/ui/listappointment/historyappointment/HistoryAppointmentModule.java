package com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment;

import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.model.Option;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentHistoryAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 07/04/2018.
 */
@Module
public class HistoryAppointmentModule {

    @Provides
    FragmentBindingComponent provideFragmentBindingComponent(HistoryAppointmentFragment historyAppointmentFragment){
        return new FragmentBindingComponent(historyAppointmentFragment);
    }

    @Named("business")
    @Provides
    AppointmentHistoryAdapter provideHistoryBussinessAppointmentAdapter(FragmentBindingComponent fragmentBindingComponent){
        return new AppointmentHistoryAdapter(fragmentBindingComponent, Option.EMPLOYER);
    }

    @Named("freelancer")
    @Provides
    AppointmentHistoryAdapter provideHistoryFreelancerAppointmentAdapter(FragmentBindingComponent fragmentBindingComponent){
        return new AppointmentHistoryAdapter(fragmentBindingComponent,Option.FREELANCER);
    }
}
