package com.mvvm.kien2111.mvvmapplication.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
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
    public abstract void insert(ProfileModel... profileModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<ProfileModel> profileModelList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ProfileNextPageResult result);

    @Query("SELECT * FROM ProfileNextPageResult WHERE `query` = :query")
    public abstract Flowable<ProfileNextPageResult> findNextPageResultFlowable(String query);

    public Flowable<List<ProfileModel>> loadOrdered(List<String> idProfilelist){
        return loadById(idProfilelist).map(new Function<List<ProfileModel>, List<ProfileModel>>() {
            @Override
            public List<ProfileModel> apply(List<ProfileModel> profileModelList) throws Exception {
                Collections.sort(profileModelList,(o1, o2) -> {
                    return o1.getPriority().compareTo(o2.getPriority());
                });
                return profileModelList;
            }
        });
    }
    @Query("SELECT * FROM ProfileModel WHERE idprofile in (:idprofiles)")
    protected abstract Flowable<List<ProfileModel>> loadById(List<String> idprofiles);
}
