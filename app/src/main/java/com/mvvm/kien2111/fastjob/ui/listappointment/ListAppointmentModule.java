package com.mvvm.kien2111.fastjob.ui.listappointment;

import com.mvvm.kien2111.fastjob.ui.universal.common.ViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 07/04/2018.
 */
@Module
public class ListAppointmentModule {
    @Provides
    ViewPagerAdapter provideViewPagerAdapter(ListAppointmentActivity activity){
        return new ViewPagerAdapter(activity.getSupportFragmentManager());
    }
}
