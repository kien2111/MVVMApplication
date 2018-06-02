package com.mvvm.kien2111.fastjob.data.remote.model.admin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by donki on 4/13/2018.
 */

public class AdminCreateUserRequest {
    private RequestBody username;
    private RequestBody email;
    private RequestBody password;
    private RequestBody age;
    private RequestBody role;
    private RequestBody birthday;
    private MultipartBody.Part avatar;

    public AdminCreateUserRequest(){

    }

    public AdminCreateUserRequest(RequestBody username, RequestBody email, RequestBody password, MultipartBody.Part avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;

    }

    public RequestBody getUsername() {
        return username;
    }

    public void setUsername(RequestBody username) {
        this.username = username;
    }

    public RequestBody getEmail() {
        return email;
    }

    public void setEmail(RequestBody email) {
        this.email = email;
    }

    public RequestBody getPassword() {
        return password;
    }

    public void setPassword(RequestBody password) {
        this.password = password;
    }

    public RequestBody getAge() {
        return age;
    }

    public void setAge(RequestBody age) {
        this.age = age;
    }

    public RequestBody getRole() {
        return role;
    }

    public void setRole(RequestBody role) {
        this.role = role;
    }

    public MultipartBody.Part getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartBody.Part avatar) {
        this.avatar = avatar;
    }
}
