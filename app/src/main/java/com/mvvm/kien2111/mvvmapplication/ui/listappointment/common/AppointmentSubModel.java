package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class AppointmentSubModel implements ExpandableChildAppointmentItem {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
