package com.mvvm.kien2111.mvvmapplication.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.model.Role;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 28/03/2018.
 */

public class SplashActivity extends AppCompatActivity {
    @Inject
    Gson gson;

    AccountManager mAccountManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAccountManager = AccountManager.get(this);
        getTokenForAccountCreateIfNeeded(
                AccountAuthenticator.ACCOUNT_TYPE,
                AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER);
    }
    private void getExistingAccountAuthToken(Account account, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null, this, null, null);
        new Thread(()-> {
            Bundle bnd = null;
            try {
                bnd = future.getResult();
                final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType) {
        mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                future -> {
                    try {
                        //final String authtoken = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                        final String account_name = future.getResult().getString(AccountManager.KEY_ACCOUNT_NAME);
                        final Account account = new Account(account_name,accountType);
                        final String rolepicked = mAccountManager.getUserData(account,AccountAuthenticator.KEY_RECENTLY_LOGIN_AS);
                        if(Role.RoleName.mapRoleName(rolepicked)== Role.RoleName.ADMIN){
                            startActivity(new Intent(SplashActivity.this, AdminMainActivity.class));
                            //this is admin
                        }else{
                            startActivity(new Intent(SplashActivity.this, UniversalActivity.class));
                            //default is user
                        }
                        finish();
                    } catch (OperationCanceledException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AuthenticatorException e) {
                        e.printStackTrace();
                    }
                }, null);
    }
}
