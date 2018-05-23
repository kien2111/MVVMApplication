package com.mvvm.kien2111.mvvmapplication.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.model.Role;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.util.AnimationUtil;
import com.mvvm.kien2111.mvvmapplication.util.RxBinding;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

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

        TextView appName = findViewById(R.id.AppName);
        appName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/OLD.ttf"));
        AnimationUtil.animationTranslateAndFade(appName);
        setUpDynamicLink();
        autoForwardToUniversal();
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

    public void setUpDynamicLink(){
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    Uri deeplink;
                    if(pendingDynamicLinkData!=null){
                        deeplink = pendingDynamicLinkData.getLink();
                        StringUtil.showToastShort(SplashActivity.this,deeplink.toString());

                    }
                }).addOnFailureListener(this, e -> StringUtil.showToastShort(SplashActivity.this,e.getMessage()));
    }

    public void autoForwardToUniversal(){
        AnimationUtil.fade_in(findViewById(R.id.progressBar));
       /* RxBinding.fromViewRaplidClick(v)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(5, TimeUnit.SECONDS)
                .subscribe(view -> {

                });*/
        new Handler().postDelayed(()->{
            getTokenForAccountCreateIfNeeded(
                    AccountAuthenticator.ACCOUNT_TYPE,
                    AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER);
        },5000);
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType) {
        mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                future -> {
                    try {
                        //final String authtoken = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                        final String account_name = future.getResult().getString(AccountManager.KEY_ACCOUNT_NAME);
                        final Account account = new Account(account_name,accountType);
                        final String rolepicked = mAccountManager.getUserData(account,AccountAuthenticator.KEY_RECENTLY_LOGIN_AS);
                        if(Role.RoleMap.mapRoleName(rolepicked)== Role.RoleMap.ADMIN){
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
