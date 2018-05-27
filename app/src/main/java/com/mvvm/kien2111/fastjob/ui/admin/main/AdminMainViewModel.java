package com.mvvm.kien2111.fastjob.ui.admin.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AdminRepository;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.model.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by VAKHANHPR on 2/26/2018.
 */


public class AdminMainViewModel extends BaseViewModel {
    public ObservableField<String> mObservableString = new ObservableField("vbmbvnvb");
    private MutableLiveData<Resource<List<User>>> resourceMutableLiveData = new MutableLiveData<>();
    private UserRepository userRepository;
    private final AdminRepository adminRepository;
    @Inject
    public AdminMainViewModel(EventBus eventBus,AdminRepository adminRepository) {
        super(eventBus);
        this.adminRepository = adminRepository;
//        getData();
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

    public  void onclickManagerAccount()
    {
        eventBus.post(new DummyMessage());
        //getNavigator().gotomanagerAccount();
    }
    public void onclicStatistical() {
        //getNavigator().gotoSatisticalActivity();
    }

    public  void onclickManageMyProfile()
    {
        //getNavigator().gotoManageMyProfile();
    }
    static class Message extends BaseMessage{

    }
    static class DummyMessage extends BaseMessage{

    }
}
