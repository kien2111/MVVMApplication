package com.mvvm.kien2111.fastjob.model;

import com.mvvm.kien2111.fastjob.data.local.db.entity.District;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by WhoAmI on 19/04/2018.
 */

public class FilterDistrict implements FilterOption<ProfileModel> {
    private final District district;
    public FilterDistrict(District district){
        this.district = district;
    }

    @Override
    public List<ProfileModel> getFilter(List<ProfileModel> listfullitemneedfiltered) {
        try{
            List<ProfileModel> collection = Collections.emptyList();
            for(ProfileModel profile : listfullitemneedfiltered){
                if(profile.getDistrict().equals(this.district)){
                    collection.add(profile);
                }
            }
            return collection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return listfullitemneedfiltered;
    }
}
