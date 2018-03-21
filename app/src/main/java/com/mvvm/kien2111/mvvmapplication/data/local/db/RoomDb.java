package com.mvvm.kien2111.mvvmapplication.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.CategoryDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.ProfileDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.UserDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileNextPageResult;

/**
 * Created by WhoAmI on 06/03/2018.
 */
@Database(entities = {Category.class, ProfileNextPageResult.class},version = 1,exportSchema = false)
public abstract class RoomDb extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract ProfileDao profileDao();
}
