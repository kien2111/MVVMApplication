package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.mvvm.kien2111.mvvmapplication.MyApplication;
import com.mvvm.kien2111.mvvmapplication.data.local.db.RoomDb;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.CategoryDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.ProfileDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.UserDao;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 21/01/2018.
 */
@Module(includes = ViewModelModule.class)
public class AppModule {
    @Provides
    @Singleton
    RoomDb provideDb(MyApplication myApplication){
        return Room.databaseBuilder(myApplication,RoomDb.class,"roomdb").build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(RoomDb roomDb){
        return roomDb.userDao();
    }

    @Provides
    @Singleton
    CategoryDao provideCategoryDao(RoomDb roomDb){
        return roomDb.categoryDao();
    }

    @Provides
    @Singleton
    ProfileDao provideProfileDao(RoomDb roomDb){return roomDb.profileDao();}
}
