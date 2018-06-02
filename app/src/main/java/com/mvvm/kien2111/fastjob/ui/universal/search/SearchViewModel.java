package com.mvvm.kien2111.fastjob.ui.universal.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.graphics.Bitmap;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.CategoryRepository;
import com.mvvm.kien2111.fastjob.data.ProfileRepository;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.util.AbsentLiveData;
import com.mvvm.kien2111.fastjob.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class SearchViewModel extends BaseViewModel {
    private final MutableLiveData<String> queryMutatableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<SearchResult>> resourceSearchResultLiveData;
    private final MutableLiveData<Resource<List<RecentSearch>>> resourceRecentSearchLiveData = new MutableLiveData<>();
    private final ProfileRepository profileRepository;
    private final CategoryRepository categoryRepository;
    @Inject
    public SearchViewModel(EventBus eventBus,
                           CategoryRepository categoryRepository,
                           ProfileRepository profileRepository){
        super(eventBus);
        this.profileRepository = profileRepository;
        this.categoryRepository = categoryRepository;
        resourceSearchResultLiveData = Transformations.switchMap(queryMutatableLiveData,input -> {
           if("".equals(input) || input==null){
               return AbsentLiveData.create();
           }else{
               return MyLiveDataReactiveStream.fromPublisher(profileRepository.getSearchResult(input));
           }
        });
        compositeDisposable.add(categoryRepository.fetchRecentSearch().subscribe(recentSearches -> {
            resourceRecentSearchLiveData.postValue(Resource.success(recentSearches));
        },throwable -> {
            resourceRecentSearchLiveData.postValue(Resource.error(throwable.getMessage(),throwable));
        }));
    }

    public MutableLiveData<Resource<List<RecentSearch>>> getResourceRecentSearchLiveData() {
        return resourceRecentSearchLiveData;
    }

    public void saveQuery(final RecentSearch recentSearch, Bitmap bitmap){
        compositeDisposable.add(categoryRepository.saveQuery(recentSearch,bitmap)
                .subscribe());
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
