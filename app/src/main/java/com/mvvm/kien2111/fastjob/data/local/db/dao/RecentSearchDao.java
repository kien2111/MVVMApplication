package com.mvvm.kien2111.fastjob.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.kien2111.fastjob.data.local.db.entity.RecentSearch;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by WhoAmI on 05/05/2018.
 */
@Dao
public abstract class RecentSearchDao {
    @Query("SELECT * FROM recentsearch WHERE `created_at` BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime') LIMIT 5")
    public abstract Flowable<List<RecentSearch>> fetch1RecentSearch();

    @Query("SELECT * FROM recentsearch LIMIT 5")
    public abstract Flowable<List<RecentSearch>> fetchRecentSearch();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RecentSearch recentSearch);

}
