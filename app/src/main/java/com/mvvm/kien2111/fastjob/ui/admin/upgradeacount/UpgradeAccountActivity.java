package com.mvvm.kien2111.fastjob.ui.admin.upgradeacount;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.ActivityAdminUpgradeAccountBinding;
import com.mvvm.kien2111.fastjob.model.AccountUpgrade;
import com.mvvm.kien2111.fastjob.model.UpgradeAccount;
import com.mvvm.kien2111.fastjob.ui.admin.dialog.BasicDialogAdmin;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 5/14/2018.
 */

public class UpgradeAccountActivity extends BaseActivity<UpgradeAccountViewModle,ActivityAdminUpgradeAccountBinding> implements BasicDialogAdmin.BasicDialogAdminListener {
    List<UpgradeAccount> listUpgradeAccount = new ArrayList<>();
    private  UpgradeAccountAdapter upgradeAccountAdapter;
    private BasicDialogAdmin basicDialogAdminProgress;
    private Boolean flag= false;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_upgrade_account;
    }

    @Override
    protected UpgradeAccountViewModle createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(UpgradeAccountViewModle.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
        if (message instanceof UpgradeAccountViewModle.ResponseUpgradeServer) {
            basicDialogAdminProgress.dismiss();
            if(message.getCause()!=null){
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(this);
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Upgrade error!!");
                basicDialogAdmin.setTitle("Upgrade Account")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .setmListener(this)
                        .show();
                flag=false;
            }
            else{
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(this);
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Upgrade error!!");
                basicDialogAdmin.setTitle("Upgrade Account")
                        .setSigleTitleButton("OK")
                        .setView(view)
                        .setmListener(this)
                        .show();
                flag=true;
            }
        }

    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListUpgradeAccount();
    }

    //get data to server
    public  void getListUpgradeAccount(){
        mViewModel.getResourceMutableLiveData().observe(this, listResource -> {
            switch (listResource.status) {
                case SUCCESS:
                    if(listResource.getData().size()>0 && listResource.getData()!=null) {
                        listUpgradeAccount= listResource.getData();
                        setupAdapter();
                    }
                    break;
                case LOADING:
                    break;
                case ERROR:

                    handleError(listResource.message);
                    break;
            }
        });
    }

    //set data to adapter
    public  void setupAdapter(){
        upgradeAccountAdapter = new UpgradeAccountAdapter(this, listUpgradeAccount);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mActivityBinding.recyclerView.setLayoutManager(mLayoutManager);
        mActivityBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mActivityBinding.recyclerView.setAdapter(upgradeAccountAdapter);
    }
    private void handleError(String message) {
    }

    //upgrade account
    public  void upgradeAccount(View view){
        //upgradeAccountAdapter.getDataUpgrade();

        if (upgradeAccountAdapter != null) {
            basicDialogAdminProgress= new BasicDialogAdmin(this);
            View view1 = LayoutInflater.from(this).inflate(R.layout.admin_dialog_progressbar,null,false);
            basicDialogAdminProgress.setView(view1)
                    .show();
            if (upgradeAccountAdapter.getDataUpgrade() != null && upgradeAccountAdapter.getDataUpgrade().size() > 0) {
                mViewModel.upgradeAccount(upgradeAccountAdapter.getDataUpgrade());
            }
        }
    }
    //finish
    public  void finish(View view){
        finish();
    }

    @Override
    public void onClickOK() {
        if(flag){
            for(AccountUpgrade accountUpgrade : upgradeAccountAdapter.getDataUpgrade()){
                for(int i=0;i<listUpgradeAccount.size();i++){
                    if(listUpgradeAccount.get(i).getIdrequest_update_profile()== accountUpgrade.getIdrequest_update_profile()){
                        listUpgradeAccount.remove(listUpgradeAccount.get(i));
                    }
                }
            }
            upgradeAccountAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onClickCancel() {

    }
}
