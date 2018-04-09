package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.RateRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateModel;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.DetailCategoryViewModel;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 04/04/2018.
 */

public class ListRateViewModel extends BaseViewModel {
    private final RateRepository rateRepository;
    private final LiveData<Resource<List<RateModel>>> resourceLiveData;
    private final MutableLiveData<String> mutableStringIdUserLiveData = new MutableLiveData<>();
    private final NextPageHandler nextPageHandler;
    @Inject
    public ListRateViewModel(EventBus eventBus, RateRepository rateRepository) {
        super(eventBus);
        this.rateRepository = rateRepository;
        nextPageHandler = new NextPageHandler(rateRepository);
        resourceLiveData = Transformations.switchMap(mutableStringIdUserLiveData,input -> {
            if(input==null || input.length()==0){
                return AbsentLiveData.create();
            }else{
                return LiveDataReactiveStreams.fromPublisher(rateRepository.fetchPageRate(input));
            }
        });
    }

    public LiveData<Resource<List<RateModel>>> getResourceLiveData() {
        return resourceLiveData;
    }
    public LiveData<LoadMoreState> getLoadMoreStateLiveData(){
        return nextPageHandler.getLoadMoreStateMutableLiveData();
    }

    public void loadNextPage() {
        String query = mutableStringIdUserLiveData.getValue();
        if(query == null ||query.trim().length()==0){
            return;
        }
        nextPageHandler.queryNextPage(query);
    }
    public void setIdUser(String iduser){
        nextPageHandler.reload();
        mutableStringIdUserLiveData.setValue(iduser);
    }
    public static class LoadMoreState{
        public final Boolean isRunning;
        public final String errorMessage;
        public LoadMoreState(Boolean isRunning,String errorMessage){
            this.isRunning = isRunning;
            this.errorMessage = errorMessage;
        }
    }
    static class NextPageHandler implements Observer<Resource<Boolean>>{
        MutableLiveData<LoadMoreState> loadMoreStateMutableLiveData = new MutableLiveData<>();
        LiveData<Resource<Boolean>> nextPageLiveData;
        private final RateRepository repository;
        private String query;
        Boolean hasMore;
        public NextPageHandler(RateRepository repository){
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
            nextPageLiveData = LiveDataReactiveStreams.fromPublisher(repository.fetchNextPageRate(query));
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
