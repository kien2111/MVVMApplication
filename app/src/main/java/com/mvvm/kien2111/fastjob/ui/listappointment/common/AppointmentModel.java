package com.mvvm.kien2111.fastjob.ui.listappointment.common;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.fastjob.data.local.db.converter.AppConverter;
import com.mvvm.kien2111.fastjob.model.Deposit_Fee;
import com.mvvm.kien2111.fastjob.model.User;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by WhoAmI on 07/04/2018.
 */
@Entity(tableName = "appointments")
@TypeConverters(AppConverter.class)
public class AppointmentModel {

    public AppointmentModel(){}

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

    @Expose
    @SerializedName("name")
    private String nameappointment;

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

    @SerializedName("duration")
    @Expose
    private String duration;

    @SerializedName("estimate_time")
    @Expose
    private Date estimmate_time;

    @SerializedName("note")
    @Expose
    private String note;

    @Expose
    @SerializedName("deposit_fee")
    private Deposit_Fee deposit_fee;

    @Expose
    @Ignore
    @SerializedName("iddeposit_fee")
    private Integer iddeposit_fee;

    @Ignore
    public Integer getIddeposit_fee() {
        return iddeposit_fee;
    }

    @Ignore
    public void setIddeposit_fee(Integer iddeposit_fee) {
        this.iddeposit_fee = iddeposit_fee;
    }

    public Deposit_Fee getDeposit_fee() {
        return deposit_fee;
    }

    public void setDeposit_fee(Deposit_Fee deposit_fee) {
        this.deposit_fee = deposit_fee;
    }

    public String getNameappointment() {
        return nameappointment;
    }

    public void setNameappointment(String nameappointment) {
        this.nameappointment = nameappointment;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getEstimmate_time() {
        return estimmate_time;
    }

    public void setEstimmate_time(Date estimmate_time) {
        this.estimmate_time = estimmate_time;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppointmentModel that = (AppointmentModel) o;

        return idappointment.equals(that.idappointment);
    }

    @Override
    public int hashCode() {
        return idappointment.hashCode();
    }

    @Override
    public String toString() {
        return "AppointmentModel{" +
                "idappointment=" + idappointment +
                ", avatar='" + avatar + '\'' +
                ", status=" + status +
                ", nameappointment='" + nameappointment + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", iduser_who_create_appointment='" + iduser_who_create_appointment + '\'' +
                ", iduser_who_receive_appointment='" + iduser_who_receive_appointment + '\'' +
                ", user_who_create_appointment=" + user_who_create_appointment +
                ", user_who_receive_appointment=" + user_who_receive_appointment +
                ", created_date=" + created_date +
                ", updated_at=" + updated_at +
                ", duration='" + duration + '\'' +
                ", estimmate_time=" + estimmate_time +
                ", note='" + note + '\'' +
                ", deposit_fee=" + deposit_fee +
                ", iddeposit_fee=" + iddeposit_fee +
                '}';
    }

    public enum StatusAppointment{
        ONPROGRESS(0,"Đang xử lý"),
        ON_WAIT_ADMINACCEPTED(1,"Đang chờ admin xác nhận"),
        FAIL(2,"Thất bại"),
        SUCCESS(3,"Thành công"),
        CONFLICT(4,"Xung đột");
        private final Integer type;
        StatusAppointment(Integer type,String name){
            this.type = type;this.name = name;
        }
        private final String name;
        public Integer getType() {
            return type;
        }

        public String getName() {
            return name;
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

    @Ignore
    private AppointmentModel(Builder builder){
        this.idappointment = builder.idappointment;
        this.avatar = builder.avatar;
        this.status = builder.status;
        this.email = builder.email;
        this.phonenumber = builder.phonenumber;
        this.iduser_who_create_appointment = builder.iduser_who_create_appointment;
        this.iduser_who_receive_appointment = builder.iduser_who_receive_appointment;
        this.created_date = builder.created_date;
        this.updated_at = builder.updated_at;
        this.estimmate_time = builder.estimmate_time;
        this.duration = builder.duration;
        this.user_who_create_appointment = builder.user_who_create_appointment;
        this.user_who_receive_appointment = builder.user_who_receive_appointment;
        this.nameappointment = builder.name;
        this.note = builder.note;
        this.deposit_fee = builder.deposit_fee;
        this.iddeposit_fee = builder.iddeposit_fee;
    }

    public static class Builder{

        private String note;

        private String name;

        private Integer idappointment;

        private String avatar;

        private StatusAppointment status;

        private String email;

        private String phonenumber;

        private String iduser_who_create_appointment;

        private String iduser_who_receive_appointment;

        private User user_who_create_appointment;

        private User user_who_receive_appointment;

        private Timestamp created_date;

        private Timestamp updated_at;

        private String duration;

        private Date estimmate_time;

        private Deposit_Fee deposit_fee;

        private Integer iddeposit_fee;

        public Builder setIddeposit_fee(Integer iddeposit_fee) {
            this.iddeposit_fee = iddeposit_fee;
            return this;
        }

        public Builder setNote(String note) {
            this.note = note;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDeposit_fee(Deposit_Fee deposit_fee) {
            this.deposit_fee = deposit_fee;return this;
        }

        public Builder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public Builder setEstimmate_time(Date estimmate_time) {
            this.estimmate_time = estimmate_time;
            return this;
        }

        public AppointmentModel build(){
            return new AppointmentModel(this);
        }

        public Builder setIdappointment(Integer idappointment) {
            this.idappointment = idappointment;return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;return this;
        }

        public Builder setStatus(StatusAppointment status) {
            this.status = status;return this;
        }

        public Builder setEmail(String email) {
            this.email = email;return this;
        }

        public Builder setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;return this;
        }

        public Builder setIduser_who_create_appointment(String iduser_who_create_appointment) {
            this.iduser_who_create_appointment = iduser_who_create_appointment;return this;
        }

        public Builder setIduser_who_receive_appointment(String iduser_who_receive_appointment) {
            this.iduser_who_receive_appointment = iduser_who_receive_appointment;return this;
        }

        public Builder setUser_who_create_appointment(User user_who_create_appointment) {
            this.user_who_create_appointment = user_who_create_appointment;return this;
        }

        public Builder setUser_who_receive_appointment(User user_who_receive_appointment) {
            this.user_who_receive_appointment = user_who_receive_appointment;return this;
        }

        public Builder setCreated_date(Timestamp created_date) {
            this.created_date = created_date;return this;
        }

        public Builder setUpdated_at(Timestamp updated_at) {
            this.updated_at = updated_at;return this;
        }
    }
}
