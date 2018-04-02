package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.AdminRepository;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by donki on 3/7/2018.
 */

public class AllUserViewModel extends BaseViewModel {
    private MutableLiveData<Resource<List<User>>> resourceMutableLiveData = new MutableLiveData<>();
    private final AdminRepository adminRepository;
    @Inject
    public AllUserViewModel(EventBus eventBus,AdminRepository adminRepository){
        super(eventBus);
        this.adminRepository = adminRepository;
        getData();
    }
    private void getData() {
        resourceMutableLiveData.setValue(Resource.loading(null));
        compositeDisposable.add(adminRepository
                .getAllUser()
                .subscribe(listResource -> {
                    resourceMutableLiveData.setValue(listResource);
                },error->{
                    resourceMutableLiveData.setValue(Resource.error(error.getMessage(),null));
                })
        );
    }

    public MutableLiveData<Resource<List<User>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }
}
