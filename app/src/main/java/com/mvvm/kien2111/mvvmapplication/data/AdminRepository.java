package com.mvvm.kien2111.mvvmapplication.data;

import com.mvvm.kien2111.mvvmapplication.data.remote.AdminService;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by donki on 3/21/2018.
 */

public class AdminRepository {
    private final AdminService adminService;
    @Inject
    public AdminRepository(AdminService adminService){
        this.adminService = adminService;
    }
    public Single<Resource<List<User>>> getAllUser(){
        return adminService.getAllUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(users -> Resource.success(users));
    }
}
