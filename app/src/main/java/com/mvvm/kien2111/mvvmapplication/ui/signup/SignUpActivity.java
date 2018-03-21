package com.mvvm.kien2111.mvvmapplication.ui.signup;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivitySignupBinding;

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

    @Override
    protected int getBindVariable() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
