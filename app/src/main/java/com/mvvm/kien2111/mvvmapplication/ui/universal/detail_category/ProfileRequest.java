package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import com.mvvm.kien2111.mvvmapplication.model.Priority;

/**
 * Created by WhoAmI on 24/04/2018.
 */

public class ProfileRequest {
    public String cityid;
    public String distid;
    public Priority priority = Priority.ALL;
    public Double salaryFrom=0.0;
    public Double salaryTo=0.0;
    public String query;

    public ProfileRequest(String query, String cityid,String distid,Priority priority,Double salaryFrom,Double salaryTo) {
        this.query = query;
        this.cityid = cityid;
        this.distid = distid;
        if(salaryFrom!=null && salaryFrom!=0.0)this.salaryFrom = salaryFrom;
        if(salaryTo!=null && salaryTo!=0.0)this.salaryTo = salaryTo;
        if(priority!=null && priority.getType()!=Priority.ALL.getType())this.priority = priority;


    }

    public ProfileRequest(Builder builder){
        this.query = builder.query;
        this.cityid = builder.cityid;
        this.distid = builder.distid;
        if(salaryFrom!=null && builder.salaryFrom!=null && builder.salaryFrom!=0.0)this.salaryFrom = builder.salaryFrom;
        if(salaryTo!=null && builder.salaryTo!=null && builder.salaryTo!=0.0)this.salaryTo = builder.salaryTo;
        if(priority!=null && builder.priority!=null && builder.priority.getType()!=Priority.ALL.getType())this.priority = builder.priority;
    }

    public static class Builder{
        public String cityid;
        public String distid;
        public Priority priority;
        public Double salaryFrom;
        public Double salaryTo;
        public String query;

        public ProfileRequest build(){
            return new ProfileRequest(this);
        }

        public Builder setCityid(String cityid) {
            this.cityid = cityid;
            return this;
        }

        public Builder setDistid(String distid) {
            this.distid = distid;return this;
        }

        public Builder setPriority(Priority priority) {
            this.priority = priority;return this;
        }

        public Builder setSalaryFrom(Double salaryFrom) {
            this.salaryFrom = salaryFrom;return this;
        }

        public Builder setSalaryTo(Double salaryTo) {
            this.salaryTo = salaryTo;return this;
        }

        public Builder setQuery(String query) {
            this.query = query;return this;
        }
    }
}
