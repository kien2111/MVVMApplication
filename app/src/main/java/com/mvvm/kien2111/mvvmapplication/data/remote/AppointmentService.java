package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.data.remote.model.AppointmentTaskRequest;
import com.mvvm.kien2111.mvvmapplication.model.Deposit_Fee;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentWrapper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by WhoAmI on 17/04/2018.
 */

public interface AppointmentService {
    @GET("Appointments/{option}/getlistappointment")
    Single<AppointmentWrapper> getListAppointment(@Path("option") int pickOption, @Query("iduser")String iduser,@Query("historyOrOnProgress") int historyOrOnProgress);

    @GET("Appointments/{option}/getlistappointment")
    Single<AppointmentWrapper> getNextPageAppointment(@Path("option") int pickOption, @Query("iduser")String iduser,@Query("historyOrOnProgress") int historyOrOnProgress, @Query("page")int page);

    @POST("Appointments/bookingappointment")
    Completable createAppointment(@Body AppointmentModel appointmentModel);

    @GET("Appointments/getdepositfee")
    Single<Deposit_Fee> getAvalableDepositFree();

    @PUT("Appointments/acceptappointment")
    Completable acceptAppointment(@Body AppointmentTaskRequest appointmentTaskRequest);

    @PUT("Appointments/declineappointment")
    Completable declineAppointment(@Body AppointmentTaskRequest appointmentTaskRequest);
}
