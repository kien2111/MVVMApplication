package com.mvvm.kien2111.mvvmapplication.ui.admin.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.model.Credential;
import com.mvvm.kien2111.mvvmapplication.model.Resource;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by VAKHANHPR on 2/26/2018.
 */


public class AdminMainViewModel extends BaseViewModel {
    public ObservableField<String> mObservableString = new ObservableField("vbmbvnvb");
    private UserRepository userRepository;

    @Inject
    public  AdminMainViewModel(UserRepository userRepository){
    }
    public  void onclickManagerAccount()
    {
        getNavigator().gotomanagerAccount();
    }
    public void onclicStatistical()
    {
        //getNavigator().gotoSatisticalActivity();
    public AdminMainViewModel(EventBus eventBus, UserRepository userRepository){
        super(eventBus);
    }

    public  void onclickManageMyProfile()
    {
        //getNavigator().gotoManageMyProfile();
    }

}
