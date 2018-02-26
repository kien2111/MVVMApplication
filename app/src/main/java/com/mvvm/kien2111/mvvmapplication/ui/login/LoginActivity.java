package com.mvvm.kien2111.mvvmapplication.ui.login;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.*;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityLoginBinding;
import com.mvvm.kien2111.mvvmapplication.model.Credential;

/*import butterknife.BindView;
import butterknife.OnClick;*/

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginViewModel,ActivityLoginBinding> implements LoginNavigator{
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public final static String PARAM_USER_PASS = "USER_PASS";


    public static final int SIGN_UP_ACTIVITY_REQUEST = 10;

    private AccountManager mAccountManager;
    private String mAuthTokenType;

    public static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    /*@OnClick(R.id.email_sign_in_button)
    public void onLogin(){
        try{
            final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
            Bundle data = new Bundle();
            mViewModel.onRequestLogin(new Credential.Builder()
                    .setUsername(username.getText().toString())
                    .setPassword(password.getText().toString())
                    .build());
            data.putString(AccountManager.KEY_ACCOUNT_NAME,mViewModel.getCredentialMutableLiveData().getValue().getUsername());
            data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
            data.putString(AccountManager.KEY_AUTHTOKEN,mViewModel.getCredentialMutableLiveData().getValue().getToken());
            data.putString(PARAM_USER_PASS, mViewModel.getCredentialMutableLiveData().getValue().getUsername());
            navigateToMainScreen(new Intent().putExtras(data));

        }catch (UnauthorizedException uauth){
            uauth.printStackTrace();//Not handle show error on UI
        }

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==SIGN_UP_ACTIVITY_REQUEST && resultCode==RESULT_OK){
            navigateToMainScreen(data);
        }else super.onActivityResult(requestCode, resultCode, data);
    }
    public void navigateToMainScreen(Intent intent){
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountpassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName,intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        if(getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT,false)){
            String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authTokenType = mAuthTokenType;
            mAccountManager.addAccountExplicitly(account, accountpassword, null);
            mAccountManager.setAuthToken(account, authTokenType, authToken);
        }else{
            mAccountManager.setPassword(account, accountpassword);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openMainActivity() {

    }
}

