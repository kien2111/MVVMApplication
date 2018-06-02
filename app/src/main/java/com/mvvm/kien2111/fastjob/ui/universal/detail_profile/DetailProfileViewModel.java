package com.mvvm.kien2111.fastjob.ui.universal.detail_profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.RateRepository;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.fastjob.data.remote.model.RateRequest;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.util.AbsentLiveData;
import com.mvvm.kien2111.fastjob.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailProfileViewModel extends BaseViewModel {
    private MutableLiveData<String> useridlivedata = new MutableLiveData<>();
    private LiveData<Resource<User>> resourceLiveData;
    private UserRepository repository;
    private RateRepository rateRepository;
    private PreferenceLiveData preferenceLiveData;
    @Inject
    public DetailProfileViewModel(EventBus eventBus, UserRepository repository, RateRepository rateRepository, PreferenceLiveData preferenceLiveData)
    {
        super(eventBus);
        this.repository = repository;
        this.rateRepository = rateRepository;
        this.preferenceLiveData = preferenceLiveData;
        this.preferenceLiveData.setUser(repository.getUserData().getUser());
        resourceLiveData = Transformations.switchMap(useridlivedata,input -> {
           if(null==input || "".equals(input)){
               return AbsentLiveData.create();
           }else{
                return MyLiveDataReactiveStream.fromPublisher(repository.getDetailProfile(input));
           }
        });
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }

    public void doRate(RateRequest rateRequest){
        compositeDisposable.add(rateRepository.doRate(rateRequest).subscribe(() -> {
            eventBus.post(new RateMessage(rateRequest));
        },throwable -> {
            eventBus.post(BaseMessage.error(throwable));
        }));
    }

    public static class RateMessage extends BaseMessage{
        public RateRequest rateRequest;
        public RateMessage(RateRequest rateRequest){
            this.rateRequest = rateRequest;
        }
    }


    public LiveData<Resource<User>> getResourceUserLiveData() {
        return resourceLiveData;
    }

    public MutableLiveData<String> getUseridlivedata() {
        return useridlivedata;
    }

    public void setUserId(String iduser){
        if(!Objects.equals(iduser,resourceLiveData.getValue())){
            useridlivedata.setValue(iduser);
        }
    }
}
