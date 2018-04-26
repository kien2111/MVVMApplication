package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.BottomSheetDialogFilterBinding;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.City;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class BottomSheetDialogFilter extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener,View.OnClickListener,HasSupportFragmentInjector, View.OnTouchListener, CompoundButton.OnCheckedChangeListener {
    private final static String TAG = BottomSheetDialogFilter.class.getSimpleName();
    private boolean isTouchableSwitchbar = false;

    private BottomSheetDialogFilterBinding bottomDialogBinding;
    private View mRootView;
    private BottomSheetDialogFilterViewModel mViewModel;
    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    protected EventBus eventBus;

    public BottomSheetDialogFilter() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(BottomSheetDialogFilterViewModel.class);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomDialogBinding = DataBindingUtil.inflate(inflater,R.layout.bottom_sheet_dialog_filter,container,false);
        mRootView = bottomDialogBinding.getRoot();
        setUpCitySpinner();
        setUpDistrictSpinner();
        setOnClick();
        setOnStateSwitchBarChange();
        return mRootView;
    }


    private void setOnStateSwitchBarChange() {
        bottomDialogBinding.switchToggleFilterDistricts.setChecked(false);
        toggleDistrictSpinner(bottomDialogBinding.switchToggleFilterDistricts.isChecked());
        bottomDialogBinding.switchToggleFilterDistricts.setOnCheckedChangeListener(this);
        bottomDialogBinding.switchToggleFilterDistricts.setOnTouchListener(this);
    }

    private void setOnClick() {
        bottomDialogBinding.filterButton.setOnClickListener(this);
    }

    private void setUpCitySpinner() {

        mViewModel.getResourceListCityLiveData().observe(this,listcity -> {
            final ArrayAdapter<City> arrayAdapter = new BottomSpinnerAdapter<City>(this.getActivity(),
                    R.layout.spinner_item,
                    listcity!=null?listcity: Collections.emptyList()){
            };
            bottomDialogBinding.cityspinner.setAdapter(arrayAdapter);
        });
        bottomDialogBinding.cityspinner.setOnItemSelectedListener(this);
    }

    private void setUpDistrictSpinner() {
        mViewModel.getListDistrictMutableLiveData().observe(this,
                listResource -> {
            final ArrayAdapter<District> arrayAdapter = new BottomSpinnerAdapter<District>(this.getActivity(),
                    R.layout.spinner_item,
                    listResource!=null&&listResource.getData()!=null?listResource.getData():Collections.emptyList()){

            };
            bottomDialogBinding.districtspinner.setAdapter(arrayAdapter);
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(isTouchableSwitchbar){

        }else{

        }
        mViewModel.pickCity((City) parent.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filterButton:
                forwardFilterMessage();
                break;
        }
    }

    private void forwardFilterMessage() {
        if(bottomDialogBinding.districtspinner.getSelectedItem()!=null && !bottomDialogBinding.switchToggleFilterDistricts.isChecked()){
            eventBus.post(new FilterMessage(bottomDialogBinding.districtspinner.getSelectedItem()));
        }else if(bottomDialogBinding.cityspinner.getSelectedItem()!=null && bottomDialogBinding.switchToggleFilterDistricts.isChecked()){
            eventBus.post(new FilterMessage(bottomDialogBinding.cityspinner.getSelectedItem()));
        }else{
            //do nothing
        }
        this.dismiss();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        isTouchableSwitchbar = true;
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isTouchableSwitchbar){
            isTouchableSwitchbar = false;
            if(isChecked){

            }else{

            }
            toggleDistrictSpinner(isChecked);
        }
    }

    public void toggleDistrictSpinner(boolean isChecked){
        if(bottomDialogBinding.districtspinnercontainer.getVisibility()!=View.VISIBLE && isChecked){
            bottomDialogBinding.districtspinnercontainer.startAnimation(AnimationUtils.loadAnimation(this.getActivity(),R.anim.slide_in_up));
            bottomDialogBinding.districtspinnercontainer.setVisibility(View.VISIBLE);
        }else if(bottomDialogBinding.districtspinnercontainer.getVisibility()!=View.GONE && !isChecked){
            Animation animation = AnimationUtils.loadAnimation(this.getActivity(),R.anim.slide_out_down);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    bottomDialogBinding.districtspinnercontainer.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            bottomDialogBinding.districtspinnercontainer.startAnimation(animation);

        }
    }

    public static class FilterMessage extends BaseMessage{
        public Object regionDivision;
        public FilterMessage(Object regionDivision){
            this.regionDivision = regionDivision;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog  = super.onCreateDialog(savedInstanceState);
        return dialog;
    }
}
