package com.mvvm.kien2111.mvvmapplication.ui.admin.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.model.Credential;
import com.mvvm.kien2111.mvvmapplication.model.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by VAKHANHPR on 2/26/2018.
 */

public class AdminMainViewModel extends ViewModel {
    public ObservableField<String> mObservableString = new ObservableField("vbmbvnvb");
    private UserRepository userRepository;
    @Inject
    public  AdminMainViewModel(UserRepository userRepository){

    }


}
