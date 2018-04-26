package com.mvvm.kien2111.mvvmapplication.data.remote.model;

/**
 * Created by WhoAmI on 11/04/2018.
 */

public class RateRequest {
    public RateRequest(float average_point, String userid) {
        this.average_point = average_point;
        this.userid = userid;
    }

    private float average_point;
    private String userid;

    public float getAverage_point() {
        return average_point;
    }

    public void setAverage_point(float average_point) {
        this.average_point = average_point;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
