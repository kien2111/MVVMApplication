package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.ProfileRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.City;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;
import com.mvvm.kien2111.mvvmapplication.model.FilterOption;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;
import com.mvvm.kien2111.mvvmapplication.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class BottomSheetDialogFilterViewModel extends BaseViewModel {
    private final LiveData<Resource<List<District>>> listDistrictMutableLiveData;
    private final MutableLiveData<List<City>> resourceListCityLiveData = new MutableLiveData<>();
    private final MutableLiveData<City> cityMutableLiveData = new MutableLiveData<>();
    private final ProfileRepository profileRepository;
    @Inject
    public BottomSheetDialogFilterViewModel(EventBus eventBus,ProfileRepository profileRepository) {
        super(eventBus);
        this.profileRepository = profileRepository;
        compositeDisposable.add(profileRepository
                .getAllCity()
                .subscribe(resourceListCityLiveData::setValue, throwable -> {

                }));
        listDistrictMutableLiveData = Transformations.switchMap(cityMutableLiveData,input -> {
           return MyLiveDataReactiveStream.fromPublisher(profileRepository.getAllDistrict(input));
        });
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

}
