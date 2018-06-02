package com.mvvm.kien2111.fastjob.data.remote.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.fastjob.data.remote.AdminService;

/**
 * Created by donki on 4/19/2018.
 */

public class AdminBlockUserResponse {
    private final AdminService adminService;
    @SerializedName("message")
    @Expose
    private String message;


    public AdminBlockUserResponse(AdminService adminService, String message) {
        this.adminService = adminService;
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdminBlockUserResponse{" +
                "adminService=" + adminService +
                ", message='" + message + '\'' +
                '}';
    }
}
