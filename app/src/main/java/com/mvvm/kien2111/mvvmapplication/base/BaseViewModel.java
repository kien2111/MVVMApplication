package com.mvvm.kien2111.mvvmapplication.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public abstract class BaseViewModel extends ViewModel{
    final ObservableBoolean mShowLoading = new ObservableBoolean(false);
    protected final CompositeDisposable compositeDisposable;
    protected final EventBus eventBus;

    public BaseViewModel(EventBus eventBus){
        compositeDisposable = new CompositeDisposable();
        this.eventBus = eventBus;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public ObservableBoolean getShowLoading() {
        return mShowLoading;
    }

    public void setShowLoading(Boolean toggleLoading) {
        this.mShowLoading.set(toggleLoading);
    }

}
