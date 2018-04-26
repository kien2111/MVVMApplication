package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;

import java.util.List;

/**
 * Created by WhoAmI on 06/04/2018.
 */
@Entity(primaryKeys = {"query"},tableName = "ratenextpageresult")
@TypeConverters(AppConverter.class)
public class RateNextPageResult {
    @NonNull
    public final String query;
    public final List<Integer> idrates;
    public final int totalcount;
    @Nullable
    public final Integer next;

    public RateNextPageResult(@NonNull String query,List<Integer> idrates,int totalcount,@Nullable Integer next){
        this.query = query;
        this.idrates = idrates;
        this.next = next;
        this.totalcount = totalcount;
    }
}
