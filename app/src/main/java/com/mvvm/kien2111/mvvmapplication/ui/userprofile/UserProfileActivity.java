package com.mvvm.kien2111.mvvmapplication.ui.userprofile;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.binding.BindingAdapters;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityUserprofileBinding;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.freelancerupgrade.FreelancerUpgradeActivity;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.bussiness.BussinessProfileFragment;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.IndividualProfileFragment;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class UserProfileActivity extends BaseActivity<UserProfileViewModel,ActivityUserprofileBinding> implements
        View.OnClickListener {
    private static final int PICK_SINGLE_IMAGE = 18;

    private int REQUEST_ACCEPT_PERMISSION_CODE = 19;


    User currentUser;

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

    public void onClickUpgradeProfile(View v){
        Intent intent = new Intent(this, FreelancerUpgradeActivity.class);
        startActivity(intent);
    }


    private void setUpUserData() {
        mViewModel.getPreferenceLiveData().observe(this,changedUser -> {
            this.currentUser = changedUser;
            new Handler().postDelayed(()->{
                mActivityBinding.txtPriorityProfile.setText(changedUser.getProfile().getPriority().getName());
                mActivityBinding.priorityBtn.setStateChangeWhenPriorityChange(changedUser.getProfile().getPriority());
                BindingAdapters.setImageUrl(mActivityBinding.imvAvatar,changedUser.getAvatar(),
                        ContextCompat.getDrawable(this,R.drawable.defaultimage),
                        ContextCompat.getDrawable(this,R.drawable.errorimg));
                mActivityBinding.txtName.setText(changedUser.getRealname());
            },500);
        });
        setUpViewPagerAdapter();

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
                showDialog("Thông báo", "Đồng bộ thành công");
            } else {
                showErrorDialog("Lỗi", "Đồng bộ thất bại");
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpViewPagerAdapter() {
        viewPagerAdapter.addFragment(IndividualProfileFragment.newInstance(),"Hồ sơ cá nhân");
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

    public User getCurrentUser(){
        return currentUser;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveChangeHandle() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ACCEPT_PERMISSION_CODE);
        switch (mViewModel.getStrategy()){
            case UPDATE_LOGO_ONLY:
                mViewModel.updateLogoOnly(currentUser);
                break;
            case NONE_UPDATE:
                mViewModel.updateProfileWithoutImage(currentUser);
                break;
            case UPDATE_BOTH_IMAGE:
                mViewModel.updateBothImage(currentUser);
                break;
            case UPDATE_AVATAR_ONLY:
                mViewModel.updateAvatarOnly(currentUser);
                break;
        }
        mViewModel.resetStategyAfterUpdate();
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
                if(!(StringUtil.getFileNameFromPath(fullUri).equals(mViewModel.getPreferenceLiveData().getValue().getAvatar()))){
                    currentUser.setAvatar(StringUtil.getRealPathFromURI(this,data.getData()));
                    BindingAdapters.setImageUri(mActivityBinding.imvAvatar,data.getData(),
                            ContextCompat.getDrawable(this,R.drawable.defaultimage),
                            ContextCompat.getDrawable(this,R.drawable.errorimg));
                    mViewModel.getImageChange()[0] = true;
                }
            }else if(requestCode == BussinessProfileFragment.PICK_SINGLE_IMAGE_BUSSINESS){
                if(!(StringUtil.getFileNameFromPath(fullUri).equals(mViewModel.getPreferenceLiveData().getValue().getLogo_company()))){
                    currentUser.setLogo_company(StringUtil.getRealPathFromURI(this,data.getData()));
                    onImageBussinessChange.onImageChange(data.getData());
                    mViewModel.getImageChange()[1] = true;
                }
            }
        }
    }

    private void backPressHandle() {
        finish();
    }

    public static class UpdateImageInFragmentMessage extends BaseMessage{
        public Uri filePath;
        public UpdateImageInFragmentMessage(Uri filePath){
            this.filePath = filePath;
        }
    }

    public void setOnImageBussinessChange(OnImageBussinessChange onImageBussinessChange){
        this.onImageBussinessChange = onImageBussinessChange;
    }

    OnImageBussinessChange onImageBussinessChange;

    public interface OnImageBussinessChange{
        void onImageChange(Uri uri);
    }
}
