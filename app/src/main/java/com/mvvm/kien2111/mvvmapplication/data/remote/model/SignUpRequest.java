package com.mvvm.kien2111.mvvmapplication.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 26/02/2018.
 */

public class SignUpRequest {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public static class Builder{
        private String username,password;
        public Builder setPassword(String password){
            this.password = password;
            return this;
        }
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        public SignUpRequest build(){
            return new SignUpRequest(this);
        }

    }
    private SignUpRequest(Builder builder){
        this.username = builder.username;
        this.password = builder.password;
    }

    public  static  class AdminInsertUser
    {
        private  String username,email,password,age,photo;
        private  int role;

        public AdminInsertUser (String username, String email, String password, String age, String photo, int role) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.age = age;
            this.photo = photo;
            this.role = role;
        }
    }
}
