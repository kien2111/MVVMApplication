package com.mvvm.kien2111.fastjob.model;

/**
 * Created by donki on 5/7/2018.
 */

public class ImpactApointment {
    int idappointment;
    int status;

    public ImpactApointment(int idappointment, int status) {
        this.idappointment = idappointment;
        this.status = status;
    }

    public int getIdappointment() {
        return idappointment;
    }

    public void setIdappointment(int idappointment) {
        this.idappointment = idappointment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
