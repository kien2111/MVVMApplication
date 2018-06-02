package com.mvvm.kien2111.mvvmapplication.model;

import java.util.Date;

/**
 * Created by donki on 5/28/2018.
 */

public class AdminAppointment {
    private int idappointment;
    private String employer_id;
    private String employee_id;
    private int completed;
    private Date estimate_time;
    private String duration;
    private  String name;
    private  String note;
    private Date created_at;
    private Date updated_at;
    private int user_who_create_appointment;
    private int user_who_receive_appointment;
    private int status;
    private User user_create_appointment;
    private User user_receive_appointment;


    public AdminAppointment(int idappointment, String employer_id, String employee_id,
                            int completed, Date estimate_time, String duration,
                            String name, String note, Date created_at, Date updated_at,
                            int user_who_create_appointment, int user_who_receive_appointment,
                            int status,User user_create_appointment, User user_receive_appointment) {

        this.idappointment = idappointment;
        this.employer_id = employer_id;
        this.employee_id = employee_id;
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
        this.user_create_appointment = user_create_appointment;
        this.user_receive_appointment = user_receive_appointment;
    }

    public int getIdappointment() {
        return idappointment;
    }

    public void setIdappointment(int idappointment) {
        this.idappointment = idappointment;
    }

    public String getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(String employer_id) {
        this.employer_id = employer_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public Date getEstimate_time() {
        return estimate_time;
    }

    public void setEstimate_time(Date estimate_time) {
        this.estimate_time = estimate_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_who_create_appointment() {
        return user_who_create_appointment;
    }

    public void setUser_who_create_appointment(int user_who_create_appointment) {
        this.user_who_create_appointment = user_who_create_appointment;
    }

    public int getUser_who_receive_appointment() {
        return user_who_receive_appointment;
    }

    public void setUser_who_receive_appointment(int user_who_receive_appointment) {
        this.user_who_receive_appointment = user_who_receive_appointment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser_create_appointment() {
        return user_create_appointment;
    }

    public void setUser_create_appointment(User user_create_appointment) {
        this.user_create_appointment = user_create_appointment;
    }

    public User getUser_receive_appointment() {
        return user_receive_appointment;
    }

    public void setUser_receive_appointment(User user_receive_appointment) {
        this.user_receive_appointment = user_receive_appointment;
    }
}
