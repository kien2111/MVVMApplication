package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableBoolean;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.ProfileRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailCategoryViewModel extends BaseViewModel {
    MutableLiveData<String> mutableCategoryIdLiveData = new MutableLiveData<>();
    ObservableBoolean showRecycleview = new ObservableBoolean(false);
    LiveData<Resource<List<ProfileModel>>> resourceListProfileMutableLiveData ;
    private final NextPageHandler nextPageHandler;
    private final ProfileRepository profileRepository;
    @Inject
    public DetailCategoryViewModel(EventBus eventBus,ProfileRepository repository){
        super(eventBus);
        this.profileRepository = repository;
        nextPageHandler = new NextPageHandler(repository);
        resourceListProfileMutableLiveData = Transformations.switchMap(mutableCategoryIdLiveData, input -> {
            if(input==null){
                return AbsentLiveData.create();
            }else{
                return LiveDataReactiveStreams.fromPublisher(profileRepository.getProfile(input));
            }
        });

    }

    public ObservableBoolean getShowRecycleview() {
        return showRecycleview;
    }

    public LiveData<LoadMoreState> getLoadMoreStateLiveData(){
        return nextPageHandler.getLoadMoreStateMutableLiveData();
    }
    public LiveData<Resource<List<ProfileModel>>> getResourceListProfileMutableLiveData() {
        return resourceListProfileMutableLiveData;
    }

    public ObservableBoolean getShowProgressBar(){
        return super.getShowLoading();
    }

    public void loadNextPage() {
        String query = mutableCategoryIdLiveData.getValue();
        if(query == null ||query.trim().length()==0){
            return;
        }
        nextPageHandler.queryNextPage(query);
    }
    public void setIdCategory(String idCategory){
        nextPageHandler.reload();
        mutableCategoryIdLiveData.setValue(idCategory);
    }

    public static class LoadMoreState{
        public final Boolean isRunning;
        public final String errorMessage;
        public LoadMoreState(Boolean isRunning,String errorMessage){
            this.isRunning = isRunning;
            this.errorMessage = errorMessage;
        }
    }
    static class NextPageHandler implements Observer<Resource<Boolean>> {
        MutableLiveData<LoadMoreState> loadMoreStateMutableLiveData = new MutableLiveData<>();
        LiveData<Resource<Boolean>> nextPageLiveData;
        private final ProfileRepository repository;
        private String query;
        Boolean hasMore;
        public NextPageHandler(ProfileRepository repository){
            this.repository = repository;
            reload();
        }

        public MutableLiveData<LoadMoreState> getLoadMoreStateMutableLiveData() {
            return loadMoreStateMutableLiveData;
        }

        void queryNextPage(String query){
            if(Objects.equals(this.query,query))
                return;
            unregister();
            this.query = query;
            nextPageLiveData = LiveDataReactiveStreams.fromPublisher(repository.fetchNextPageProfile(query));
            loadMoreStateMutableLiveData.setValue(new LoadMoreState(true,null));
            nextPageLiveData.observeForever(this);
        }

        @Override
        public void onChanged(@Nullable Resource<Boolean> result) {
            if(result==null){
                return;
            }else {
                switch (result.status){
                    case SUCCESS:
                        hasMore = Boolean.TRUE.equals(result.data);
                        unregister();
                        loadMoreStateMutableLiveData.setValue(new LoadMoreState(false,null));
                        break;
                    case ERROR:
                        hasMore = true;
                        unregister();
                        loadMoreStateMutableLiveData.setValue(new LoadMoreState(false,result.message));
                        break;
                }
            }
        }
        private void reload(){
            unregister();
            hasMore = true;
            loadMoreStateMutableLiveData.setValue(new LoadMoreState(false,null));
        }
        private void unregister(){
            if(nextPageLiveData!=null){
                nextPageLiveData.removeObserver(this);
                nextPageLiveData = null;
                if(hasMore){
                    query = null;
                }
            }
        }

    }

}
