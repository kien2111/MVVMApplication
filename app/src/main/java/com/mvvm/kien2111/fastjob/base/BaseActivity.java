package com.mvvm.kien2111.fastjob.base;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.mvvm.kien2111.fastjob.MyApplication;
import com.mvvm.kien2111.fastjob.broadcast.NetworkChangeReceiver;
import com.mvvm.kien2111.fastjob.exception.ApiException;
import com.mvvm.kien2111.fastjob.model.ErrorResponse;
import com.mvvm.kien2111.fastjob.ui.login.LoginActivity;
import com.mvvm.kien2111.fastjob.util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

//import butterknife.ButterKnife;
//import butterknife.Unbinder;


/**
 * Created by WhoAmI on 21/01/2018.
 */

public abstract class BaseActivity<VM extends ViewModel,VB extends ViewDataBinding> extends DaggerAppCompatActivity
    implements NetworkChangeReceiver.ListenNetWorkChange{

    @Inject
    public AccountManager mAccountManager;

    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private Bundle mResultBundle = null;


    @Inject
    protected Gson gson;

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
        bind();
        setUpNetworkReceiver();
        if(this instanceof LoginActivity){
            mAccountAuthenticatorResponse =
                    getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

            if (mAccountAuthenticatorResponse != null) {
                mAccountAuthenticatorResponse.onRequestContinued();
            }
        }
        //mApplication = getApplication();
    }

    private void setUpNetworkReceiver() {
        mNetWorkReceiver = new NetworkChangeReceiver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetWorkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetWorkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
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
        unregisterReceiver(mNetWorkReceiver);
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

    @Deprecated
    public void showErrorDialog(String title,String message){
        if(message==null)throw new IllegalArgumentException("Not supply message");
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Dismiss",((dialog, which) -> {
            dialog.dismiss();
        })).create();
        alertDialog.show();
    }

    @Deprecated
    public void showSuccessDialog(String title,String message){
        if(message==null)throw new IllegalArgumentException("Not supply message");
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Dismiss",((dialog, which) -> {
                    dialog.dismiss();
                })).create();
        alertDialog.show();
    }

    @Deprecated
    public void showServerErrorDialog(ApiException apiException) throws IOException {
        if(apiException==null)throw new IllegalArgumentException("no throwable supply");
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Lỗi từ server");
        ErrorResponse errorResponse = gson.fromJson(apiException.getMessage(),ErrorResponse.class);
        switch (apiException.getErrorType()){
            case BlockedAccount:builder.setMessage(errorResponse.getMessage());
        }
        AlertDialog alertDialog = builder.setPositiveButton("Dismiss",((dialog, which) -> {
            dialog.dismiss();
        })).create();
        alertDialog.show();
    }

    public void showDialog(String title,String message){
        if(message==null)throw new IllegalArgumentException("Not supply message");
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Dismiss",((dialog, which) -> {
                    dialog.dismiss();
                })).create();
        alertDialog.show();
    }

    private BroadcastReceiver mNetWorkReceiver;

    @Override
    public void onNetWorkOff() {
        //TODO
    }

    @Override
    public void onNetWorkOn() {
        //TODO
    }
}
