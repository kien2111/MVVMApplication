package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class AppointmentModel implements ExpandableHeaderAppointmentItem {
    @Expose
    @SerializedName("avatar")
    private String avatar;

    @Expose
    @SerializedName("realname")
    private String realname;

    @Expose
    @SerializedName("status")
    private StatusAppointment status;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public StatusAppointment getStatus() {
        return status;
    }

    public void setStatus(StatusAppointment status) {
        this.status = status;
    }

    public enum StatusAppointment{
        ONPROGRESS(0),FAIL(1),SUCCESS(2),CONFLICT(3);
        private final Integer type;
        StatusAppointment(Integer type){
            this.type = type;
        }

        public Integer getType() {
            return type;
        }
        public static StatusAppointment mapType(Integer type){
            for(StatusAppointment statusAppointment : values()){
                if(type.equals(statusAppointment.getType())){
                    return statusAppointment;
                }
            }
            return ONPROGRESS;
        }
    }
}
