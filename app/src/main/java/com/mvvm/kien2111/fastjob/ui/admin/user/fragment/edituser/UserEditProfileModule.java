package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.edituser;

import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.addnewuser.DatasetSpinnerRole;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dagger.Module;
import dagger.Provides;

/**
 * Created by donki on 4/25/2018.
 */

@Module
public class UserEditProfileModule {

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
