package com.mvvm.kien2111.fastjob.ui.userprofile.bussiness;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.FragmentBussinessProfileBinding;
import com.mvvm.kien2111.fastjob.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.fastjob.ui.userprofile.invidual.WatcherEdittext;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 26/04/2018.
 */

public class BussinessProfileFragment extends BaseFragment<BussinessProfileViewModel,FragmentBussinessProfileBinding> implements View.OnClickListener,UserProfileActivity.OnImageBussinessChange{
    public static final int PICK_SINGLE_IMAGE_BUSSINESS = 1;
    public static BussinessProfileFragment newInstance(){
        BussinessProfileFragment fragment = new BussinessProfileFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onImageChange(Uri uri) {
        updateImage(uri);
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
        fragmentBindingComponent.getFragmentBindingAdapter().setImageUri(mFragmentBinding.logoImageView,
                uri, ContextCompat.getDrawable(this.getActivity(),R.drawable.defaultimage),
                ContextCompat.getDrawable(this.getActivity(),R.drawable.errorimg));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpDataChange();
        bindingUserData();
        setOnClick();
        setOnListenImageChange();
        return view;
    }

    private void bindingUserData() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                try{
                    new Handler().postDelayed(()->{
                        fragmentBindingComponent.getFragmentBindingAdapter()
                                .setImageUrl(mFragmentBinding.logoImageView,user.getLogo_company(),
                                        ContextCompat.getDrawable(this.getActivity(),R.drawable.defaultimage),
                                        ContextCompat.getDrawable(this.getActivity(),R.drawable.errorimg));
                        mFragmentBinding.edtBrandname.setText(user.getBrandname());
                        mFragmentBinding.edtPhonenumber.setText(user.getPhone_company());
                        mFragmentBinding.executePendingBindings();
                    },800);
                }catch (Exception e){

                }

            }else{

            }
        });

    }

    private void setOnListenImageChange() {
        ((UserProfileActivity)getActivity()).setOnImageBussinessChange(this);
    }

    private void setOnClick() {
        mFragmentBinding.logoImageView.setOnClickListener(this);
    }

    private void setUpDataChange() {
        mFragmentBinding.edtBrandname.addTextChangedListener(new WatcherEdittext(this.getActivity(), textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().setBrandname(textChanged);
        }));
        mFragmentBinding.edtPhonenumber.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().setPhone_company(textChanged);
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
