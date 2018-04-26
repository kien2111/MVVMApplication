package com.mvvm.kien2111.mvvmapplication.ui.universal.user;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Injectable;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentUserBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileActivity;

import java.io.IOException;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public class UserFragment extends BaseFragment<UserViewModel,FragmentUserBinding> implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMuser;
    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setUpUserData();
        setOnClick();
        return view;
    }

    private void setOnClick() {
        mFragmentBinding.profiledetailbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profiledetailbutton:
                forwardToProfileDetail();
                break;
        }
    }

    private void forwardToProfileDetail() {
        Intent intent = new Intent(this.getActivity(), UserProfileActivity.class);
        startActivity(intent);
    }


    private void setUpUserData() {
        final Account[] account = new Account[1];
        mAccountManager.getAuthTokenByFeatures(AccountAuthenticator.ACCOUNT_TYPE,
                AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER, null, null, null, null,future -> {
                    try {
                        account[0] = new Account(future.getResult().getString(AccountManager.KEY_ACCOUNT_NAME),future.getResult().getString(AccountManager.KEY_ACCOUNT_TYPE));
                        mAccountManager.getUserData(account[0],AccountAuthenticator.KEY_USER_DATA);
                    } catch (OperationCanceledException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AuthenticatorException e) {
                        e.printStackTrace();
                    }
                },null);
    }

    private void navigateToDetailUser(){
        UniversalActivity activity = (UniversalActivity)getActivity();
        //activity.startActivity(new Intent(activity,));
    }

    private void navigateToInviteFriend(){

    }

    private void navigateToDepositMoney(){

    }


}
