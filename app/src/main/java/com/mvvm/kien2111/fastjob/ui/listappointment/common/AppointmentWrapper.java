package com.mvvm.kien2111.fastjob.ui.listappointment.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.fastjob.ui.universal.common.BaseWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 18/04/2018.
 */

public class AppointmentWrapper extends BaseWrapper {
    @SerializedName("option")
    @Expose
    private int option;
    @SerializedName("iduser")
    @Expose
    private String iduser;
    @SerializedName("appointments")
    @Expose
    private List<AppointmentModel> lst_appointment;
    @SerializedName("historyOrOnProgress")
    @Expose
    private int historyOrOnProgress;


    public int getHistoryOrOnProgress() {
        return historyOrOnProgress;
    }

    public void setHistoryOrOnProgress(int historyOrOnProgress) {
        this.historyOrOnProgress = historyOrOnProgress;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public List<AppointmentModel> getLst_appointment() {
        return lst_appointment;
    }

    public void setLst_appointment(List<AppointmentModel> lst_appointment) {
        this.lst_appointment = lst_appointment;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
    public List<AppointmentModel> getListAppointment() {
        return lst_appointment;
    }

    public void setLstAppointment(List<AppointmentModel> lst_rate) {
        this.lst_appointment = lst_rate;
    }



    public List<Integer> getIntergerAppointmentsFromApi(){
        List<Integer> intlist = new ArrayList<>();
        for(AppointmentModel appointmentModel : lst_appointment){
            intlist.add(appointmentModel.getIdappointment());
        }
        return intlist;
    }
}
