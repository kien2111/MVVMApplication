package com.mvvm.kien2111.fastjob.ui.listappointment.historyappointment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableField;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AppointmentRepository;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.fastjob.model.BaseNextPageHandler;
import com.mvvm.kien2111.fastjob.model.LoadMoreState;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.fastjob.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.fastjob.util.AbsentLiveData;
import com.mvvm.kien2111.fastjob.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class HistoryAppointmentViewModel extends BaseViewModel{
    private final ObservableField<String> headerName = new ObservableField<>();
    private final LiveData<Resource<List<AppointmentModel>>> resourceLiveData;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PreferenceLiveData preferenceLiveData;
    private final BaseNextPageHandler baseNextPageHandler;
    private final MutableLiveData<ListAppointmentViewModel.PickOption> roleOptionMutableLiveData = new MutableLiveData<>();

    @Inject
    public HistoryAppointmentViewModel(EventBus eventBus,
                                       PreferenceLiveData preferenceLiveData,
                                       UserRepository userRepository,
                                       AppointmentRepository appointmentRepository) {
        super(eventBus);
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.preferenceLiveData = preferenceLiveData;
        this.preferenceLiveData.setUser(userRepository.getUserData().getUser());
        this.baseNextPageHandler = new BaseNextPageHandler() {
            @Override
            public LiveData<Resource<Boolean>> bindNextPageCall(Object... param) {
                return MyLiveDataReactiveStream
                        .fromPublisher(appointmentRepository.fetchNextPageAppointment((ListAppointmentViewModel.PickOption)param[0]));
            }
        };
        resourceLiveData = Transformations.switchMap(roleOptionMutableLiveData, input->{
            if(roleOptionMutableLiveData==null){
                return AbsentLiveData.create();
            }else{
                return MyLiveDataReactiveStream
                        .fromPublisher(appointmentRepository.fetchPageAppointment(input));
            }
        });
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }

    public ObservableField<String> getHeaderName() {
        return headerName;
    }

    public void pickOption(ListAppointmentViewModel.PickOption roleOption){
        if(roleOption!=null && roleOption!= roleOptionMutableLiveData.getValue()){
            baseNextPageHandler.reload();
            roleOptionMutableLiveData.setValue(roleOption);
        }
    }

    public MutableLiveData<ListAppointmentViewModel.PickOption> getPickOptionMutableLiveData() {
        return roleOptionMutableLiveData;
    }

    public LiveData<Resource<List<AppointmentModel>>> getResourceLiveData() {
        return resourceLiveData;
    }
    public LiveData<LoadMoreState> getLoadMoreStateLiveData(){
        return baseNextPageHandler.getLoadMoreStateMutableLiveData();
    }

    public void loadNextPage() {
        ListAppointmentViewModel.PickOption pickOption = roleOptionMutableLiveData.getValue();
        if(pickOption == null || pickOption.getIduser()!=null || pickOption.getOption()!=null){
            return;
        }
        baseNextPageHandler.queryNextPage(pickOption);
    }

}
