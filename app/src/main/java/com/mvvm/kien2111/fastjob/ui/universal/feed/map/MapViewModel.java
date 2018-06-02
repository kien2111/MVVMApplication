package com.mvvm.kien2111.fastjob.ui.universal.feed.map;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.ProfileRepository;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.util.AbsentLiveData;
import com.mvvm.kien2111.fastjob.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 27/05/2018.
 */

public class MapViewModel extends BaseViewModel {
    private final MutableLiveData<FilterMapModel> filterMapModelMutableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<List<ProfileModel>>> profileLiveData;
    private final ProfileRepository profileRepository;
    @Inject
    public MapViewModel(EventBus eventBus,ProfileRepository profileRepository) {
        super(eventBus);
        this.profileRepository = profileRepository;
        profileLiveData = Transformations.switchMap(filterMapModelMutableLiveData,input -> {
            if(input==null){
                return AbsentLiveData.create();
            }else{
                return MyLiveDataReactiveStream.fromPublisher(profileRepository.getMapProfile(input));
            }
        });
    }

    public void pickAnotherFilter(final FilterMapModel filterMapModel){
        if(filterMapModel==null || filterMapModel.equals(filterMapModelMutableLiveData.getValue())){
            return;
        }
        filterMapModelMutableLiveData.setValue(filterMapModel);
    }

    public LiveData<Resource<List<ProfileModel>>> getProfileLiveData() {
        return profileLiveData;
    }
}
