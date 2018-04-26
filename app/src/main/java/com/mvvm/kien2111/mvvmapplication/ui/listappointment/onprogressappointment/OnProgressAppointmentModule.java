package com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate.ListRateFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 07/04/2018.
 */
@Module
public class OnProgressAppointmentModule {
    @Provides
    FragmentBindingComponent provideFragmentComponent(OnProgressAppointmentFragment onProgressAppointmentFragment){
        return new FragmentBindingComponent(onProgressAppointmentFragment);
    }
    @Provides
    AppointmentAdapter provideAppointmentAdapter(FragmentBindingComponent bindingComponent){
        return new AppointmentAdapter(bindingComponent);
    }
    @Provides
    DividerItemDecoration provideDecoration(OnProgressAppointmentFragment onProgressAppointmentFragment){
        return new DividerItemDecoration(onProgressAppointmentFragment.getContext(), LinearLayoutManager.VERTICAL);
    }

}
