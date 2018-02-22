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
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginActivity;
import com.mvvm.kien2111.mvvmapplication.model.Credential;

import javax.inject.Inject;

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
        final Intent intent = new Intent(mContext, LoginActivity.class);
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
        if (!authTokenType.equals(AccountAuthenticator.AUTHTOKEN_TYPE_BEARER)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        final AccountManager mAccountManager = AccountManager.get(mContext);
        final Credential credential = new Credential.Builder().build();
        credential.setToken(mAccountManager.peekAuthToken(account, authTokenType));

        //when don't have authtoken but account name and password request 1 token up to date from server
        if(credential.getToken() ==null || credential.getToken().isEmpty()){
            final String password = mAccountManager.getPassword(account);
            if(password!=null){
                credential.setUsername(account.name);
                credential.setPassword(password);
                credential.setToken(userRepository.requestLogin(credential).getValue().getData().getToken());
            }
            /*if(password!=null){
                userRepository.requestLogin(new Credential.Builder()
                        .setUsername(account.name)
                        .setPassword(password).build())
                        .observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result->credential.setToken(result.getToken()),
                                RxDefault::onErrorHandler
                        ).dispose();
            }*/
        }
        final Bundle bundle = new Bundle();

        //When have token already save it and return bundle to use later
        if(credential.getToken() !=null && !credential.getToken().equals("")){
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME,account.name);
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE,account.type);
            bundle.putString(AccountManager.KEY_AUTHTOKEN, credential.getToken());
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
