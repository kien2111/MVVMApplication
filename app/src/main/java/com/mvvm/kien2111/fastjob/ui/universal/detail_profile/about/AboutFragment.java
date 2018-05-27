package com.mvvm.kien2111.fastjob.ui.universal.detail_profile.about;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.databinding.FragmentAboutProfilebackupBinding;
import com.mvvm.kien2111.fastjob.util.AnimationUtil;

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
        setUpStateList();
        return view;
    }

    private void setUpStateList() {
        /*ColorStateList csl = AppCompatResources.getColorStateList(this.getContext(), R.color.state_color_about_tab);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFragmentBinding.imgAbout.setImageTintList(csl);
            mFragmentBinding.imgExperiencedDescription.setImageTintList(csl);
            mFragmentBinding.imgSalaryExpected.setImageTintList(csl);
        }else{
            ImageViewCompat.setImageTintList(mFragmentBinding.imgAbout, csl);
            ImageViewCompat.setImageTintList(mFragmentBinding.imgExperiencedDescription,csl);
            ImageViewCompat.setImageTintList(mFragmentBinding.imgSalaryExpected,csl);
        }*/
    }

    private void setActiveButton(ImageView img, TextView textView){
        img.setColorFilter(Color.BLACK);
        textView.setTextColor(Color.BLACK);
    }

    private void setUnActiveButton(ImageView img, TextView textView){
        img.setColorFilter(Color.GRAY);
        textView.setTextColor(Color.GRAY);
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
        /*mFragmentBinding.btnToggleAbout.setSelected(false);
        mFragmentBinding.btnToggleSalaryExpected.setSelected(false);
        mFragmentBinding.btnToggleExperiencedDescription.setSelected(true);*/
        setActiveButton(mFragmentBinding.imgExperiencedDescription,mFragmentBinding.txtTitleExperiencedDescription);
        setUnActiveButton(mFragmentBinding.imgAbout,mFragmentBinding.txtTitleAbout);
        setUnActiveButton(mFragmentBinding.imgSalaryExpected,mFragmentBinding.txtTitleSalaryExpected);
        mFragmentBinding.aboutContainer.setVisibility(View.GONE);
        mFragmentBinding.salaryExpectedContainer.setVisibility(View.GONE);
    }

    private void showSalaryExpected(){
        transformAnimation(mFragmentBinding.salaryExpectedContainer);
        /*mFragmentBinding.btnToggleAbout.setSelected(false);
        mFragmentBinding.btnToggleSalaryExpected.setSelected(true);
        mFragmentBinding.btnToggleExperiencedDescription.setSelected(false);*/
        setActiveButton(mFragmentBinding.imgSalaryExpected,mFragmentBinding.txtTitleSalaryExpected);
        setUnActiveButton(mFragmentBinding.imgExperiencedDescription,mFragmentBinding.txtTitleExperiencedDescription);
        setUnActiveButton(mFragmentBinding.imgAbout,mFragmentBinding.txtTitleAbout);
        mFragmentBinding.aboutContainer.setVisibility(View.GONE);
        mFragmentBinding.experiencedDescriptionContainer.setVisibility(View.GONE);
    }

    private void showAbout() {
        transformAnimation(mFragmentBinding.aboutContainer);
        /*mFragmentBinding.btnToggleAbout.setSelected(true);
        mFragmentBinding.btnToggleSalaryExpected.setSelected(false);
        mFragmentBinding.btnToggleExperiencedDescription.setSelected(false);*/
        setActiveButton(mFragmentBinding.imgAbout,mFragmentBinding.txtTitleAbout);
        setUnActiveButton(mFragmentBinding.imgExperiencedDescription,mFragmentBinding.txtTitleExperiencedDescription);
        setUnActiveButton(mFragmentBinding.imgSalaryExpected,mFragmentBinding.txtTitleSalaryExpected);
        mFragmentBinding.salaryExpectedContainer.setVisibility(View.GONE);
        mFragmentBinding.experiencedDescriptionContainer.setVisibility(View.GONE);
    }
}
