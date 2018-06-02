package com.mvvm.kien2111.fastjob.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.Objects;

/**
 * Created by WhoAmI on 18/04/2018.
 */

public abstract class BaseNextPageHandler implements Observer<Resource<Boolean>> {
    MutableLiveData<LoadMoreState> loadMoreStateMutableLiveData = new MutableLiveData<>();
    LiveData<Resource<Boolean>> nextPageLiveData;

    private Object[] param;
    Boolean hasMore;
    public BaseNextPageHandler(){
        reload();
    }

    public MutableLiveData<LoadMoreState> getLoadMoreStateMutableLiveData() {
        return loadMoreStateMutableLiveData;
    }

    public void queryNextPage(Object... params){
        if(Objects.equals(this.param,params))
            return;
        unregister();
        this.param = params;
        nextPageLiveData = bindNextPageCall(params);
        loadMoreStateMutableLiveData.setValue(new LoadMoreState(true,null));
        nextPageLiveData.observeForever(this);
    }

    public abstract LiveData<Resource<Boolean>> bindNextPageCall(Object... param);

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
    public void reload(){
        unregister();
        hasMore = true;
        loadMoreStateMutableLiveData.setValue(new LoadMoreState(false,null));
    }
    private void unregister(){
        if(nextPageLiveData!=null){
            nextPageLiveData.removeObserver(this);
            nextPageLiveData = null;
            if(hasMore){
                param = null;
            }
        }
    }
}
