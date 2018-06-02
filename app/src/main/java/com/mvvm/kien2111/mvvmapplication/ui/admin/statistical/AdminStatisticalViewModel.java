package com.mvvm.kien2111.mvvmapplication.ui.admin.statistical;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.AdminRepository;
import com.mvvm.kien2111.mvvmapplication.model.Income;
import com.mvvm.kien2111.mvvmapplication.model.RequestStaticfy;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;
import com.mvvm.kien2111.mvvmapplication.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 3/9/2018.
 */

public class AdminStatisticalViewModel extends BaseViewModel {
    private  List<Income> listIncome = new ArrayList<>();
    private LiveData<Resource<List<Income>>> resourceLiveData;
    private MutableLiveData<RequestStaticfy> requestStaticfyMutableLiveData = new MutableLiveData<>();
    private final AdminRepository adminRepository;

    @Inject
    public  AdminStatisticalViewModel(EventBus eventBus,AdminRepository adminRepository){
        super(eventBus);
        this.adminRepository = adminRepository;
        resourceLiveData = Transformations.switchMap(requestStaticfyMutableLiveData, input -> {
            if(input==null){
                return AbsentLiveData.create();
            }else{
                return MyLiveDataReactiveStream.fromPublisher(adminRepository.getAllStatisfy(input));
            }
        });
    }

    public void filter(RequestStaticfy requestStaticfy){
        if(requestStaticfy==null || requestStaticfy.equals(requestStaticfyMutableLiveData.getValue()))return;
        requestStaticfyMutableLiveData.setValue(requestStaticfy);
    }

    public LiveData<Resource<List<Income>>> getResourceLiveData() {
        return resourceLiveData;
    }
}
