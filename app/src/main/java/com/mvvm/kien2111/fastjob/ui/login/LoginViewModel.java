package com.mvvm.kien2111.fastjob.ui.login;


import android.accounts.Account;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.remote.model.LoginRequest;
import com.mvvm.kien2111.fastjob.data.remote.model.LoginResponse;
import com.mvvm.kien2111.fastjob.model.Credential;
import com.mvvm.kien2111.fastjob.model.LoggedInMode;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by WhoAmI on 21/01/2018.
 */

public class LoginViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    @Inject
    LoginViewModel(EventBus eventBus, UserRepository userRepository){
        super(eventBus);
        this.userRepository = userRepository;
    }
    public ObservableBoolean getLoading(){
        return getShowLoading();
    }
    private MutableLiveData<Credential> mCredentialMutableLiveData;


    public void login(String username,String password){
        setShowLoading(true);
        compositeDisposable.add(
                userRepository.loginServer(new LoginRequest.ExpressLoginRequest(username,password))
                .doOnSuccess(response->{
                    userRepository.updateInfo(response
                            ,LoggedInMode.LOGGED_IN_MODE_EXPRESS,
                            new Account(response.getUser().getUserName()
                                    ,"com.mvvm.finalcourse"));
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response->{
                    setShowLoading(false);
                    eventBus.post(new LoginMessage(response));
                    //getNavigator().onLoginSuccess(username,password,remote.getAccessToken());
                },throwable ->{
                    setShowLoading(false);
                    eventBus.post(new LoginMessage(throwable.getMessage()));
                }));
    }
    public void serverlogin(){
        eventBus.post(new TriggerLoginServer());
    }

    public static class LoginMessage extends BaseMessage {
        LoginResponse loginResponse;

        public LoginMessage(String errorMessage) {
            super(errorMessage);
        }
        public LoginMessage(LoginResponse loginResponse){
            this.loginResponse = loginResponse;
        }
    }
    public static class TriggerLoginServer extends BaseMessage{

    }



}
