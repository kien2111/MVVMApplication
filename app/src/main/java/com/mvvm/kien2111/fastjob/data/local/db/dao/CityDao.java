package com.mvvm.kien2111.fastjob.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.kien2111.fastjob.data.local.db.entity.City;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by WhoAmI on 19/04/2018.
 */
@Dao
public abstract class CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCiites(City... cities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCities(List<City> cityList);

    @Query("SELECT * from cities")
    public  abstract Flowable<List<City>> getAllCity();
}
