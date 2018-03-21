package com.mvvm.kien2111.mvvmapplication.base;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mvvm.kien2111.mvvmapplication.MyApplication;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity;
import com.mvvm.kien2111.mvvmapplication.util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

//import butterknife.ButterKnife;
//import butterknife.Unbinder;


/**
 * Created by WhoAmI on 21/01/2018.
 */

public abstract class BaseActivity<VM extends ViewModel,VB extends ViewDataBinding> extends DaggerAppCompatActivity{
    protected AccountManager mAccountManager;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private Bundle mResultBundle = null;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected EventBus eventBus;

    /*@Inject
    protected Class<VM> clazz;*/

    //protected Unbinder unbinder;
    protected VB mActivityBinding;

    protected VM mViewModel;

    @Inject
    protected MyApplication mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountManager = AccountManager.get(this);
        bind();
        if(this instanceof LoginActivity){
            mAccountAuthenticatorResponse =
                    getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

            if (mAccountAuthenticatorResponse != null) {
                mAccountAuthenticatorResponse.onRequestContinued();
            }
        }
        //mApplication = getApplication();
    }

    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }

    public void finish() {
        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED,
                        "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        mActivityBinding.unbind();
        //unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(eventBus.isRegistered(this)){
            eventBus.unregister(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!eventBus.isRegistered(this)){
            eventBus.register(this);
        }
    }

    abstract protected int getLayoutRes();

    protected void bind() {
        mActivityBinding = DataBindingUtil.setContentView(this,getLayoutRes());
        mViewModel = createViewModel();
        mActivityBinding.setVariable(getBindVariable(),mViewModel);
        //unbinder = ButterKnife.bind(this);
    }

    protected abstract VM createViewModel();

    protected abstract int getBindVariable();

    public boolean isNetworkConnected(){
        return NetworkUtil.isNetworkConnected(this);
    }
    public void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager inputMethodManager =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    public ViewModelProvider.Factory getViewModelFactory(){
        return viewModelFactory;
    }

}
