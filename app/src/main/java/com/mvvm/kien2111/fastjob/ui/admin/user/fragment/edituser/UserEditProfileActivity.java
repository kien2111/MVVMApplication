package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.edituser;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.BuildConfig;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.ActivityAdminEditProfileUserBinding;
import com.mvvm.kien2111.fastjob.model.Role;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.ui.admin.dialog.BasicDialogAdmin;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.addnewuser.DatasetSpinnerRole;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser.AllUserFragment;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 4/25/2018.
 */

public class UserEditProfileActivity extends BaseActivity<UserEditProfileViewModel, ActivityAdminEditProfileUserBinding> implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, BasicDialogAdmin.BasicDialogAdminListener {
    User user= new User();
    User userUpdate= new User();
    private int REQUEST_CODE = 1;
    private BasicDialogAdmin basicDialogAdminDialog;
    private  boolean flag=false;

    @Inject
    Calendar calendar;
    @Inject
    SimpleDateFormat simpleDateFormat;
    private Role role = new Role(0,"admin",null);


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_edit_profile_user;
    }

    @Override
    protected UserEditProfileViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(UserEditProfileViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Intent intent =getIntent();
        Bundle bundle = intent.getBundleExtra("ParcelKey");
        user = bundle.getParcelable("data");*/
        user= AllUserFragment.getUserItem();
        userUpdate=user;
        mActivityBinding.setUser(user);
        setDataLayout();
        setDatatoSpiner();
    }

    //using event bus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {

        if(message instanceof UserEditProfileViewModel.ResponseUpdateServer) {
            basicDialogAdminDialog.dismiss();
            BasicDialogAdmin basicDialogAdminshow = new BasicDialogAdmin(this);
            if (message.getCause() != null) {
                //fail
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog, null, false);
                TextView tv = view.findViewById(R.id.tv_content);
                tv.setText("Add new user unsucccess!!!");
                basicDialogAdminshow.setTitle("Add new User")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .setmListener(this)
                        .show();
                flag = false;
            } else {
                basicDialogAdminshow.cancel();
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog, null, false);
                TextView tv = view.findViewById(R.id.tv_content);
                tv.setText("Insert success!");
                basicDialogAdminshow.setTitle("Add new User")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .setmListener(this)
                        .show();
                flag = true;
            }
        }

    }

    public void callUpdateUser(View view1){
        if(0==0 ) {
            basicDialogAdminDialog= new BasicDialogAdmin(this);
            View view = LayoutInflater.from(this).inflate(R.layout.admin_dialog_progressbar,null,false);
            basicDialogAdminDialog.setView(view)
                    .show();

            user.setUserName(String.valueOf(mActivityBinding.edtUsername.getText()));
            user.setEmail(String.valueOf(mActivityBinding.edtEmail.getText()));
            if(mActivityBinding.edtPassword.getText()==null){

            }else {
                user.setPassword(String.valueOf(mActivityBinding.edtPassword.getText()));
            }
            userUpdate.setBirthday(calendar.getTime());
            mViewModel.callUpdteUser(userUpdate);
        }
        else{
            BasicDialogAdmin basicDialogAdminErr = new BasicDialogAdmin(this);
            View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog,null,false);
            TextView tv= view.findViewById(R.id.tv_content);
            tv.setText("Check username,passwork and email!");
            basicDialogAdminErr.setTitle("Error")
                    .setSigleTitleButton("OK")
                    .setView(view)
                    .show();
        }
    }

    //setdata to layout
    public void setDataLayout(){
        if(user!=null) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String date = dateFormat.format(user.getBirthday());
            mActivityBinding.tvBirthday.setText(date);
            Glide.with(this)
                    .load(BuildConfig.IMG_URL + user.getAvatar())
                    .into(mActivityBinding.imvShowAvatar);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year,month,dayOfMonth);
        mActivityBinding.tvBirthday.setText(simpleDateFormat.format(calendar.getTime()));
    }
    public void finishActivity(View view){
        finish();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);
        if(!(item instanceof Role))return;
        List<Role> listrole = new ArrayList<>();
        Role role= new Role(1,null,null);
        role.setIdrole(((Role)mActivityBinding.spiner.getSelectedItem()).getIdrole());
        role.setRolename(mActivityBinding.spiner.getSelectedItem().toString());
        listrole.add(role);
        userUpdate.setRole_list(listrole);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //set picture in machine
    public void selectImage(View view) {
        ImagePicker.create(this)
                .folderMode(true)
                .single()
                .start(REQUEST_CODE);
    }

    @Override
    public void onClickOK() {
        if(!flag){
            finish();
        }
    }
    @Override
    public void onClickCancel() {

    }
}
