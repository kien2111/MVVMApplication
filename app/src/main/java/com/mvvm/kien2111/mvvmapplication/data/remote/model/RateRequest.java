package com.mvvm.kien2111.mvvmapplication.data.remote.model;

/**
 * Created by WhoAmI on 11/04/2018.
 */

public class RateRequest {

    private String content;
    private float average_point;
    private String user_who_receive_this_rate;
    private String user_who_rate_this;


    public RateRequest(float average_point, String user_who_receive_this_rate, String user_who_rate_this,String content) {
        this.average_point = average_point;
        this.user_who_receive_this_rate = user_who_receive_this_rate;
        this.user_who_rate_this = user_who_rate_this;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getAverage_point() {
        return average_point;
    }

    public void setAverage_point(float average_point) {
        this.average_point = average_point;
    }

    public String getUser_who_receive_this_rate() {
        return user_who_receive_this_rate;
    }

    public void setUser_who_receive_this_rate(String user_who_receive_this_rate) {
        this.user_who_receive_this_rate = user_who_receive_this_rate;
    }

    public String getUser_who_rate_this() {
        return user_who_rate_this;
    }

    public void setUser_who_rate_this(String user_who_rate_this) {
        this.user_who_rate_this = user_who_rate_this;
    }


}
