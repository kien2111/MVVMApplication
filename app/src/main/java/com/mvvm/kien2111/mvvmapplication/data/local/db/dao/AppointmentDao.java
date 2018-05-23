package com.mvvm.kien2111.mvvmapplication.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.AppointmentNextPageResult;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by WhoAmI on 18/04/2018.
 */
@Dao
public abstract class AppointmentDao {
    @Query("SELECT * FROM appointmentnextpageresult WHERE `iduser` = :iduser AND `option`=:option AND `historyOrOnProgress`=:historyOrOnProgress")
    public abstract AppointmentNextPageResult
    findAppointmentNextPageResult(int option,String iduser,int historyOrOnProgress);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(AppointmentModel... appointmentModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<AppointmentModel> appointmentModelList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(AppointmentNextPageResult result);

    @Query("SELECT * FROM appointmentnextpageresult WHERE `option` = :option AND `iduser`=:iduser AND `historyOrOnProgress`=:historyOrOnProgress")
    public abstract Flowable<AppointmentNextPageResult>
    findNextPageAppointmentResultFlowable(int option,String iduser,int historyOrOnProgress);

    public Flowable<List<AppointmentModel>> loadOrdered(List<Integer> idappointments){
        return loadById(idappointments).map(appointmentModelList -> {
            Collections.sort(appointmentModelList,(o1, o2) -> {
                if(o1.getUpdated_at()!=null && o2.getUpdated_at()!=null){
                    return o1.getUpdated_at().compareTo(o2.getUpdated_at());
                }else if(o1.getCreated_date()!=null && o2.getCreated_date()!=null){
                    return o1.getCreated_date().compareTo(o2.getCreated_date());
                }else{
                    return o1.getIdappointment().compareTo(o2.getIdappointment());
                }
            });
            return appointmentModelList;
        });
    }
    @Query("SELECT * FROM appointments WHERE idappointment in (:idappointments)")
    protected abstract Flowable<List<AppointmentModel>> loadById(List<Integer> idappointments);
}
