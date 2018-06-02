package com.mvvm.kien2111.fastjob.data.remote.model;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class AppointmentTaskRequest {
    private Integer idappointment;

    public AppointmentTaskRequest(Integer idappointment) {
        this.idappointment = idappointment;
    }

    public Integer getIdappointment() {
        return idappointment;
    }

    public void setIdappointment(Integer idappointment) {
        this.idappointment = idappointment;
    }
}
