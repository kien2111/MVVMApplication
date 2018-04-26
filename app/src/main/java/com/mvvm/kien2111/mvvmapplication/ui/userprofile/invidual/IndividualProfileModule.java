package com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual;

import com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.pickcategory.PickCategoryDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 20/04/2018.
 */
@Module
public class IndividualProfileModule {
    @Provides
    Calendar provideCallendar(){
        return Calendar.getInstance();
    }

    @Named("vn_format_date")
    @Provides
    String provideFormat_VN(){
        return "dd/MM/yyyy";
    }
    @Provides
    SimpleDateFormat provideSimpleDateFormat(@Named("vn_format_date") String vn_format){
        return new SimpleDateFormat(vn_format, Locale.getDefault());
    }
}
