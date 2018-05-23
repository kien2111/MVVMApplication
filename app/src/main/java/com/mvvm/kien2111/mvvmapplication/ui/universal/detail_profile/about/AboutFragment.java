package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.about;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentAboutProfileBinding;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentAboutProfilebackupBinding;
import com.mvvm.kien2111.mvvmapplication.util.AnimationUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 04/04/2018.
 */

public class AboutFragment extends BaseFragment<AboutViewModel,FragmentAboutProfilebackupBinding> implements View.OnClickListener {
    private static String KEY_PROFILE_MODEL = "key profile model";
    public static AboutFragment newInstance(ProfileModel profileModel){
        AboutFragment aboutFragment = new AboutFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROFILE_MODEL,profileModel);
        aboutFragment.setArguments(bundle);
        return aboutFragment;
    }

    @Override
    protected AboutViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(AboutViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_profilebackup;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mFragmentBinding.setProfile(getArguments().getParcelable(KEY_PROFILE_MODEL));
        showAbout();
        setOnClick();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setOnClick() {
        mFragmentBinding.btnToggleAbout.setOnClickListener(this);
        mFragmentBinding.btnToggleExperiencedDescription.setOnClickListener(this);
        mFragmentBinding.btnToggleSalaryExpected.setOnClickListener(this);
        mFragmentBinding.btnToggleRating.setOnClickListener(this);
    }

    public void transformAnimation(View v){
        if(v.getVisibility()==View.GONE){
            AnimationUtil.fade_content_in(v);
        }
    }

    private void showOffAnimation(View view){
        if(view.getVisibility()==View.GONE){
            AnimationUtil.toggle_in(view);
        }else{
            AnimationUtil.toggle_out(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toggle_about:
                showAbout();
                break;
            case R.id.btn_toggle_experienced_description:
                showExperiencedDescription();
                break;
            case R.id.btn_toggle_salary_expected:
                showSalaryExpected();
                break;
            case R.id.btn_toggle_rating:
                showRatingContainer();
                break;
        }
    }

    private void showRatingContainer() {
        if(mFragmentBinding.expandContainer.getVisibility()==View.GONE){
            mFragmentBinding.btnToggleRating.setSelected(true);
            AnimationUtil.expand(mFragmentBinding.expandContainer);
        }else{
            mFragmentBinding.btnToggleRating.setSelected(false);
            AnimationUtil.collapse(mFragmentBinding.expandContainer);
        }


    }

    private void showExperiencedDescription() {
        transformAnimation(mFragmentBinding.experiencedDescriptionContainer);
        mFragmentBinding.btnToggleAbout.setSelected(false);
        mFragmentBinding.btnToggleSalaryExpected.setSelected(false);
        mFragmentBinding.btnToggleExperiencedDescription.setSelected(true);
        mFragmentBinding.aboutContainer.setVisibility(View.GONE);
        mFragmentBinding.salaryExpectedContainer.setVisibility(View.GONE);
    }

    private void showSalaryExpected(){
        transformAnimation(mFragmentBinding.salaryExpectedContainer);
        mFragmentBinding.btnToggleAbout.setSelected(false);
        mFragmentBinding.btnToggleSalaryExpected.setSelected(true);
        mFragmentBinding.btnToggleExperiencedDescription.setSelected(false);
        mFragmentBinding.aboutContainer.setVisibility(View.GONE);
        mFragmentBinding.experiencedDescriptionContainer.setVisibility(View.GONE);
    }

    private void showAbout() {
        transformAnimation(mFragmentBinding.aboutContainer);
        mFragmentBinding.btnToggleAbout.setSelected(true);
        mFragmentBinding.btnToggleSalaryExpected.setSelected(false);
        mFragmentBinding.btnToggleExperiencedDescription.setSelected(false);
        mFragmentBinding.salaryExpectedContainer.setVisibility(View.GONE);
        mFragmentBinding.experiencedDescriptionContainer.setVisibility(View.GONE);
    }
}
