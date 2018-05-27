package com.mvvm.kien2111.fastjob.base;

import android.accounts.AccountManager;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mvvm.kien2111.fastjob.binding.FragmentBindingComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import dagger.android.support.DaggerFragment;

/**
 * Created by WhoAmI on 21/01/2018.
 */

public abstract class BaseFragment<VM extends ViewModel,VB extends ViewDataBinding> extends DaggerFragment{
    private BaseActivity mActivity;
    protected VB mFragmentBinding;

    @Inject
    public Gson gson;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    public AccountManager mAccountManager;

    @Inject
    protected EventBus eventBus;

    protected VM mViewModel;
    protected View rootView;

    protected android.databinding.DataBindingComponent fragmentBindingComponent = new FragmentBindingComponent(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = createViewModel();
        setHasOptionsMenu(false);
    }

    protected abstract VM createViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false,fragmentBindingComponent);
        rootView = mFragmentBinding.getRoot();
        return rootView;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    protected abstract @LayoutRes int getLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentBinding.setVariable(getBindVariable(),mViewModel);
        mFragmentBinding.executePendingBindings();
    }

    protected abstract int getBindVariable();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(mActivity instanceof BaseActivity){
            BaseActivity baseActivity = (BaseActivity)context;
            this.mActivity = baseActivity;
        }
    }

    public boolean isNetworkConnected(){
        return mActivity!=null && mActivity.isNetworkConnected();
    }
    public void hideKeyBoard(){
        if(mActivity!=null){
            mActivity.hideKeyBoard();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!eventBus.isRegistered(this)){
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(eventBus.isRegistered(this)){
            eventBus.unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
