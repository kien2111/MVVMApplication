package com.mvvm.kien2111.mvvmapplication.data.local.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;

import java.util.List;

/**
 * Created by WhoAmI on 20/03/2018.
 */
@Entity(primaryKeys = {"query"},tableName = "profilenextpageresult")
@TypeConverters(AppConverter.class)
public class ProfileNextPageResult {
    @NonNull
    public final String query;
    public final String filter;
    public final List<String> idprofiles;
    public final int totalcount;
    @Nullable
    public final Integer next;

    public ProfileNextPageResult(@NonNull String query,
                                 String filter,
                                 List<String> idprofiles,
                                 int totalcount,
                                 @Nullable Integer next){
        this.query = query;
        this.filter = filter;
        this.idprofiles = idprofiles;
        this.next = next;
        this.totalcount = totalcount;
    }
}
