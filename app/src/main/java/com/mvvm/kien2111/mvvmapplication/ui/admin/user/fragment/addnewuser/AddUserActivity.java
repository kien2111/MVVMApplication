package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.addnewuser;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityAdminManageAddnewuserBinding;
import com.mvvm.kien2111.mvvmapplication.model.Role;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.login.LoginViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 4/12/2018.
 */

public class AddUserActivity extends BaseActivity<AddUserViewModel, ActivityAdminManageAddnewuserBinding> implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {

    private int REQUEST_CODE = 1;
    private String avatar;
    private User user = new User();

    @Inject
    Calendar calendar;

    @Inject
    SimpleDateFormat simpleDateFormat;

    private Role role = new Role(0,"admin",null);

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_manage_addnewuser;
    }

    @Override
    protected AddUserViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(AddUserViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
        if (message instanceof LoginViewModel.LoginMessage) {

        } else if (message instanceof AddUserViewModel.TriggerInsertServer) {
            getdatainsert();
        } else if(message instanceof AddUserViewModel.ResponseInsertServer){
            if(message.getCause()!=null){
                //fail
            }else{
                //success
            }
        }
    }
    public void getdatainsert() {
        user.setUserName(String.valueOf(mActivityBinding.edtUsername.getText()));
        user.setEmail(String.valueOf(mActivityBinding.edtEmail.getText()));
        user.setPassword(String.valueOf(mActivityBinding.edtUplaypass.getText()));
        user.setBirthday(calendar.getTime());
        user.setAvatar(avatar);
        mViewModel.gotoinsertUser(user);

        if (mActivityBinding.edtUplaypass.getText().equals(mActivityBinding.edtConfirmpass.getText())) {

        } else {
        }
    }
    //set main
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDatatoSpiner();
        mActivityBinding.tvBirthday.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Inject
    DatasetSpinnerRole datasetSpinnerRole;
    //set data to spiner
    public void setDatatoSpiner() {
        ArrayAdapter adapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,datasetSpinnerRole.getDefault_lst());
        mActivityBinding.spiner.setAdapter(adapter);
        mActivityBinding.spiner.setOnItemSelectedListener(this);
    }

    //set picture in machine
    public void selectImage(View view) {
        ImagePicker.create(this)
                .folderMode(true)
                .single()
                .start(REQUEST_CODE);
    }

    //image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            List<Image> listimage = ImagePicker.getImages(data);
            Glide.with(mActivityBinding.imvShowAvatar)
                    .load(listimage.get(0).getPath())
                    .into(mActivityBinding.imvShowAvatar);
            avatar = listimage.get(0).getPath();
        }
    }

    //finish actiity
    public void finishActivity(View view) {
        finish();
    }

    //set data spiner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);
        if(!(item instanceof Role))return;
        List<Role> listrole = new ArrayList<>();
        role.setIdrole(((Role)mActivityBinding.spiner.getSelectedItem()).getIdrole());
        role.setRolename(mActivityBinding.spiner.getSelectedItem().toString());
        listrole.add(role);
        user.setRole_list(listrole);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //nothing
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    //set birthday user
    public void setbirthday(View view)
    {
        DatePickerDialog pickerDialog = new DatePickerDialog(this,this,
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setTitle("Select birthday");
        pickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year,month,dayOfMonth);
        mActivityBinding.tvBirthday.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
