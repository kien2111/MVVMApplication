package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentWrapper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by WhoAmI on 17/04/2018.
 */

public interface AppointmentService {
    @GET("Appointments/{option}/getlistappointment")
    Single<AppointmentWrapper> getListAppointment(@Path("option") int pickOption, @Query("iduser")String iduser);

    @GET("Appointments/{option}/getlistappointment")
    Flowable<AppointmentWrapper> getNextPageAppointment(@Path("option") int pickOption, @Query("iduser")String iduser, @Query("page")int page);


}
