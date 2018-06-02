package com.mvvm.kien2111.fastjob.ui.createappointment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 27/04/2018.
 */
@Module
public class CreateAppointmentModule {
    @Provides
    Calendar provideCalendar(){
        return Calendar.getInstance();
    }
    @Provides
    @Named("date_format")
    SimpleDateFormat provideSimpleDateFormat(){
        return new SimpleDateFormat("dd/MM/yyyy");
    }
    @Provides
    @Named("time_format")
    SimpleDateFormat provideTimeFormat(){
        return new SimpleDateFormat("HH:mm a");
    }

    @Provides
    DecimalFormat provideDecimalFormat(){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        return decimalFormat;
    }
}
