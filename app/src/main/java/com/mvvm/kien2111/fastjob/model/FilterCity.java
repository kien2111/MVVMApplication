package com.mvvm.kien2111.fastjob.model;

import com.mvvm.kien2111.fastjob.data.local.db.entity.City;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by WhoAmI on 19/04/2018.
 */

public class FilterCity implements FilterOption<ProfileModel> {
    private final City city;
    public FilterCity(City city){
        this.city = city;
    }
    @Override
    public List<ProfileModel> getFilter(List<ProfileModel> listfullitemneedfiltered) {
        try{
            List<ProfileModel> collection = Collections.emptyList();
            for(ProfileModel profile : listfullitemneedfiltered){
                if(profile.getCity().equals(this.city)){
                    collection.add(profile);
                }
            }
            return collection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return listfullitemneedfiltered;//return old list
    }
}
