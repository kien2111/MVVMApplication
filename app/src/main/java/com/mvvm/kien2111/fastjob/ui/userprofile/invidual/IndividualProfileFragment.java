package com.mvvm.kien2111.fastjob.ui.userprofile.invidual;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.data.local.db.entity.City;
import com.mvvm.kien2111.fastjob.data.local.db.entity.District;
import com.mvvm.kien2111.fastjob.data.remote.model.ApprovePublishRequest;
import com.mvvm.kien2111.fastjob.databinding.FragmentIndividualProfileBinding;
import com.mvvm.kien2111.fastjob.model.Approve_Publish;
import com.mvvm.kien2111.fastjob.model.Gender;
import com.mvvm.kien2111.fastjob.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.fastjob.ui.userprofile.invidual.pickcategory.PickCategoryDialog;
import com.mvvm.kien2111.fastjob.util.CustomSpinner;
import com.mvvm.kien2111.fastjob.util.PublishButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class IndividualProfileFragment extends BaseFragment<IndividualProfileViewModel,FragmentIndividualProfileBinding>
        implements PickCategoryDialog.ListenerPickCategoryDialog,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        RadioGroup.OnCheckedChangeListener,
        PublishButton.OnClickPublish{

    public static IndividualProfileFragment newInstance(){
        IndividualProfileFragment fragment = new IndividualProfileFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected IndividualProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(IndividualProfileViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        switch (message.getState()){
            case SUCCESS:
                ((BaseActivity)getActivity()).showDialog("Thông tin",message.getMessage());
                break;
            case FAIL:
                ((BaseActivity)getActivity()).showDialog("Lỗi",message.getMessage());
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_individual_profile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.background:
                publishProfileHandle();
                break;
            case R.id.birthdaybutton:
                showDatePickerDialog();
                break;
            case R.id.imv_settime:
                showDatePickerDialog();
                break;
            case R.id.tv_datetime:
                showDatePickerDialog();
                break;
            case R.id.categoryProfile:
                showPickCategoryDialog();
                break;
        }
    }

    @Inject
    Calendar calendar;

    @Inject
    SimpleDateFormat simpleDateFormat;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year,month,dayOfMonth);

        mFragmentBinding.tvDatetime.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void showDatePickerDialog(){
        new DatePickerDialog(this.getActivity(),this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void publishProfileHandle() {
        switch (mViewModel.getPreferenceLiveData().getValue().getProfile().getApprove_publish()){
            case ACCEPT:
                doOnAccept();
                break;
            case CONFLICT:
                doOnConflict();
                break;
            case NOT_DO_ANYTHING:
                doOnNothingHappen();
                break;
            case ADMIN_BLOCKED:
                doOnAdminBlocked();
                break;
        }
    }

    private void doOnAccept(){
        mViewModel.doTaskPublishProfile(new ApprovePublishRequest(
                ((UserProfileActivity)getActivity()).getCurrentUser().getUserId(),
                Approve_Publish.NOT_DO_ANYTHING));//from accept -> nothing
    }

    private void doOnNothingHappen(){
        mViewModel.doTaskPublishProfile(
                new ApprovePublishRequest(
                        ((UserProfileActivity)getActivity()).getCurrentUser().getUserId(),
                        Approve_Publish.ACCEPT));//from nothing -> accept
    }

    private void doOnConflict(){
        ((BaseActivity)getActivity()).showDialog("Lỗi","Hồ sơ của bạn đang bị xung đột");
    }

    private void doOnAdminBlocked(){
        ((BaseActivity)getActivity()).showDialog("Lỗi","Chức năng công khai hồ sơ của bạn đã bị khóa");
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpUserData();
        setOnClick();
        setUpSpinnerCityData();
        setUpSpinnerDistrictData();
        return view;
    }

    private void setUpSpinnerDistrictData() {
        mViewModel.getResourceListCityLiveData().observe(this,cities -> {
            if(cities!=null){
                ArrayAdapter<City> citiesAdapter = new ArrayAdapter<City>(this.getActivity(),
                        R.layout.spinner_item_selected,
                        cities);
                citiesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                mFragmentBinding.spinnerCity.setAdapter(citiesAdapter);
                mFragmentBinding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((UserProfileActivity)getActivity()).getCurrentUser().getProfile().setCity((City) parent.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mFragmentBinding.spinnerCity.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
                    public void onSpinnerOpened() {
                        mFragmentBinding.spinnerCity.setSelected(true);
                    }
                    public void onSpinnerClosed() {
                        mFragmentBinding.spinnerCity.setSelected(false);
                    }
                });
                mFragmentBinding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mViewModel.pickCity((City)parent.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                new Handler().postDelayed(()->{

                },1000);
            }
        });
    }

    private void setUpSpinnerCityData() {
        mViewModel.getListDistrictMutableLiveData().observe(this,listResource -> {
            if(listResource!=null && listResource.getData()!=null){
                ArrayAdapter<District> districtArrayAdapter = new ArrayAdapter<District>(this.getActivity(),
                        R.layout.spinner_item_selected,
                        listResource.getData());
                districtArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                mFragmentBinding.spinnerDistrict.setAdapter(districtArrayAdapter);
                mFragmentBinding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((UserProfileActivity)getActivity()).getCurrentUser().getProfile().setDistrict((District)parent.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mFragmentBinding.spinnerDistrict.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
                    public void onSpinnerOpened() {
                        mFragmentBinding.spinnerCity.setSelected(true);
                    }
                    public void onSpinnerClosed() {
                        mFragmentBinding.spinnerCity.setSelected(false);
                    }
                });
                new Handler().postDelayed(()->{

                },1000);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnDataChange();
    }

    private void setOnDataChange() {
        mFragmentBinding.tvDatetime.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().setBirthday(calendar.getTime());
        }));
        mFragmentBinding.edtPhonenumber.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().setPhone_individual(textChanged);
        }));
        mFragmentBinding.edtRealname.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().setRealname(textChanged);
        }));
        mFragmentBinding.edtSummary.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().getProfile().setSummary(textChanged);
        }));
        mFragmentBinding.edtEmail.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().setEmail(textChanged);
        }));
        mFragmentBinding.edtFromSalary.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().getProfile().setSalary_expected_from(Double.valueOf(textChanged));
        }));
        mFragmentBinding.edtToSalary.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getCurrentUser().getProfile().setSalary_expected_to(Double.valueOf(textChanged));
        }));
        mFragmentBinding.radioGender.setOnCheckedChangeListener(this);
        mFragmentBinding.categoryProfile.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            if(((UserProfileActivity)getActivity()).getCurrentUser().getProfile().getCategory()!=null){
                ((UserProfileActivity)getActivity()).getCurrentUser().getProfile().getCategory().setNamecategory(textChanged);
            }
        }));
    }

    private void setOnClick() {
        mFragmentBinding.publishBtn.setOnClickPublish(this);
        mFragmentBinding.birthdaybutton.setOnClickListener(this);
        mFragmentBinding.imvSettime.setOnClickListener(this);
        mFragmentBinding.tvDatetime.setOnClickListener(this);
        mFragmentBinding.categoryProfile.setOnClickListener(this);
    }

    private void setUpUserData() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                new Handler().postDelayed(()->{
                    calendar.setTime(user.getBirthday()==null?Calendar.getInstance().getTime():user.getBirthday());
                    mFragmentBinding.tvDatetime.setText(simpleDateFormat.format(calendar.getTime()));
                    mFragmentBinding.edtSummary.setText(user.getProfile().getSummary());
                    mFragmentBinding.categoryProfile.setText(user.getProfile().getCategory().getNamecategory());
                    mFragmentBinding.radioButtonMale.setChecked(user.getGender() == Gender.MALE);
                    mFragmentBinding.radioButtonFemale.setChecked(user.getGender()==Gender.FEMALE);
                    mFragmentBinding.radioButtonUnknown.setChecked(user.getGender()==Gender.UNKNOWN);
                    mFragmentBinding.edtRealname.setText(user.getRealname());
                    mFragmentBinding.edtEmail.setText(user.getEmail());
                    mFragmentBinding.edtToSalary.setText(String.valueOf(user.getProfile().getSalary_expected_to()));
                    mFragmentBinding.edtFromSalary.setText(String.valueOf(user.getProfile().getSalary_expected_from()));
                    mFragmentBinding.edtPhonenumber.setText(user.getPhone_individual());
                    mFragmentBinding.publishBtn.setDrawableFollowApprove_Publish(user.getProfile().getApprove_publish());
                    mFragmentBinding.executePendingBindings();
                },800);
            }else{
                ((BaseActivity)getActivity()).showDialog("Error","Don't have any data");
            }
        });
    }


    PickCategoryDialog pickCategoryDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDialogPickCategory();
    }

    private void setUpDialogPickCategory() {
        mViewModel.getResourceMutableLiveData().observe(this,listResource -> {
            if(listResource!=null){
                pickCategoryDialog = PickCategoryDialog.newInstance(listResource.getData()==null? Collections.<Category>emptyList():listResource.getData());
                pickCategoryDialog.setOnClickListenerPickCategoryDialog(this);
            }
        });

    }

    public void showPickCategoryDialog(){
        pickCategoryDialog.show(getActivity().getSupportFragmentManager(),"pick category dialog");
    }

    @Override
    public void onClickCategory(Category category) {
        mFragmentBinding.categoryProfile.setText(category.getNamecategory());
        ((UserProfileActivity)getActivity()).getCurrentUser()
                .getProfile()
                .setCategory(category);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        UserProfileActivity activity = (UserProfileActivity)getActivity();
        if(activity!=null){
            switch (checkedId){
                case R.id.radioButtonFemale:
                    activity.getCurrentUser().setGender(Gender.FEMALE);
                    break;
                case R.id.radioButtonMale:
                    activity.getCurrentUser().setGender(Gender.MALE);
                    break;
                case R.id.radioButtonUnknown:
                    activity.getCurrentUser().setGender(Gender.UNKNOWN);
                    break;
            }
        }

    }

    public static class DataChangeMessage extends BaseMessage{

    }

}
