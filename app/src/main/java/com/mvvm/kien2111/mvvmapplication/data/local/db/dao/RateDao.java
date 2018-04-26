package com.mvvm.kien2111.mvvmapplication.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateModel;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateNextPageResult;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by WhoAmI on 06/04/2018.
 */
@Dao
public abstract class RateDao {
    @Query("SELECT * FROM ratenextpageresult WHERE `query` = :query")
    public abstract RateNextPageResult findRateNextPageResult(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RateModel... rateModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<RateModel> rateModelList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RateNextPageResult result);

    @Query("SELECT * FROM ratenextpageresult WHERE `query` = :query")
    public abstract Flowable<RateNextPageResult> findNextPageResultFlowable(String query);

    public Flowable<List<RateModel>> loadOrdered(List<Integer> idRatelist){
        return loadById(idRatelist).map(new Function<List<RateModel>, List<RateModel>>() {
            @Override
            public List<RateModel> apply(List<RateModel> rateModelList) throws Exception {
                Collections.sort(rateModelList,(o1, o2) -> {
                    return Float.compare(o1.getRatepoint(),o2.getRatepoint());
                });
                return rateModelList;
            }
        });
    }
    @Query("SELECT * FROM rates WHERE idrate in (:idrates)")
    protected abstract Flowable<List<RateModel>> loadById(List<Integer> idrates);
}
