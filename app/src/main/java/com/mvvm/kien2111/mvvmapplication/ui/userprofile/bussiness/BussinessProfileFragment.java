package com.mvvm.kien2111.mvvmapplication.ui.userprofile.bussiness;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentBussinessProfileBinding;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.WatcherEdittext;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 26/04/2018.
 */

public class BussinessProfileFragment extends BaseFragment<BussinessProfileViewModel,FragmentBussinessProfileBinding> implements View.OnClickListener{
    public static final int PICK_SINGLE_IMAGE_BUSSINESS = 1;

    public static BussinessProfileFragment newInstance(){
        BussinessProfileFragment fragment = new BussinessProfileFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BussinessProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(BussinessProfileViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bussiness_profile;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof UserProfileActivity.UpdateImageInFragmentMessage){
            updateImage(((UserProfileActivity.UpdateImageInFragmentMessage) message).filePath);
        }
    }

    private void updateImage(Uri uri) {
        mFragmentBinding.logoImageView.setImageURI(uri);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpDataChange();
        setOnClick();
        return view;
    }

    private void setOnClick() {
        mFragmentBinding.logoImageView.setOnClickListener(this);
    }

    private void setUpDataChange() {
        mFragmentBinding.edtBrandname.addTextChangedListener(new WatcherEdittext(this.getActivity(), textChanged->{
            ((UserProfileActivity)getActivity()).getBindingUser().setBrandname(textChanged);
        }));
        mFragmentBinding.edtPhonenumber.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getBindingUser().setPhone_company(textChanged);
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logo_imageView:
                ((UserProfileActivity)getActivity()).openGalleryImage(PICK_SINGLE_IMAGE_BUSSINESS);
                break;
        }
    }
}
