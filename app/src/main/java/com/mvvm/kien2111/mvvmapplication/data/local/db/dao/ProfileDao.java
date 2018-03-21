package com.mvvm.kien2111.mvvmapplication.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseIntArray;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.HighRateProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileNextPageResult;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by WhoAmI on 20/03/2018.
 */
@Dao
public abstract class ProfileDao {
    @Query("SELECT * FROM ProfileNextPageResult WHERE `query` = :query")
    public abstract ProfileNextPageResult findProfileNextPageResult(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(HighRateProfileModel... highRateProfileModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<HighRateProfileModel> highRateProfileModelList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ProfileNextPageResult result);

    @Query("SELECT * FROM ProfileNextPageResult WHERE `query` = :query")
    public abstract Flowable<ProfileNextPageResult> findNextPageResultFlowable(String query);

    public Flowable<List<HighRateProfileModel>> loadOrdered(List<String> idProfilelist){
        return loadById(idProfilelist).map(new Function<List<HighRateProfileModel>, List<HighRateProfileModel>>() {
            @Override
            public List<HighRateProfileModel> apply(List<HighRateProfileModel> highRateProfileModelList) throws Exception {
                Collections.sort(highRateProfileModelList,(o1, o2) -> {
                    return o1.getPriority().compareTo(o2.getPriority());
                });
                return highRateProfileModelList;
            }
        });
    }
    @Query("SELECT * FROM HighRateProfileModel WHERE idprofile in (:idprofiles)")
    protected abstract Flowable<List<HighRateProfileModel>> loadById(List<String> idprofiles);
}
