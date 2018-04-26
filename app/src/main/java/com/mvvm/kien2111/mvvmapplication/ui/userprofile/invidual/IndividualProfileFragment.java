package com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual;

import android.accounts.Account;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.LoginResponse;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentIndividualProfileBinding;
import com.mvvm.kien2111.mvvmapplication.model.Gender;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.pickcategory.PickCategoryDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class IndividualProfileFragment extends BaseFragment<IndividualProfileViewModel,FragmentIndividualProfileBinding> implements PickCategoryDialog.ListenerPickCategoryDialog,View.OnClickListener,DatePickerDialog.OnDateSetListener, RadioGroup.OnCheckedChangeListener {

    private static final String KEY_USER_PROFILE = "Key user data";

    public static IndividualProfileFragment newInstance(User user){
        IndividualProfileFragment fragment = new IndividualProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USER_PROFILE,user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected IndividualProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(IndividualProfileViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_individual_profile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_btn:
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
        mFragmentBinding.getUser().setBirthday(calendar.getTime());
        mFragmentBinding.tvDatetime.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void showDatePickerDialog(){
        new DatePickerDialog(this.getActivity(),this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void publishProfileHandle() {
        switch (mFragmentBinding.getUser().getProfile().getApprove_publish()){
            case ACCEPT:
                doOnAccept();
                break;
            case DECLINE:
                doOnDecLine();
                break;
            case CONFLICT:
                doOnConflict();
                break;
            case ON_PROGRESS:
                doOnProgress();
                break;
            case ADMIN_BLOCKED:
                doOnAdminBlocked();
                break;
        }
    }

    private void doOnAccept(){

    }

    private void doOnDecLine(){

    }

    private void doOnProgress(){

    }

    private void doOnConflict(){
        ((BaseActivity)getActivity()).showErrorDialog("Lỗi","Hồ sơ của bạn đang bị xung đột");
    }

    private void doOnAdminBlocked(){
        ((BaseActivity)getActivity()).showErrorDialog("Lỗi","Chức năng công khai hồ sơ của bạn đã bị khóa");
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
        setOnDataChange();
        return view;
    }

    private void setOnDataChange() {
        mFragmentBinding.tvDatetime.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getBindingUser().setBirthday(calendar.getTime());
        }));
        mFragmentBinding.edtPhonenumber.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getBindingUser().setPhone_individual(textChanged);
        }));
        mFragmentBinding.edtRealname.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getBindingUser().setRealname(textChanged);
        }));
        mFragmentBinding.edtSummary.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            ((UserProfileActivity)getActivity()).getBindingUser().getProfile().setSummary(textChanged);
        }));
        mFragmentBinding.radioGender.setOnCheckedChangeListener(this);
        mFragmentBinding.categoryProfile.addTextChangedListener(new WatcherEdittext(this.getActivity(),textChanged->{
            if(((UserProfileActivity)getActivity()).getBindingUser().getProfile().getCategory()!=null){
                ((UserProfileActivity)getActivity()).getBindingUser().getProfile().getCategory().setNamecategory(textChanged);
            }
        }));
    }

    private void setOnClick() {
        mFragmentBinding.publishBtn.setOnClickListener(this);
        mFragmentBinding.birthdaybutton.setOnClickListener(this);
        mFragmentBinding.imvSettime.setOnClickListener(this);
        mFragmentBinding.tvDatetime.setOnClickListener(this);
        mFragmentBinding.categoryProfile.setOnClickListener(this);
    }

    private void setUpUserData() {
        try{
            User user = getArguments().getParcelable(KEY_USER_PROFILE);
            if(user!=null){
                calendar.setTime(user.getBirthday()==null?Calendar.getInstance().getTime():user.getBirthday());
                mFragmentBinding.tvDatetime.setText(simpleDateFormat.format(calendar.getTime()));
                mFragmentBinding.setUser(user);
                mFragmentBinding.executePendingBindings();
            }else{
                ((BaseActivity)getActivity()).showErrorDialog("Error","Don't have any data");
            }
        }catch (ClassCastException e){
            e.printStackTrace();
        }
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
        ((UserProfileActivity)getActivity()).getBindingUser()
                .getProfile()
                .setCategory(category);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        UserProfileActivity activity = (UserProfileActivity)getActivity();
        if(activity!=null){
            activity.showButtonSaveChangeAnimation();
            switch (checkedId){
                case R.id.radioButtonFemale:
                    activity.getBindingUser().setGender(Gender.FEMALE);
                    break;
                case R.id.radioButtonMale:
                    activity.getBindingUser().setGender(Gender.MALE);
                    break;
                case R.id.radioButtonUnknown:
                    activity.getBindingUser().setGender(Gender.UNKNOWN);
                    break;
            }
        }

    }

    public static class DataChangeMessage extends BaseMessage{

    }

}
