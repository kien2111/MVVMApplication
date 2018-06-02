package com.mvvm.kien2111.fastjob.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mvvm.kien2111.fastjob.data.local.db.dao.AppointmentDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.CategoryDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.CityDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.DistrictDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.ProfileDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.RateDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.RecentSearchDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.UserDao;
import com.mvvm.kien2111.fastjob.data.local.db.entity.AppointmentNextPageResult;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileNextPageResult;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RateModel;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RateNextPageResult;
import com.mvvm.kien2111.fastjob.data.local.db.entity.City;
import com.mvvm.kien2111.fastjob.data.local.db.entity.District;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.fastjob.ui.listappointment.common.AppointmentModel;

/**
 * Created by WhoAmI on 06/03/2018.
 */
@Database(entities = {AppointmentModel.class,
        AppointmentNextPageResult.class,
        Category.class, RateModel.class,
        RateNextPageResult.class,
        ProfileNextPageResult.class,
        City.class,
        RecentSearch.class,
        District.class,
        ProfileModel.class
},version = 2,exportSchema = false)
public abstract class RoomDb extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract ProfileDao profileDao();
    public abstract RateDao rateDao();
    public abstract AppointmentDao appointmentDao();
    public abstract CityDao cityDao();
    public abstract DistrictDao districtDao();
    public abstract RecentSearchDao recentSearchDao();

}
