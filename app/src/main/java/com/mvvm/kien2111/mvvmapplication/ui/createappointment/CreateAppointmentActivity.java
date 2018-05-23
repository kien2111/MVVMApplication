package com.mvvm.kien2111.mvvmapplication.ui.createappointment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityCreateAppoinmentBinding;
import com.mvvm.kien2111.mvvmapplication.model.Deposit_Fee;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.DetailCategoryFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by WhoAmI on 27/04/2018.
 */

public class CreateAppointmentActivity extends BaseActivity<CreateAppointmentViewModel,ActivityCreateAppoinmentBinding>
        implements DurationPickerDialog.ListenerDurationChange,View.OnClickListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    User user_create_Appintment;


    @Inject
    Calendar calendar;

    @Named("date_format")
    @Inject
    SimpleDateFormat date_format;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_create_appoinment;
    }

    @Override
    protected CreateAppointmentViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(CreateAppointmentViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage baseMessage){
        showDialog("Lỗi",baseMessage.getMessage());
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar();
        setOnClick();
        initializeStartupData();
    }

    private void setOnClick() {
        //mActivityBinding.durationAppointment.setOnClickListener(this);
    }

    private void initializeStartupData() {
        mActivityBinding.txtAppointmenttime.setText(time_format.format(calendar.getTime()));
        mActivityBinding.txtAppointmentDate.setText(date_format.format(calendar.getTime()));
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                this.user_create_Appintment = user;
                mActivityBinding.edtNameAppointment.setText(String.format("Cuộc hẹn của %s và %s",
                        ((ProfileModel)getIntent().getParcelableExtra(DetailCategoryFragment.KEY_BOOKING_PROFILE)).getName(),
                        user.getRealname()));
            }
        });
        mViewModel.getDeposit_feeMutableLiveData().observe(this,deposit_fee -> {
            if(deposit_fee!=null && deposit_fee.getApply()== Deposit_Fee.Apply.APPLY_NOW){
                mActivityBinding.txtDepositFee.setText(String.format("%s đ",decimalFormat.format(deposit_fee.getFee())));
            }
        });
    }

    @Inject
    DecimalFormat decimalFormat;

    public void onCancleCreateAppointment(View v){
        finish();
    }

    public void onAcceptCreateAppointment(View v){
        mViewModel.createAppointment(new AppointmentModel.Builder()
                .setStatus(AppointmentModel.StatusAppointment.ONPROGRESS)
                .setEstimmate_time(calendar.getTime())
                .setIduser_who_create_appointment(user_create_Appintment.getUserId())
                .setIduser_who_receive_appointment(((ProfileModel)getIntent().getExtras().getParcelable(DetailCategoryFragment.KEY_BOOKING_PROFILE)).getIdprofile())
                .setIddeposit_fee(mViewModel.getDeposit_feeMutableLiveData().getValue().getIddeposit())
                .setNote(mActivityBinding.edtNote.getText().toString())
                .setName(mActivityBinding.edtNameAppointment.getText().toString())
                .setDuration(mActivityBinding.durationAppointment.getText().toString())
                .build());
    }

    private void setUpToolbar() {
        setSupportActionBar(mActivityBinding.toolbar);
        mActivityBinding.toolbar.setNavigationOnClickListener(v->finish());
    }

    public void onClickSetTime(View v){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.durationAppointment:
                break;
        }
    }

    public void onClickSetDate(View v){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void showNumberPickDuration(View view){
        final DurationPickerDialog dialog = new DurationPickerDialog();
        dialog.setOnListenDurationChange(this);
        new Handler().postDelayed(()->{
            dialog.show(getSupportFragmentManager(),"numberPicker");
        },10);
    }

    @Inject
    @Named("time_format")
    SimpleDateFormat time_format;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year,month,dayOfMonth);
        mActivityBinding.txtAppointmentDate.setText(date_format.format(calendar.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        mActivityBinding.txtAppointmenttime.setText(time_format.format(calendar.getTime()));
    }

    @Override
    public void onSetDuration(String duration) {
        mActivityBinding.durationAppointment.setText(duration);
    }
}
