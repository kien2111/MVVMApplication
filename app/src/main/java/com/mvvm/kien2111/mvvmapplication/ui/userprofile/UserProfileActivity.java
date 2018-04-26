package com.mvvm.kien2111.mvvmapplication.ui.userprofile;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityUserprofileBinding;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.bussiness.BussinessProfileFragment;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.IndividualProfileFragment;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class UserProfileActivity extends BaseActivity<UserProfileViewModel,ActivityUserprofileBinding> implements View.OnClickListener {
    private static final int PICK_SINGLE_IMAGE = 18;

    private boolean FLAG_IMAGE_HAS_CHANGED = false;
    private int REQUEST_ACCEPT_PERMISSION_CODE = 19;
    @Inject
    User user;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_userprofile;
    }

    @Override
    protected UserProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(UserProfileViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUserData();
        setOnClick();
    }


    private void setUpUserData() {
        mViewModel.getUserMutableLiveData().observe(this,user -> {
            mActivityBinding.setPriorityProfileName(user.getProfile().getPriority().getName());
            mActivityBinding.setOlduser(user);
            mActivityBinding.executePendingBindings();
        });
        setUpViewPagerAdapter(mViewModel.getStaticUserData());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_ACCEPT_PERMISSION_CODE){

        }
    }

    private void setOnClick() {
        mActivityBinding.imvBack.setOnClickListener(this);
        mActivityBinding.imvAvatar.setOnClickListener(this);
        mActivityBinding.saveChangeButton.setOnClickListener(this);
    }

    @Inject
    ViewPagerAdapter viewPagerAdapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
        if (message instanceof UserProfileViewModel.UserActionMessage) {
            if (((UserProfileViewModel.UserActionMessage) message).message != null) {
                showSuccessDialog("Thông báo", "Đồng bộ thành công");
            } else {
                showErrorDialog("Lỗi", "Đồng bộ thất bại");
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpViewPagerAdapter(final User user) {
        viewPagerAdapter.addFragment(IndividualProfileFragment.newInstance(user),"Hồ sơ cá nhân");
        viewPagerAdapter.addFragment(BussinessProfileFragment.newInstance(),"Hồ sơ doanh nghiệp");
        mActivityBinding.viewPager.setAdapter(viewPagerAdapter);
        mActivityBinding.tabLayout.setupWithViewPager(mActivityBinding.viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_back:
                backPressHandle();
                break;
            case R.id.imv_avatar:
                openGalleryImage(PICK_SINGLE_IMAGE);
                break;
            case R.id.saveChangeButton:
                saveChangeHandle();
                break;

        }
    }

    public User getBindingUser(){
        return user;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveChangeHandle() {
        if(FLAG_IMAGE_HAS_CHANGED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ACCEPT_PERMISSION_CODE);
            mViewModel.updateProfile(user);
            FLAG_IMAGE_HAS_CHANGED = false;

        }else{
            mViewModel.updateProfileWithoutImage(user);
        }
        hideButtonSaveChangeAnimation();
    }



    public void openGalleryImage(int RequestCode) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK ){
            String fullUri = StringUtil.getRealPathFromURI(this,data.getData());
            if(requestCode == PICK_SINGLE_IMAGE){
                if(data==null){
                    //error
                    showErrorDialog("Error","Can't receive image from Gallery App");
                } else if(requestCode==REQUEST_ACCEPT_PERMISSION_CODE){

                }
                else{

                    if(!(fullUri.substring(fullUri.lastIndexOf("/")+1,fullUri.length()).equals(mActivityBinding.getOlduser().getAvatar()))){
                        //mActivityBinding.getUser().setAvatar(StringUtil.getFileNameFromUri(this,data.getData()));
                        FLAG_IMAGE_HAS_CHANGED = true;
                        showButtonSaveChangeAnimation();
                        user.setAvatar(fullUri);
                        mActivityBinding.imvAvatar.setImageURI(data.getData());
                        mActivityBinding.executePendingBindings();
                    }


                }
            }else if(requestCode == BussinessProfileFragment.PICK_SINGLE_IMAGE_BUSSINESS){
                if(!(fullUri.substring(fullUri.lastIndexOf("/")+1,fullUri.length()).equals(mActivityBinding.getOlduser().getLogo_company()))){
                    FLAG_IMAGE_HAS_CHANGED = true;
                    showButtonSaveChangeAnimation();
                    user.setLogo_company(fullUri);
                    eventBus.post(new UpdateImageInFragmentMessage(data.getData()));
                }
            }
        }
    }



    private void backPressHandle() {
        finish();
    }


    public void showButtonSaveChangeAnimation(){
        LinearLayout buttonSaveChange = mActivityBinding.saveChangeButton;
        if(buttonSaveChange.getVisibility()!=View.VISIBLE){
            buttonSaveChange.setAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
            buttonSaveChange.setVisibility(View.VISIBLE);
        }
    }

    public void hideButtonSaveChangeAnimation(){
        LinearLayout buttonSaveChange = mActivityBinding.saveChangeButton;
        if(buttonSaveChange.getVisibility()!=View.GONE){
            buttonSaveChange.setAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_out_down));
            buttonSaveChange.setVisibility(View.GONE);
        }
    }

    public static class UpdateImageInFragmentMessage extends BaseMessage{
        public Uri filePath;
        public UpdateImageInFragmentMessage(Uri filePath){
            this.filePath = filePath;
        }
    }
}
