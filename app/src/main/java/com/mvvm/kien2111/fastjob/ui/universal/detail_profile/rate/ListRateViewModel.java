package com.mvvm.kien2111.fastjob.ui.universal.detail_profile.rate;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.RateRepository;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RateModel;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.fastjob.data.remote.model.RateRequest;
import com.mvvm.kien2111.fastjob.model.BaseNextPageHandler;
import com.mvvm.kien2111.fastjob.model.LoadMoreState;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.DetailProfileViewModel;
import com.mvvm.kien2111.fastjob.util.AbsentLiveData;
import com.mvvm.kien2111.fastjob.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 04/04/2018.
 */

public class ListRateViewModel extends BaseViewModel {
    private final RateRepository rateRepository;
    private final LiveData<Resource<List<RateModel>>> resourceLiveData;
    private final PreferenceLiveData preferenceLiveData;
    private final MutableLiveData<String> mutableStringIdUserLiveData = new MutableLiveData<>();
    //private final NextPageHandler nextPageHandler;
    private final BaseNextPageHandler nextPageHandler;
    @Inject
    public ListRateViewModel(EventBus eventBus,
                             PreferenceLiveData preferenceLiveData,
                             RateRepository rateRepository) {
        super(eventBus);
        this.rateRepository = rateRepository;
        this.preferenceLiveData = preferenceLiveData;
        this.nextPageHandler = new BaseNextPageHandler(){
            @Override
            public LiveData<Resource<Boolean>> bindNextPageCall(Object... param) {
                return MyLiveDataReactiveStream.fromPublisher(rateRepository.fetchNextPageRate(String.valueOf(param[0])));
            }
        };
        //nextPageHandler = new NextPageHandler(rateRepository);
        resourceLiveData = Transformations.switchMap(mutableStringIdUserLiveData,input -> {
            if(input==null || input.length()==0){
                return AbsentLiveData.create();
            }else{
                return MyLiveDataReactiveStream.fromPublisher(rateRepository.fetchPageRate(input));
            }
        });
    }

    public void doRate(RateRequest rateRequest){
        compositeDisposable.add(rateRepository.doRate(rateRequest).subscribe(() -> {
            eventBus.post(new DetailProfileViewModel.RateMessage(rateRequest));
        },throwable -> {
            eventBus.post(BaseMessage.error(throwable));
        }));
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
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

    /*static class NextPageHandler implements Observer<Resource<Boolean>>{
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
            nextPageLiveData = MyLiveDataReactiveStream.fromPublisher(repository.fetchNextPageRate(query));
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
