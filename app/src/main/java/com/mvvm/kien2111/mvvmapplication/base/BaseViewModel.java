package com.mvvm.kien2111.mvvmapplication.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public class BaseViewModel<N> extends ViewModel{
    private final ObservableBoolean mShowLoading = new ObservableBoolean(false);
    protected final CompositeDisposable compositeDisposable;
    private N mNavigator;
    public BaseViewModel(){
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public ObservableBoolean getmShowLoading() {
        return mShowLoading;
    }

    public void setShowLoading(Boolean toggleLoading) {
        this.mShowLoading.set(toggleLoading);
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setmNavigator(N mNavigator) {
        this.mNavigator = mNavigator;
    }
}
