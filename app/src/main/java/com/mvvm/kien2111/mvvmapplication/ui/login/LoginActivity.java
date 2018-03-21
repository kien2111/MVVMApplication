package com.mvvm.kien2111.mvvmapplication.ui.login;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.*;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityLoginBinding;
import com.mvvm.kien2111.mvvmapplication.model.Credential;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/*import butterknife.BindView;
import butterknife.OnClick;*/

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginViewModel,ActivityLoginBinding>{
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public final static String PARAM_USER_PASS = "USER_PASS";

    @Inject
    Gson gson;

    public static final int SIGN_UP_ACTIVITY_REQUEST = 10;

    private String mAuthTokenType;

    public static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); }
        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER;
        if (accountName != null) {
            //username.setText(accountName);
        }
    }
    //final Observer<Credential> credentialObserver = this::onChangeCredential;
    public void onChangeCredential(Credential credential){
        //update ui
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }


    protected void buildDataBinding() {

    }

    @Override
    protected LoginViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==SIGN_UP_ACTIVITY_REQUEST && resultCode==RESULT_OK){
            navigateToMainScreen(data);//sign up success automatically navigate to main screen no need to fill user pass again
        }else super.onActivityResult(requestCode, resultCode, data);
    }
    public void navigateToMainScreen(Intent intent){
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountpassword = intent.getStringExtra(PARAM_USER_PASS);
        String userdata = intent.getStringExtra(AccountAuthenticator.KEY_USER_DATA);
        final Account account = new Account(accountName,intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        if(getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT,false)){
            String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authTokenType = mAuthTokenType;
            mAccountManager.addAccountExplicitly(account, accountpassword, null);
            mAccountManager.setAuthToken(account, authTokenType, authToken);
            mAccountManager.setUserData(account,AccountAuthenticator.KEY_USER_DATA,userdata);
        }else{
            mAccountManager.setPassword(account, accountpassword);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK,intent);
        finish();//from baseactivity

    }


    public void loginSocial(View v){

        Animation animationfadeout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        Animation animationmove_up = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);
        mActivityBinding.loginSocial.startAnimation(animationfadeout);
        animationfadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mActivityBinding.loginSocial.setVisibility(View.GONE);
                mActivityBinding.googleLogin.startAnimation(animationmove_up);
                mActivityBinding.facebookLogin.startAnimation(animationmove_up);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationmove_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mActivityBinding.googleLogin.setVisibility(View.VISIBLE);
                mActivityBinding.facebookLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    private void showAccountPicker(final String authTokenType) {
        final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountAuthenticator.ACCOUNT_TYPE);

        if (availableAccounts.length == 0) {
            Toast.makeText(this, "No accounts", Toast.LENGTH_SHORT).show();
        } else {
            String name[] = new String[availableAccounts.length];
            for (int i = 0; i < availableAccounts.length; i++) {
                name[i] = availableAccounts[i].name;
            }

            // Account picker
            AlertDialog mAlertDialog = new AlertDialog.Builder(this).setTitle("Pick Account").setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, name), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getExistingAccountAuthToken(availableAccounts[which], authTokenType);
                }
            }).create();
            mAlertDialog.show();
        }
    }

    private void getExistingAccountAuthToken(Account availableAccount, String authTokenType) {
        //this get access token go to AuthenticatorIml and navigate to main screen
        mAccountManager.getAuthTokenByFeatures(availableAccount.type,authTokenType,null,this,null,null,future -> {
            try {
                String access_token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                Toast.makeText(LoginActivity.this," access token : "+access_token,Toast.LENGTH_LONG).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        },null);
    }

    public void login() {
        String username = mActivityBinding.username.getText().toString();
        String password = mActivityBinding.password.getText().toString();
        mViewModel.login(username,password);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
        if(message instanceof LoginViewModel.LoginMessage){
            LoginViewModel.LoginMessage loginMessage = ((LoginViewModel.LoginMessage)message);
            if(loginMessage.loginResponse!=null){
                //save account to AccountManager and navigate to Universal
                final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
                Bundle data = new Bundle();
                data.putString(AccountManager.KEY_ACCOUNT_NAME,loginMessage.loginResponse.getUserName());
                data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                data.putString(AccountManager.KEY_AUTHTOKEN,loginMessage.loginResponse.getAccessToken());
                data.putString(AccountAuthenticator.KEY_USER_DATA,gson.toJson(loginMessage.loginResponse));
                data.putString(PARAM_USER_PASS, loginMessage.loginResponse.getUserName());
                navigateToMainScreen(new Intent().putExtras(data));
            }else{
                handleLoginError(message.getErrorMessage());
            }

        }else if(message instanceof LoginViewModel.TriggerLoginServer){
            login();
        }
        else{

        }
    }

    public void handleLoginError(String message){
        //show error dialog
    }



}

