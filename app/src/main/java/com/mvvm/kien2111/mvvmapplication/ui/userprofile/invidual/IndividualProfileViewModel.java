package com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.CategoryRepository;
import com.mvvm.kien2111.mvvmapplication.data.ProfileRepository;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.City;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.ApprovePublishRequest;
import com.mvvm.kien2111.mvvmapplication.model.Approve_Publish;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileViewModel;
import com.mvvm.kien2111.mvvmapplication.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class IndividualProfileViewModel extends BaseViewModel {
    private final LiveData<Resource<List<District>>> listDistrictMutableLiveData;
    private final MutableLiveData<List<City>> resourceListCityLiveData = new MutableLiveData<>();
    private final MutableLiveData<City> cityMutableLiveData = new MutableLiveData<>();
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<Resource<List<Category>>> resourceMutableLiveData = new MutableLiveData<>();
    private final PreferenceLiveData preferenceLiveData ;
    @Inject
    public IndividualProfileViewModel(EventBus eventBus,
                                      ProfileRepository profileRepository,
                                      PreferenceLiveData preferenceLiveData,
                                      CategoryRepository categoryRepository,
                                      UserRepository userRepository) {
        super(eventBus);
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.preferenceLiveData = preferenceLiveData;
        listDistrictMutableLiveData = Transformations.switchMap(cityMutableLiveData, input ->
                MyLiveDataReactiveStream.fromPublisher(profileRepository.getAllDistrict(input)));
        compositeDisposable.add(profileRepository
                .getAllCity()
                .subscribe(resourceListCityLiveData::setValue, throwable -> {

                }));
        compositeDisposable.add(categoryRepository
                .getListCategory()
                .subscribe(resourceMutableLiveData::setValue, throwable -> {
                    resourceMutableLiveData.setValue(Resource.error(throwable.getMessage(),throwable));
                }));
    }

    public void pickCity(City city){
        if(city!=null && city != cityMutableLiveData.getValue()){
            cityMutableLiveData.setValue(city);
        }
    }

    public LiveData<Resource<List<District>>> getListDistrictMutableLiveData() {
        return listDistrictMutableLiveData;
    }

    public LiveData<List<City>> getResourceListCityLiveData() {
        return resourceListCityLiveData;
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }


    public void doTaskPublishProfile(ApprovePublishRequest approvePublishRequest){
        compositeDisposable.add(profileRepository
                .requestTaskPublishProfile(approvePublishRequest)
                .subscribe(() -> {
                    eventBus.post(BaseMessage.success("Success accept publish"));
                },throwable -> {
                    eventBus.post(BaseMessage.error(throwable));
                }));
    }

    public MutableLiveData<Resource<List<Category>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }

}
