package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.converter.AppConverter;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by WhoAmI on 07/04/2018.
 */
@Entity(tableName = "appointments")
@TypeConverters(AppConverter.class)
public class AppointmentModel {
    @NonNull
    @Expose
    @SerializedName("idappointment")
    @PrimaryKey
    private Integer idappointment;

    @Expose
    @SerializedName("avatar")
    private String avatar;

    @Expose
    @SerializedName("status")
    private StatusAppointment status;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;

    @SerializedName("user_who_create_appointment")
    @Expose
    private String iduser_who_create_appointment;

    @SerializedName("user_who_receive_appointment")
    @Expose
    private String iduser_who_receive_appointment;

    @SerializedName("user_create_appointment")
    @Expose
    private User user_who_create_appointment;

    @SerializedName("user_receive_appointment")
    @Expose
    private User user_who_receive_appointment;

    @SerializedName("created_at")
    @Expose
    private Timestamp created_date;

    @SerializedName("updated_at")
    @Expose
    private Timestamp updated_at;

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getIduser_who_create_appointment() {
        return iduser_who_create_appointment;
    }

    public void setIduser_who_create_appointment(String iduser_who_create_appointment) {
        this.iduser_who_create_appointment = iduser_who_create_appointment;
    }

    public String getIduser_who_receive_appointment() {
        return iduser_who_receive_appointment;
    }

    public void setIduser_who_receive_appointment(String iduser_who_receive_appointment) {
        this.iduser_who_receive_appointment = iduser_who_receive_appointment;
    }
    @NonNull
    public Integer getIdappointment() {
        return idappointment;
    }

    public void setIdappointment(Integer idappointment) {
        this.idappointment = idappointment;
    }

    public User getUser_who_create_appointment() {
        return user_who_create_appointment;
    }

    public void setUser_who_create_appointment(User user_who_create_appointment) {
        this.user_who_create_appointment = user_who_create_appointment;
    }

    public User getUser_who_receive_appointment() {
        return user_who_receive_appointment;
    }

    public void setUser_who_receive_appointment(User user_who_receive_appointment) {
        this.user_who_receive_appointment = user_who_receive_appointment;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
