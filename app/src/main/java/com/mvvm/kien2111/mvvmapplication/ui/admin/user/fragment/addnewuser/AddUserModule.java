package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.addnewuser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dagger.Module;
import dagger.Provides;

/**
 * Created by donki on 4/12/2018.
 */

@Module
public class AddUserModule {
    @Provides
    DatasetSpinnerRole provideDatasetSpinner(){
        return new DatasetSpinnerRole();
    }

    @Provides
    Calendar provideCaledar(){
        return Calendar.getInstance();
    }

    @Provides
    SimpleDateFormat provideSimpleDateForMat(){
        return new SimpleDateFormat("dd/MM/yyyy");
    }
}
