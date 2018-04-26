package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.ProfileRepository;
import com.mvvm.kien2111.mvvmapplication.data.RateRepository;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.RateRequest;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;
import com.mvvm.kien2111.mvvmapplication.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailProfileViewModel extends BaseViewModel {
    private MutableLiveData<String> useridlivedata = new MutableLiveData<>();
    private LiveData<Resource<DetailProfileWithPoint>> resourceLiveData;
    private UserRepository repository;
    private RateRepository rateRepository;
    @Inject
    public DetailProfileViewModel(EventBus eventBus,UserRepository repository,RateRepository rateRepository)
    {
        super(eventBus);
        this.repository = repository;
        this.rateRepository = rateRepository;
        resourceLiveData = Transformations.switchMap(useridlivedata,input -> {
           if(null==input || "".equals(input)){
               return AbsentLiveData.create();
           }else{
                return MyLiveDataReactiveStream.fromPublisher(repository.getDetailProfile(input));
           }
        });
    }

    public void doRate(RateRequest rateRequest){
        compositeDisposable.add(rateRepository.doRate(rateRequest).subscribe(() -> {
            eventBus.post(new RateMessage(RateMessage.Status.SUCCESS));
        },throwable -> {
            eventBus.post(new RateMessage(RateMessage.Status.ERROR,throwable));
        }));
    }

    public static class RateMessage extends BaseMessage{
        public enum Status{
            SUCCESS,ERROR
        }
        public RateMessage(Status status){

        }
        public RateMessage(Status status,Throwable throwable){super(throwable);this.status = status;}
        public Status status;
    }


    public LiveData<Resource<DetailProfileWithPoint>> getResourceUserLiveData() {
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
