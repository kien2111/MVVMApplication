package com.mvvm.kien2111.mvvmapplication.ui.admin.upgradeacount;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.AdminRepository;
import com.mvvm.kien2111.mvvmapplication.model.AccountUpgrade;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.UpgradeAccount;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 5/14/2018.
 */

public class UpgradeAccountViewModle extends BaseViewModel {

    private MutableLiveData<Resource<List<UpgradeAccount>>> resourceMutableLiveData = new MutableLiveData<>();
    private final AdminRepository adminRepository;

    @Inject
    public UpgradeAccountViewModle(EventBus eventBus,AdminRepository adminRepository) {
        super(eventBus);
        this.adminRepository=adminRepository;
        getListUpgradeAccount();
    }

    public void getListUpgradeAccount(){
        resourceMutableLiveData.setValue(Resource.loading(null));
        compositeDisposable.add(adminRepository
                .getAllUpgradeAccount()
                .subscribe(listResource -> {
                    resourceMutableLiveData.setValue(listResource);
                }, error -> {
                    resourceMutableLiveData.setValue(Resource.error(error.getMessage(), null));
                })
        );
    }
    public MutableLiveData<Resource<List<UpgradeAccount>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }

    public  void upgradeAccount(List<AccountUpgrade> list){
        compositeDisposable.add(adminRepository
                .upgradeAccount(list)
                .subscribe(() -> {
                    eventBus.post(new ResponseUpgradeServer("Success upgrade"));
                }, error -> {
                    eventBus.post(new ResponseUpgradeServer(error));
                })
        );
    }

    public static class ResponseUpgradeServer extends BaseMessage {
        public String message;
        public ResponseUpgradeServer(Throwable errorMess) {
            super(errorMess);
        }
        public ResponseUpgradeServer(String message){
            this.message = message;
        }
    }
}
