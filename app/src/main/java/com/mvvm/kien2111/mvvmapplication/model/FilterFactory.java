package com.mvvm.kien2111.mvvmapplication.model;

import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.City;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;

/**
 * Created by WhoAmI on 19/04/2018.
 */

public final class FilterFactory {

    private static final String TAG = FilterFactory.class.getSimpleName();

    public static FilterOption<ProfileModel> newFilterCity(City city){
        return new FilterCity(city);
    }
    public static FilterOption<ProfileModel> newFilterDistrict(District district){
        return new FilterDistrict(district);
    }
}
