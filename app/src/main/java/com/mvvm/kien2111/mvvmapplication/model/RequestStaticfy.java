package com.mvvm.kien2111.mvvmapplication.model;

import java.util.Date;

/**
 * Created by donki on 5/24/2018.
 */

public class RequestStaticfy {

    private Date starttime;
    private Date endtime;
    private int option;

    public RequestStaticfy(Date starttime, Date endtime, int option) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.option = option;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestStaticfy that = (RequestStaticfy) o;

        if (option != that.option) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null)
            return false;
        return endtime != null ? endtime.equals(that.endtime) : that.endtime == null;
    }

    @Override
    public int hashCode() {
        int result = starttime != null ? starttime.hashCode() : 0;
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + option;
        return result;
    }
}
