package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;

import com.huma.room_for_asset.RoomAsset;
import com.mvvm.kien2111.mvvmapplication.MyApplication;
import com.mvvm.kien2111.mvvmapplication.data.local.db.RoomDb;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.AppointmentDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.CategoryDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.CityDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.DistrictDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.ProfileDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.RateDao;
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
        //return Room.databaseBuilder(myApplication,RoomDb.class,"roomdb").build();
        return RoomAsset.databaseBuilder(myApplication,RoomDb.class,"mydb.db")
                .build();
    }

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'appointments' ADD COLUMN 'idappointment' INTEGER");
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };*/

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

    @Provides
    @Singleton
    RateDao provideRateDao(RoomDb roomDb){return roomDb.rateDao();}

    @Provides
    @Singleton
    AppointmentDao provideAppointmentDao(RoomDb roomDb){return roomDb.appointmentDao();}

    @Provides
    @Singleton
    CityDao provideCityDao(RoomDb roomDb){return roomDb.cityDao();}

    @Provides
    @Singleton
    DistrictDao provideDistrictDao(RoomDb roomDb){return roomDb.districtDao();}
}
