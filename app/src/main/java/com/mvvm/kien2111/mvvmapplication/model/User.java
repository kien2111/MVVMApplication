package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by WhoAmI on 21/01/2018.
 */

public class User {
    private int id;
    private int employer_id;
    private int employee_id;
    private String username;
    private String password;
    private String avatar;
    private String updated_at;
    private String created_at;

    public User(int id, int employer_id, int employee_id, String username, String password, String avatar, String updated_at, String created_at) {
        this.id = id;
        this.employer_id = employer_id;
        this.employee_id = employee_id;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.updated_at = updated_at;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(int employer_id) {
        this.employer_id = employer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
