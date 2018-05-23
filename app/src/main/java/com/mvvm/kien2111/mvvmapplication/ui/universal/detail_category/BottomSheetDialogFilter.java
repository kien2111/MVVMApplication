package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.BottomSheetDialogFilterBinding;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.City;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.util.AnimationUtil;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        setUpSpinnerChooseAction();
        buildExpandMoreFilter();
        return mRootView;
    }

    public void hideDistrictSpinner(){
        if(bottomDialogBinding.districtspinnercontainer.getVisibility()==View.VISIBLE){
            Animation animation = AnimationUtils.loadAnimation(this.getActivity(),R.anim.slide_out_right_ease_out);
            bottomDialogBinding.districtspinnercontainer.startAnimation(animation);
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
        }
    }

    public void showDistrictSpinner(){
        if(bottomDialogBinding.districtspinnercontainer.getVisibility()==View.GONE){
            Animation animation = AnimationUtils.loadAnimation(this.getActivity(),R.anim.slide_in_left_ease_in);
            bottomDialogBinding.districtspinnercontainer.startAnimation(animation);
            bottomDialogBinding.districtspinnercontainer.setVisibility(View.VISIBLE);
        }
    }

    private void setUpSpinnerChooseAction() {
        mViewModel.getFilterActionMutableLiveData().observe(this,action -> {
            if(action!=null){
                switch (action){
                    case CITY_FILTER:
                        hideDistrictSpinner();
                        break;
                    case DISTRICT_FILTER:
                        showDistrictSpinner();
                        break;
                    default:
                        break;
                }
            }
        });
        List<BottomSheetDialogFilterViewModel.FilterAction> listAction = new ArrayList<>();
        listAction.add(BottomSheetDialogFilterViewModel.FilterAction.CITY_FILTER);
        listAction.add(BottomSheetDialogFilterViewModel.FilterAction.DISTRICT_FILTER);
        final ArrayAdapter<BottomSheetDialogFilterViewModel.FilterAction> actionArrayAdapter = new ArrayAdapter<BottomSheetDialogFilterViewModel.FilterAction>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item,listAction);
        bottomDialogBinding.spinnerAction.setAdapter(actionArrayAdapter);
        bottomDialogBinding.spinnerAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.doActionFilter((BottomSheetDialogFilterViewModel.FilterAction) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setOnClick() {
        bottomDialogBinding.filterButton.setOnClickListener(this);
        bottomDialogBinding.expandBtn.setOnClickListener(this);
    }

    private void setUpCitySpinner() {
        mViewModel.getResourceListCityLiveData().observe(this,listcity -> {
            if(listcity!=null){
                listcity.add(new City("All","Tất cả"));
                Collections.sort(listcity, (o1, o2) -> o1.getCityid().compareTo(o2.getCityid()));
                final ArrayAdapter<City> arrayAdapter = new BottomSpinnerAdapter<City>(this.getActivity(),
                        R.layout.spinner_item,
                        listcity){
                };
                bottomDialogBinding.cityspinner.setAdapter(arrayAdapter);
                bottomDialogBinding.cityspinner.setSelection(mViewModel.getCitiesPosition());
                bottomDialogBinding.districtspinner.setSelection(mViewModel.getDistrictsPosition());
            }
        });
        bottomDialogBinding.cityspinner.setOnItemSelectedListener(this);
    }

    private void setUpDistrictSpinner() {
        mViewModel.getListDistrictMutableLiveData().observe(this,listResource -> {
            if(listResource!=null && listResource.getData()!=null){
                listResource.getData().add(new District("All","Tất cả",null));
                Collections.sort(listResource.getData(), (o1, o2) -> o1.getDistid().compareTo(o2.getDistid()));
                final ArrayAdapter<District> arrayAdapter = new BottomSpinnerAdapter<District>(this.getActivity(),
                        R.layout.spinner_item,
                        listResource.getData()){};
                bottomDialogBinding.districtspinner.setAdapter(arrayAdapter);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            case R.id.expand_btn:
                expandMoreFilter();
                break;
        }
    }

    private void expandMoreFilter() {
        if(bottomDialogBinding.expandContainer.getVisibility()==View.GONE){
            AnimationUtil.expand(bottomDialogBinding.expandContainer);
            bottomDialogBinding.expandBtn.setText(R.string.collapse);
        }else{
            AnimationUtil.collapse(bottomDialogBinding.expandContainer);
            bottomDialogBinding.expandBtn.setText(R.string.expand_more_filter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void forwardFilterMessage() {
        eventBus.post(new FilterMessage(
                ((City)bottomDialogBinding.cityspinner.getSelectedItem()).getCityid(),
                ((District)bottomDialogBinding.districtspinner.getSelectedItem()).getDistid(),
                1.0d*bottomDialogBinding.fromSalaryNump.getValue(),
                1.0d*bottomDialogBinding.toSalaryNum.getValue(),
                (Priority) bottomDialogBinding.spinnerSelectLevel.getSelectedItem()));
        /*switch (mViewModel.getAction()){
            case CITY_FILTER:
                eventBus.post(new FilterMessage(
                        ((City)bottomDialogBinding.cityspinner.getSelectedItem()).getCityid(),
                        ((District)bottomDialogBinding.districtspinner.getSelectedItem()).getDistid(),
                        1.0d*bottomDialogBinding.fromSalaryNump.getValue(),
                        1.0d*bottomDialogBinding.toSalaryNum.getValue(),
                        (Priority) bottomDialogBinding.spinnerSelectLevel.getSelectedItem()));
                break;
            case DISTRICT_FILTER:
                eventBus.post(new FilterMessage(
                        ((City)bottomDialogBinding.cityspinner.getSelectedItem()).getCityid(),
                        ((District)bottomDialogBinding.districtspinner.getSelectedItem()).getDistid(),
                        1.0d*bottomDialogBinding.fromSalaryNump.getValue(),
                        1.0d*bottomDialogBinding.toSalaryNum.getValue(),
                        (Priority) bottomDialogBinding.spinnerSelectLevel.getSelectedItem()));
                break;
        }*/
        mViewModel.saveCitiesPosition(bottomDialogBinding.cityspinner.getSelectedItemPosition());
        mViewModel.saveDistrictsPosition(bottomDialogBinding.districtspinner.getSelectedItemPosition());
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
        public String distid;
        public String cityid;
        public Double fromSalary;
        public Double toSalary;
        public Priority priority;
        public FilterMessage(String cityid,String distid,Double fromSalary,Double toSalary,Priority priority){
            this.cityid = cityid;
            this.distid = distid;
            this.fromSalary = fromSalary;
            this.toSalary = toSalary;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "FilterMessage{" +
                    "distid='" + distid  +"/"+
                    ", cityid='" + cityid +"/"+
                    ", fromSalary=" + fromSalary +"/"+
                    ", toSalary=" + toSalary +"/"+
                    ", priority=" + priority.getType() +
                    '}';
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog  = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        return dialog;
    }

    private void buildExpandMoreFilter(){
        final ArrayAdapter<Priority> arrayAdapter = new ArrayAdapter<Priority>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList(Priority.values()));
        bottomDialogBinding.spinnerSelectLevel.setAdapter(arrayAdapter);
        /*bottomDialogBinding.toSalaryNum.setDisplayedValues(this.getActivity().getResources().getStringArray(R.array.salaryPicker));
        bottomDialogBinding.fromSalaryNump.setDisplayedValues(this.getActivity().getResources().getStringArray(R.array.salaryPicker));
        */
        bottomDialogBinding.toSalaryNum.setWrapSelectorWheel(true);
        bottomDialogBinding.fromSalaryNump.setWrapSelectorWheel(true);
        NumberPicker.Formatter formatter = value -> {
            if(value==0)return "0";
            int temp = value*10000000;
            return StringUtil.formatDecimal(temp);
        };
        bottomDialogBinding.fromSalaryNump.setFormatter(formatter);
        bottomDialogBinding.toSalaryNum.setFormatter(formatter);
        bottomDialogBinding.fromSalaryNump.setMaxValue(100);
        bottomDialogBinding.toSalaryNum.setMaxValue(100);
        bottomDialogBinding.fromSalaryNump.setMinValue(0);
        bottomDialogBinding.toSalaryNum.setMinValue(0);
    }
}
