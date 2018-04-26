package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;

import java.util.List;

/**
 * Created by WhoAmI on 18/04/2018.
 */
@Entity(primaryKeys = {"iduser","option"},tableName = "appointmentnextpageresult")
@TypeConverters(AppConverter.class)
public class AppointmentNextPageResult {
    @NonNull
    public final int option;
    @NonNull
    public final String iduser;
    public final List<Integer> idappointments;
    public final int totalcount;
    @Nullable
    public final Integer next;

    public AppointmentNextPageResult(@NonNull int option,@NonNull String iduser,List<Integer> idappointments,int totalcount,@Nullable Integer next){
        this.option = option;
        this.iduser = iduser;
        this.idappointments = idappointments;
        this.next = next;
        this.totalcount = totalcount;
    }
}
