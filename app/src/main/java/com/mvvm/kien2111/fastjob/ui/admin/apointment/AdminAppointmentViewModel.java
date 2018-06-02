package com.mvvm.kien2111.fastjob.ui.admin.apointment;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AdminRepository;
import com.mvvm.kien2111.fastjob.model.AdminAppointment;
import com.mvvm.kien2111.fastjob.model.ImpactApointment;
import com.mvvm.kien2111.fastjob.model.Resource;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 5/5/2018.
 */

public class AdminAppointmentViewModel extends BaseViewModel {

    private MutableLiveData<Resource<List<AdminAppointment>>> resourceMutableLiveData = new MutableLiveData<>();
    private final AdminRepository adminRepository;


    @Inject
    public AdminAppointmentViewModel(EventBus eventBus,AdminRepository adminRepository) {
        super(eventBus);
        this.adminRepository= adminRepository;
        getData();
    }

    //get list appointment
    public  void getData(){
        resourceMutableLiveData.setValue(Resource.loading(null));
        compositeDisposable.add(adminRepository
                .getAllAppointment()
                .subscribe(listResource -> {
                    resourceMutableLiveData.setValue(listResource);
                }, error -> {
                    resourceMutableLiveData.setValue(Resource.error(error.getMessage(), null));
                })
        );
    }
    public MutableLiveData<Resource<List<AdminAppointment>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }

    public void acceptApointment(ImpactApointment impactApointment) {
        compositeDisposable.add(adminRepository
                .acceptAppointment(impactApointment)
                .subscribe(() -> {
                    eventBus.post(new ResponseAcceptApointmentServer("Success update"));
                }, error -> {
                    eventBus.post(new ResponseAcceptApointmentServer(error));
                })
        );
    }

    public void skipAppointment(ImpactApointment impactApointment){
        compositeDisposable.add(adminRepository
                .skipAppointment(impactApointment)
                .subscribe(() -> {
                    eventBus.post(new ResponseSkipApointmentServer("Success update"));
                }, error -> {
                    eventBus.post(new ResponseSkipApointmentServer(error));
                })
        );
    }

    public static class ResponseAcceptApointmentServer extends BaseMessage {
        public String message;
        public ResponseAcceptApointmentServer(Throwable errorMess) {
            super(errorMess);
        }
        public ResponseAcceptApointmentServer(String message){
            this.message = message;
        }
    }
    public static class ResponseSkipApointmentServer extends BaseMessage {
        public String message;
        public ResponseSkipApointmentServer(Throwable errorMess) {
            super(errorMess);
        }
        public ResponseSkipApointmentServer(String message){
            this.message = message;
        }
    }
}
