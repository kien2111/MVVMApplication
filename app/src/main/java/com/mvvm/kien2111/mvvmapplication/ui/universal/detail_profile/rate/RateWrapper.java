package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate;

import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.BaseWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 06/04/2018.
 */

public class RateWrapper extends BaseWrapper {
    public List<RateModel> getLst_rate() {
        return lst_rate;
    }

    public void setLst_rate(List<RateModel> lst_rate) {
        this.lst_rate = lst_rate;
    }

    @SerializedName("rates")
    private List<RateModel> lst_rate;

    public List<Integer> getRatesId(){
        List<Integer> intlist = new ArrayList<>();
        for(RateModel rateItem : lst_rate){
            intlist.add(rateItem.getIdrate());
        }
        return intlist;
    }
}
