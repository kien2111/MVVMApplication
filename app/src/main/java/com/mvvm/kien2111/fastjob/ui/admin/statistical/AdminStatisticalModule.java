package com.mvvm.kien2111.fastjob.ui.admin.statistical;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dagger.Module;
import dagger.Provides;

/**
 * Created by donki on 3/9/2018.
 */
@Module
public class AdminStatisticalModule {

    @Provides
    Calendar provideCaledar(){
        return Calendar.getInstance();
    }

    @Provides
    SimpleDateFormat provideSimpleDateForMat(){
        return new SimpleDateFormat("dd/MM/yyyy");
    }

}
