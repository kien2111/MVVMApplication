package com.mvvm.kien2111.mvvmapplication.ui.admin.apointment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminAppointmentBinding;
import com.mvvm.kien2111.mvvmapplication.model.AdminAppointment;
import com.mvvm.kien2111.mvvmapplication.model.ImpactApointment;
import com.mvvm.kien2111.mvvmapplication.ui.admin.dialog.BasicDialogAdmin;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 5/5/2018.
 */

public class AdminAppointmentActivity extends BaseActivity<AdminAppointmentViewModel,ActivityAdminAppointmentBinding> implements IAppointmentImpactStatus, BasicDialogAdmin.BasicDialogAdminListener {
    private List<AdminAppointment>listAppointment= new ArrayList<>();
    private AdminApointmentAdapter adminApointmentAdapter;
    private BasicDialogAdmin basicDialogAdminProgress;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_appointment;
    }
    @Override
    protected AdminAppointmentViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(AdminAppointmentViewModel.class);
    }
    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    //get data from viewmodel
    public  void getData(){
        mViewModel.getResourceMutableLiveData().observe(this, listResource -> {
            switch (listResource.status) {
                case SUCCESS:
                    if(listResource.getData().size()>0 && listResource.getData()!=null) {
                        listAppointment= listResource.getData();
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

    //call accept and skip appointment
    public  void impackAppointment(){

    }

    private void handleError(String message) {
    }

    //using event bus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {

        if (message instanceof AdminAppointmentViewModel.ResponseAcceptApointmentServer) {
            basicDialogAdminProgress.dismiss();
            if(message.getCause()!=null){
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(this);
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Accept appointment error!!");
                basicDialogAdmin.setTitle("Accept Appointment")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .show();
            }
            else{
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(this);
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Accept appointment success!!");
                basicDialogAdmin.setTitle("Accept User")
                        .setSigleTitleButton("OK")
                        .setView(view)
                        .setmListener(this)
                        .show();
            }
        }
        if(message instanceof AdminAppointmentViewModel.ResponseSkipApointmentServer){
            basicDialogAdminProgress.dismiss();
            if(message.getCause()!=null){
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(this);
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Skip appointment error!!");
                basicDialogAdmin.setTitle("Skip Appointment")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .show();
            }
            else{
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(this);
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Skip appointment success!!");
                basicDialogAdmin.setTitle("Skip User")
                        .setSigleTitleButton("OK")
                        .setView(view)
                        .setmListener(this)
                        .show();
            }
        }

    }

    //setup adapter
    public  void setupAdapter(){
        adminApointmentAdapter = new AdminApointmentAdapter(this,listAppointment,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mActivityBinding.rcAdminAppointment.setLayoutManager(mLayoutManager);
        mActivityBinding.rcAdminAppointment.setItemAnimator(new DefaultItemAnimator());
        mActivityBinding.rcAdminAppointment.setAdapter(adminApointmentAdapter);
    }


    @Override
    public void acceptAppointment(ImpactApointment impactApointment) {
        if(adminApointmentAdapter!=null){
            mViewModel.acceptApointment(impactApointment);
            basicDialogAdminProgress= new BasicDialogAdmin(this);
            View view = LayoutInflater.from(this).inflate(R.layout.admin_dialog_progressbar,null,false);
            basicDialogAdminProgress.setView(view)
                    .show();
        }
    }

    @Override
    public void skipAppointment(ImpactApointment impactApointment) {
        if(adminApointmentAdapter!=null){
            mViewModel.skipAppointment(impactApointment);
            basicDialogAdminProgress= new BasicDialogAdmin(this);
            View view = LayoutInflater.from(this).inflate(R.layout.admin_dialog_progressbar,null,false);
            basicDialogAdminProgress.setView(view)
                    .show();
        }
    }

    @Override
    public void onClickOK() {
        //adminApointmentAdapter.hireButton();
        listAppointment.remove(adminApointmentAdapter.getItemChange());
        adminApointmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickCancel() {

    }

    public void finish(View view){
        finish();
    }
}
