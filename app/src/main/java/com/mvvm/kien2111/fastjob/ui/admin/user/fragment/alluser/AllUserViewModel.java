package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser;

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

import timber.log.Timber;


/**
 * Created by donki on 3/7/2018.
 */

public class AllUserViewModel extends BaseViewModel {
    private MutableLiveData<Resource<List<User>>> resourceMutableLiveData = new MutableLiveData<>();
    private final AdminRepository adminRepository;

    @Inject
    public AllUserViewModel(EventBus eventBus, AdminRepository adminRepository) {
        super(eventBus);
        this.adminRepository = adminRepository;
        UserFilterRequest status = new UserFilterRequest(1);
        getData(status);
    }


    //Get all data from server
    private void getData(UserFilterRequest status) {
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

    //Call block user so all user
    public void blockUser(List<BlockUser> listblock) {
        compositeDisposable.add(adminRepository
                .blockUser(listblock)
                .subscribe(() -> {
                    Timber.d("OOKKKKKKKKKKKK");
                    eventBus.post(new ResponseBlockServer("Success blocked"));
                }, error -> {
                    Timber.tag("THU").d("error: "+error);
                    eventBus.post(new ResponseBlockServer(error));
                })
        );
    }

    public MutableLiveData<Resource<List<User>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }


    public static class TriggerBlockServer extends BaseMessage {
    }

    public static class ResponseBlockServer extends BaseMessage {
        public String message;
        public ResponseBlockServer(Throwable errorMess) {
            super(errorMess);
        }
        public ResponseBlockServer(String message){
            this.message = message;
        }
    }
}
