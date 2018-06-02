package com.mvvm.kien2111.fastjob.ui.universal.feed.map;

import com.mvvm.kien2111.fastjob.model.Priority;
import com.mvvm.kien2111.fastjob.ui.universal.FilterMapController;

/**
 * Created by WhoAmI on 29/05/2018.
 */

public class FilterMapModel {
    private double mLat;
    private double mLong;
    private Priority mPriority;
    private double mDistance;
    private FilterMapController.Unit unit;

    public FilterMapController.Unit getUnit() {
        return unit;
    }

    public void setUnit(FilterMapController.Unit unit) {
        this.unit = unit;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double mLat) {
        this.mLat = mLat;
    }

    public double getLong() {
        return mLong;
    }

    public void setLong(double mLong) {
        this.mLong = mLong;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority mPriority) {
        this.mPriority = mPriority;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double mDistance) {
        this.mDistance = mDistance;
    }


    private FilterMapModel(){}

    public FilterMapModel(Builder builder){
        this.mLat = builder.mLat;
        this.mLong = builder.mLong;
        this.mPriority = builder.mPriority;
        this.mDistance = builder.mDistance;
        this.unit = builder.unit;
    }

    public static class Builder {
        double mLat;
        double mLong;
        Priority mPriority;
        double mDistance;
        FilterMapController.Unit unit;

        public FilterMapModel build(){
            return new FilterMapModel(this);
        }

        public Builder setLat(double mLat) {
            this.mLat = mLat;
            return this;
        }

        public Builder unit(FilterMapController.Unit unit) {
            this.unit = unit;
            return this;
        }

        public Builder setLong(double mLong) {
            this.mLong = mLong;return this;
        }

        public Builder priority(Priority mPriority) {
            this.mPriority = mPriority;return this;
        }

        public Builder distance(double mDistance) {
            this.mDistance = mDistance;return this;
        }
    }
}
