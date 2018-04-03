package com.mvvm.kien2111.mvvmapplication.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by WhoAmI on 06/03/2018.
 */
@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getLocalCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Category> categoryList);
}
