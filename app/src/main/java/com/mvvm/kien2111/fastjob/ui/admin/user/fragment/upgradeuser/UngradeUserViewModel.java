package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.upgradeuser;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AdminRepository;
import com.mvvm.kien2111.fastjob.data.remote.model.admin.UserFilterRequest;
import com.mvvm.kien2111.fastjob.model.BlockUser;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.model.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 3/7/2018.
 */

public class UngradeUserViewModel extends BaseViewModel {
    private MutableLiveData<Resource<List<User>>> resourceMutableLiveData = new MutableLiveData<>();
    private final AdminRepository adminRepository;

    @Inject
    public UngradeUserViewModel(EventBus eventBus,AdminRepository adminRepository){
        super(eventBus);
        this.adminRepository = adminRepository;
        UserFilterRequest status = new UserFilterRequest(0);
        getUserBlock(status);
    }

    private void getUserBlock(UserFilterRequest status){
        resourceMutableLiveData.setValue(Resource.loading(null));
        compositeDisposable.add(adminRepository
                .getUserBlock(status)
                .subscribe(listResource -> {
                    resourceMutableLiveData.setValue(listResource);
                }, error -> {
                    resourceMutableLiveData.setValue(Resource.error(error.getMessage(), null));
                })
        );
    }

    //callunLockUse
    public void unlockUser(List<BlockUser> listblock) {
        resourceMutableLiveData.setValue(Resource.loading(null));
        compositeDisposable.add(adminRepository
                .blockUser(listblock)
                .subscribe(() -> {
                    eventBus.post(new ResponseUnBlockServer("Unlock success"));
                }, error -> {
                    eventBus.post(new ResponseUnBlockServer(error));
                })
        );
    }


    public MutableLiveData<Resource<List<User>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }
    public static class ResponseUnBlockServer extends BaseMessage {
        public ResponseUnBlockServer(Throwable errorMess) {
            super(errorMess);
        }
        public String message;
        public ResponseUnBlockServer(String message){
            this.message = message;
        }
    }
}
