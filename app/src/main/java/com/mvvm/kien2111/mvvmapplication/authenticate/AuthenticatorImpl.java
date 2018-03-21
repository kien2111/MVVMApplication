package com.mvvm.kien2111.mvvmapplication.authenticate;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginRequest;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.model.LoggedInMode;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainViewModel;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 28/01/2018.
 */

public class AuthenticatorImpl extends AbstractAccountAuthenticator {
    Context mContext;
    @Inject
    UserRepository userRepository;
    public AuthenticatorImpl(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, AdminMainActivity.class);
        intent.putExtra(LoginActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(LoginActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(LoginActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT,intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        if (!authTokenType.equals(AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        final AccountManager mAccountManager = AccountManager.get(mContext);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(mAccountManager.peekAuthToken(account, authTokenType));


        //when don't have authtoken but account name and password request 1 token up to date from server
        if(loginResponse.getAccessToken()==null
                || loginResponse.getAccessToken().isEmpty()){
            final String password = mAccountManager.getPassword(account);
            if(password!=null){
                //get authtokentype and token from server
                userRepository.loginServer(new LoginRequest.ExpressLoginRequest(account.name,password))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(resp->{
                            loginResponse.setAccessToken(resp.getAccessToken());
                            loginResponse.setAuth_token_type(resp.getAuth_token_type());
                            userRepository.updateInfo(resp
                                    ,LoggedInMode.LOGGED_IN_MODE_EXPRESS);}
                        ).dispose();
            }
        }
        final Bundle bundle = new Bundle();

        //When have token already save it and return bundle to use later
        if(loginResponse.getAccessToken() !=null && !loginResponse.getAccessToken().equals("")){
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME,account.name);
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE,account.type);
            bundle.putString(AccountManager.KEY_AUTHTOKEN,loginResponse.getAccessToken());
            return bundle;
        }

        //when don't have anything dispatch user to LoginActivity (i.e like first time login)
        final Intent intent = new Intent(mContext,LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,response);
        intent.putExtra(LoginActivity.ARG_ACCOUNT_TYPE,account.type);
        intent.putExtra(LoginActivity.ARG_AUTH_TYPE,authTokenType);
        if (null != account.name) {
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, account.name);
        }
        bundle.putParcelable(AccountManager.KEY_INTENT,intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return authTokenType.equals(AccountAuthenticator.AUTHTOKEN_TYPE_BEARER_LABEL)?authTokenType:null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
