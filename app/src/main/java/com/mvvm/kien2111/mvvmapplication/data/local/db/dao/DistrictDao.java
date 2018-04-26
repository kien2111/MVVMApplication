package com.mvvm.kien2111.mvvmapplication.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by WhoAmI on 19/04/2018.
 */
@Dao
public abstract class DistrictDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCiites(District... cities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCities(List<District> cityList);

    @Query("SELECT * from districts WHERE cityid=:cityid")
    public  abstract Flowable<List<District>> getAllDistrict(String cityid);
}
