package com.mvvm.kien2111.fastjob.ui.signup;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.data.remote.model.SignUpRequest;
import com.mvvm.kien2111.fastjob.databinding.ActivitySignupBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 30/01/2018.
 */

public class SignUpActivity extends BaseActivity<SignUpViewModel,ActivitySignupBinding>{
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_signup;
    }

    @Override
    protected SignUpViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(SignUpViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        switch (message.getState()){
            case SUCCESS:
                showSuccessiveSignUpDialog("Thông tin",message.getMessage());
                break;
            case FAIL:
                showDialog("Lỗi",message.getMessage());
                break;
        }
    }

    public void showSuccessiveSignUpDialog(String title,String message){
        if(message==null)throw new IllegalArgumentException("Not supply message");
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Dismiss",((dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })).create();
        alertDialog.show();
    }

    @Override
    protected int getBindVariable() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onClickSignUpRequest(View v){
        mViewModel.signUp(new SignUpRequest.Builder()
                .setUsername(mActivityBinding.username.getText().toString())
                .setEmail(mActivityBinding.edtEmail.getText().toString())
                .setPassword(mActivityBinding.edtPassword.getText().toString())
                .build());
    }

}
