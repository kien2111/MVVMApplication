package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.BaseWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 20/03/2018.
 */

public class ProfileWrapper extends BaseWrapper{
    @SerializedName("query")
    @Expose
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    @SerializedName("profiles")
    @Expose
    private List<ProfileModel> listprofile;

    public List<ProfileModel> getListprofile() {
        return listprofile;
    }

    public void setListprofile(List<ProfileModel> listprofile) {
        this.listprofile = listprofile;
    }

    public List<String> getProfilesId(){
        List<String> stringList = new ArrayList<>();
        for(ProfileModel profileModel : listprofile){
            stringList.add(profileModel.getIdprofile());
        }
        return stringList;
    }


}
