package com.mvvm.kien2111.fastjob.ui.admin.profile;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.ActivityAdminEditMyinforBinding;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.ui.admin.dialog.BasicDialogAdmin;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.addnewuser.AddUserViewModel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 3/11/2018.
 */

public class AdminManageProfileActivity extends BaseActivity<AdminManageProfileViewModel,ActivityAdminEditMyinforBinding> implements DatePickerDialog.OnDateSetListener, BasicDialogAdmin.BasicDialogAdminListener {

    private int REQUEST_CODE = 1;
    @Inject
    Calendar calendar;
    @Inject
    SimpleDateFormat simpleDateFormat;
    private User userUpdate = new User();
    private BasicDialogAdmin basicDialogAdminDialog;
    private boolean flag= false;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_edit_myinfor;
    }

    @Override
    protected AdminManageProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(AdminManageProfileViewModel.class);
    }
    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //set birthday user
    public void setbirthday(View view) {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setTitle("Select birthday");
        pickerDialog.show();
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
            userUpdate.setAvatar(listimage.get(0).getPath());
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        mActivityBinding.tvBirthday.setText(simpleDateFormat.format(calendar.getTime()));
    }
    //update my profile
    public void updateMyProfile(View view){
        userUpdate.setUserName(String.valueOf(mActivityBinding.edtMyname.getText()));
        userUpdate.setEmail(String.valueOf(mActivityBinding.edtMyemail.getText()));
        userUpdate.setBirthday(calendar.getTime());
        basicDialogAdminDialog = new BasicDialogAdmin(this);
        View viewshow = LayoutInflater.from(this).inflate(R.layout.admin_dialog_progressbar, null, false);
        basicDialogAdminDialog.setView(viewshow)
                .show();
        mViewModel.callUpdteProfile(userUpdate);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
         if (message instanceof AddUserViewModel.ResponseInsertServer) {
            basicDialogAdminDialog.dismiss();
            BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(this);
            if (message.getCause() != null) {
                //fail
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog, null, false);
                TextView tv = view.findViewById(R.id.tv_content);
                tv.setText("Update unsucccess!!!");
                basicDialogAdmin.setTitle("Update Profile")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .setmListener(this)
                        .show();
                flag = false;
            } else {
                basicDialogAdmin.cancel();
                View view = LayoutInflater.from(this).inflate(R.layout.admin_view_dialog, null, false);
                TextView tv = view.findViewById(R.id.tv_content);
                tv.setText("Update success!");
                basicDialogAdmin.setTitle("Update Profile")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .setmListener(this)
                        .show();
                flag = true;
            }
        }
    }

    @Override
    public void onClickOK() {
        if(flag==true){
            finish();
        }
    }

    @Override
    public void onClickCancel() {

    }
    public void  finish(View view){
        finish();
    }
}
