package com.mvvm.kien2111.mvvmapplication.ui.depositfund;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.TopUpRequest;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityDepositfundBinding;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 28/04/2018.
 */

public class DepositFundActivity extends BaseActivity<DepositFundViewModel,ActivityDepositfundBinding> {

    User user;

    @Inject
    TableExchangeAdapter tableExchangeAdapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_depositfund;
    }

    @Override
    protected DepositFundViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(DepositFundViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolBar();
        setUpDataTableExchange();
        setUpUserData();
    }

    private void setUpUserData() {
        mViewModel.getPreferenceLiveData().observe(this,changeduser -> {
            this.user = changeduser;
        });
    }

    private void setUpDataTableExchange() {
        mViewModel.getTableExchangeModelMutableLiveData().observe(this,tableExchangeModels -> {
            if(tableExchangeModels!=null){
                tableExchangeAdapter.changeDataSet(tableExchangeModels);
                mActivityBinding.recyclerViewTableExchange.setAdapter(tableExchangeAdapter);
                mActivityBinding.executePendingBindings();
            }
        });
    }

    private void setUpToolBar() {
        setSupportActionBar(mActivityBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void topUpMoney(View view){
        mViewModel.topUpMoney(new TopUpRequest(
                user.getUserId(),
                mActivityBinding.edtSerial.getText().toString()
                ,mActivityBinding.edtPincode.getText().toString()));
    }


}
