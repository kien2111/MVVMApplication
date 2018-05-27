package com.mvvm.kien2111.fastjob.ui.universal.detail_category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableBoolean;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.ProfileRepository;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.model.BaseNextPageHandler;
import com.mvvm.kien2111.fastjob.model.LoadMoreState;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.util.AbsentLiveData;
import com.mvvm.kien2111.fastjob.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailCategoryViewModel extends BaseViewModel {
    private final MutableLiveData<ProfileRequest> mutableCategoryIdLiveData = new MutableLiveData<>();

    ObservableBoolean showRecycleview = new ObservableBoolean(false);
    private final LiveData<Resource<List<ProfileModel>>> resourceListProfileMutableLiveData ;
    //private final NextPageHandler nextPageHandler;
    private final BaseNextPageHandler nextPageHandler;
    private final ProfileRepository profileRepository;
    @Inject
    public DetailCategoryViewModel(EventBus eventBus,ProfileRepository repository){
        super(eventBus);
        this.profileRepository = repository;
        nextPageHandler = new BaseNextPageHandler() {
            @Override
            public LiveData<Resource<Boolean>> bindNextPageCall(Object... param) {
                return MyLiveDataReactiveStream
                        .fromPublisher(repository.fetchNextPageProfile(
                                (ProfileRequest) param[0]));
            }
        };
        resourceListProfileMutableLiveData = Transformations.switchMap(mutableCategoryIdLiveData, input -> {
            if(input==null){
                return AbsentLiveData.create();
            }else{
                return MyLiveDataReactiveStream.fromPublisher(profileRepository.getProfile(input));
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
        ProfileRequest profileRequest = mutableCategoryIdLiveData.getValue();
        if(profileRequest == null ||
                profileRequest.query.trim().length()==0){
            return;
        }
        nextPageHandler.queryNextPage(profileRequest);
    }
    public void setProfileRequest(ProfileRequest profileRequest){
        nextPageHandler.reload();
        mutableCategoryIdLiveData.setValue(profileRequest);
    }

    /*static class NextPageHandler implements Observer<Resource<Boolean>> {
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
            nextPageLiveData = MyLiveDataReactiveStream.fromPublisher(repository.fetchNextPageProfile(query));
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

    }*/

}
