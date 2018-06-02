package com.mvvm.kien2111.fastjob.data;

import com.mvvm.kien2111.fastjob.data.remote.AdminService;
import com.mvvm.kien2111.fastjob.data.remote.model.admin.AdminCreatedUserResponse;
import com.mvvm.kien2111.fastjob.data.remote.model.admin.UserFilterRequest;
import com.mvvm.kien2111.fastjob.model.BlockUser;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.model.User;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by donki on 3/21/2018.
 */

public class AdminRepository {
    private final AdminService adminService;

    @Inject
    public AdminRepository(AdminService adminService) {
        this.adminService = adminService;
    }

    public Single<Resource<List<User>>> getAllUser() {
        return adminService.getAllUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(users -> Resource.success(users));
    }
    public Single<Resource<List<User>>> getUserBlock(UserFilterRequest status) {
        return adminService.getUserBlock(status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(users -> Resource.success(users));
    }
    public Single<AdminCreatedUserResponse> updateUser(User user) {
        return adminService.updateUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable insertUser(User user) {
        RequestBody username = RequestBody.create(MediaType.parse("multipart/form-data"), user.getUserName());
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), user.getEmail());
        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), user.getPassword());
        RequestBody age = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user.getBirthday()));
        RequestBody role = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user.getRole_list().get(0).getIdrole()));
        File file = new File(user.getAvatar());
        RequestBody requestBodyAvaatar = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar",file.getName(),requestBodyAvaatar);

        return adminService.insertUser(username, email, password,age,role,avatar)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    public Completable blockUser(List<BlockUser> listblockUser){
        return adminService.blockuser(listblockUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
