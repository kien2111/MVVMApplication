package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.ProfileRepository;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class SearchViewModel extends BaseViewModel {
    private final MutableLiveData<String> queryMutatableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<SearchResult>> resourceSearchResultLiveData;
    private final ProfileRepository profileRepository;
    @Inject
    public SearchViewModel(EventBus eventBus,ProfileRepository profileRepository){
        super(eventBus);
        this.profileRepository = profileRepository;
        resourceSearchResultLiveData = Transformations.switchMap(queryMutatableLiveData,input -> {
           if("".equals(input) || input==null){
               return AbsentLiveData.create();
           }else{
               return LiveDataReactiveStreams.fromPublisher(profileRepository.getSearchResult(input));
           }
        });
    }

    public LiveData<Resource<SearchResult>> getResourceSearchResultLiveData() {
        return resourceSearchResultLiveData;
    }

    public void setShowLoading(Boolean toggle){
        super.setShowLoading(toggle);
    }

    public void setNewQuery(String newQuery){
        if(newQuery.equals(queryMutatableLiveData.getValue())){
            return;
        }
        queryMutatableLiveData.setValue(newQuery);
    }
}
