package com.mvvm.kien2111.mvvmapplication.ui.upgrade.freelancerupgrade;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.ProfileRepository;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.model.Pakage_Upgrade;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.common.RequestUpgradeModel;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;
import com.mvvm.kien2111.mvvmapplication.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class FreelancerUpgradeViewModel extends BaseViewModel{
    private final ProfileRepository repository;
    private final UserRepository userRepository;
    private final PreferenceLiveData preferenceLiveData;
    private final LiveData<Resource<RequestUpgradeModel>> lastestRequest;
    private final MutableLiveData<Resource<List<Pakage_Upgrade>>> listMutableLiveListPakageData = new MutableLiveData<>();
    @Inject
    public FreelancerUpgradeViewModel(EventBus eventBus,
                                      ProfileRepository repository,
                                      PreferenceLiveData preferenceLiveData,
                                      UserRepository userRepository) {
        super(eventBus);
        this.repository = repository;
        this.preferenceLiveData = preferenceLiveData;
        this.userRepository = userRepository;

        lastestRequest = Transformations.switchMap(preferenceLiveData,input -> {
            if(input==null){
                return AbsentLiveData.create();
            }else{
                return MyLiveDataReactiveStream.fromPublisher(repository.getLastestOnProcessRequestUpgradeProfile(input.getUserId()));
            }
        });
        compositeDisposable.add(repository.getListPakageUpgrade().subscribe(pakage_upgrades -> {
            listMutableLiveListPakageData.postValue(Resource.success(pakage_upgrades));
        },throwable -> {
            listMutableLiveListPakageData.postValue(Resource.error(throwable.getMessage(),throwable));
        }));
        compositeDisposable.add(userRepository.syncLocalVersusPromoteData()
                .subscribe());
    }

    public MutableLiveData<Resource<List<Pakage_Upgrade>>> getListMutableLiveListPakageData() {
        return listMutableLiveListPakageData;
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }

    public LiveData<Resource<RequestUpgradeModel>> getLastestRequest() {
        return lastestRequest;
    }

    public void upgradeProfile(final RequestUpgradeModel requestUpdateModel){
        compositeDisposable.add(repository.upgradeProfile(requestUpdateModel)
                .subscribe(()->{
            eventBus.post(new TaskUpgradeRequestMessage(TaskUpgrade.UPGRADE_REQUEST,"Yêu cầu nâng cấp đã được chuyển cho admin để duyệt"));

            },throwable -> {
            eventBus.post(BaseMessage.error(throwable));
        }));
    }

    public void deleteLastestUpgradeRequest(final String userid) {
        compositeDisposable.add(repository.deleteLastestUpgradeRequest(userid)
                .subscribe(()->{
            eventBus.post(new TaskUpgradeRequestMessage(TaskUpgrade.DELETE_LASTEST_REQUEST,"Hủy yêu cầu nâng cấp thành công"));
        },throwable -> {
            eventBus.post(BaseMessage.error(throwable));
        }));
    }
    public static class TaskUpgradeRequestMessage extends BaseMessage{
        public TaskUpgrade taskUpgrade;
        public TaskUpgradeRequestMessage(TaskUpgrade taskUpgrade,String message){super(message);this.taskUpgrade = taskUpgrade;}
    }
    public enum TaskUpgrade{
        DELETE_LASTEST_REQUEST,UPGRADE_REQUEST
    }
}
