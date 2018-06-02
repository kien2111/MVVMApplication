package com.mvvm.kien2111.mvvmapplication.ui.admin.profile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dagger.Module;
import dagger.Provides;

/**
 * Created by donki on 3/11/2018.
 */

@Module
public class AdminManageProfileModule {
    @Provides
    Calendar provideCaledar(){
        return Calendar.getInstance();
    }

    @Provides
    SimpleDateFormat provideSimpleDateForMat(){
        return new SimpleDateFormat("dd/MM/yyyy");
    }
}
