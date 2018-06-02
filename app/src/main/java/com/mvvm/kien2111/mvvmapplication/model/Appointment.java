package com.mvvm.kien2111.mvvmapplication.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by donki on 5/5/2018.
 */


public class Appointment extends BaseObservable implements Parcelable {



    /*public Appointment(int idappointment, int completed,
                       String duration, String name, String note, String created_at,
                       String updated_at, String user_who_create_appointment,
                       String user_who_receive_appointment, String status, List<User> listuser) {

        this.idappointment = idappointment;
        this.completed = completed;
        this.estimate_time = estimate_time;
        this.duration = duration;
        this.name = name;
        this.note = note;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user_who_create_appointment = user_who_create_appointment;
        this.user_who_receive_appointment = user_who_receive_appointment;
        this.status = status;
        this.listuser = listuser;
    }*/

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idappointment);
        dest.writeString(completed);
        dest.writeString(duration);
        dest.writeString(name);
        dest.writeString(note);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(user_who_create_appointment);
        dest.writeString(user_who_receive_appointment);
        dest.writeInt(status);
    }
    @Expose
    @SerializedName("idappointment")
    private int idappointment;

    @Expose
    @SerializedName("completed")
    private String completed;

    @Expose
    @SerializedName("employer_id")
    private String employer_id;

    @Expose
    @SerializedName("employee_id")
    private String employee_id;

    @Expose
    @SerializedName("estimate_time")
    private Date estimate_time;

    @Expose
    @SerializedName("duration")
    private String duration;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("note")
    private String note;

    @Expose
    @SerializedName("created_at")
    private String created_at;

    @Expose
    @SerializedName("updated_at")
    private String updated_at;

    @Expose
    @SerializedName("user_who_create_appointment")
    private String user_who_create_appointment;

    @Expose
    @SerializedName("user_who_receive_appointment")
    private String user_who_receive_appointment;

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("user_create_appointment")
    private User user_create_appointment;

    @Expose
    @SerializedName("user_take_appointment")
    private User user_take_appointment;


    protected Appointment(Parcel in) {
        idappointment = in.readInt();
        completed = in.readString();
        duration = in.readString();
        name = in.readString();
        note = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        user_who_create_appointment = in.readString();
        user_who_receive_appointment = in.readString();
        status = in.readInt();
    }

    @Bindable
    public int getIdappointment() {
        return idappointment;

    }

    public void setIdappointment(int idappointment) {
        this.idappointment = idappointment;
        notifyPropertyChanged(BR.idappointment);
    }

    @Bindable
    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
        notifyPropertyChanged(BR.completed);
    }

    @Bindable
    public Date getEstimate_time() {
        return estimate_time;
    }

    public void setEstimate_time(Date estimate_time) {
        this.estimate_time = estimate_time;
        notifyPropertyChanged(BR.estimate_time);
    }

    @Bindable
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
        notifyPropertyChanged(BR.duration);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }

    @Bindable
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
        notifyPropertyChanged(BR.created_at);
    }

    @Bindable
    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        notifyPropertyChanged(BR.updated_at);
    }

    @Bindable
    public String getUser_who_create_appointment() {
        return user_who_create_appointment;
    }

    public void setUser_who_create_appointment(String user_who_create_appointment) {
        this.user_who_create_appointment = user_who_create_appointment;
        notifyPropertyChanged(BR.user_who_create_appointment);
    }

    @Bindable
    public String getUser_who_receive_appointment() {
        return user_who_receive_appointment;
    }

    public void setUser_who_receive_appointment(String user_who_receive_appointment) {
        this.user_who_receive_appointment = user_who_receive_appointment;
        notifyPropertyChanged(BR.user_who_receive_appointment);
    }

    @Bindable
    public User getUser_create_appointment() {
        return user_create_appointment;
    }

    public void setUser_create_appointment(User user_create_appointment) {
        this.user_create_appointment = user_create_appointment;
    }

    @Bindable
    public User getUser_take_appointment() {
        return user_take_appointment;
    }

    public void setUser_take_appointment(User user_take_appointment) {
        this.user_take_appointment = user_take_appointment;
    }

    @Bindable
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }



    public  Appointment(){}
}
