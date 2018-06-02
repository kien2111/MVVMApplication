package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.upgradeuser;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

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

import timber.log.Timber;

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
                    Log.d("DATA","fasdfsadfsd"+error);
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
                    Timber.d("OOKKKKKKKKKKKK");
                    eventBus.post(new ResponseBlockServer("Unlock success"));
                }, error -> {
                    Timber.tag("THU").d("error: "+error);
                    eventBus.post(new ResponseBlockServer(error));
                })
        );
    }


    public MutableLiveData<Resource<List<User>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }
    public static class ResponseBlockServer extends BaseMessage {
        public ResponseBlockServer(Throwable errorMess) {
            super(errorMess);
        }
        public String message;
        public ResponseBlockServer(String message){
            this.message = message;
        }
    }
}
