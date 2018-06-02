package com.mvvm.kien2111.fastjob.ui.listappointment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableField;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.AppointmentRepository;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.fastjob.data.remote.model.AppointmentTaskRequest;
import com.mvvm.kien2111.fastjob.model.BaseNextPageHandler;
import com.mvvm.kien2111.fastjob.model.LoadMoreState;
import com.mvvm.kien2111.fastjob.model.Option;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.fastjob.util.AbsentLiveData;
import com.mvvm.kien2111.fastjob.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class ListAppointmentViewModel extends BaseViewModel {
    private final ObservableField<String> headerName = new ObservableField<>();
    private final LiveData<Resource<List<AppointmentModel>>> resourceLiveData;
    private final AppointmentRepository appointmentRepository;
    private final BaseNextPageHandler baseNextPageHandler;
    private final BaseNextPageHandler baseHistoryNextPageHandler;
    private final PreferenceLiveData preferenceLiveData;
    private final UserRepository userRepository;
    private final MutableLiveData<PickOption> roleOptionMutableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<List<AppointmentModel>>> resourceHistoryLiveData;
    @Inject
    public ListAppointmentViewModel(EventBus eventBus,
                                    UserRepository userRepository,
                                    PreferenceLiveData preferenceLiveData
            ,AppointmentRepository appointmentRepository){
        super(eventBus);
        this.userRepository = userRepository;
        this.preferenceLiveData = preferenceLiveData;
        this.appointmentRepository = appointmentRepository;
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
        this.baseHistoryNextPageHandler = new BaseNextPageHandler() {
            @Override
            public LiveData<Resource<Boolean>> bindNextPageCall(Object... param) {
                return MyLiveDataReactiveStream.fromPublisher(appointmentRepository.fetchNextPageAppointment((ListAppointmentViewModel.PickOption)param[0]));
            }
        };
        resourceHistoryLiveData = Transformations.switchMap(roleOptionMutableLiveData,input -> {
            if(input==null){
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

    public void acceptAppointment(AppointmentTaskRequest appointmentTaskRequest){
        compositeDisposable.add(appointmentRepository.acceptAppointment(appointmentTaskRequest)
                .subscribe(() -> {
                    eventBus.post(BaseMessage.success("Accept successfull"));
                },throwable -> {
                    eventBus.post(BaseMessage.error(throwable));
                }));
    }

    public void declineAppointment(AppointmentTaskRequest appointmentTaskRequest){
        compositeDisposable.add(appointmentRepository.declineAppointment(appointmentTaskRequest)
                .subscribe(() -> {
                    eventBus.post(BaseMessage.success("decline successfull"));
                },throwable -> {
                    eventBus.post(BaseMessage.error(throwable));
                }));
    }

    public LiveData<Resource<List<AppointmentModel>>> getResourceHistoryLiveData() {
        return resourceHistoryLiveData;
    }

    public LiveData<LoadMoreState> getLoadMoreHistoryStateLiveData(){
        return baseHistoryNextPageHandler.getLoadMoreStateMutableLiveData();
    }

    public MutableLiveData<ListAppointmentViewModel.PickOption> getPickOptionMutableLiveData() {
        return roleOptionMutableLiveData;
    }

    public LiveData<Resource<List<AppointmentModel>>> getOnProgressResourceLiveData() {
        return resourceLiveData;
    }
    public LiveData<LoadMoreState> getLoadMoreOnProgressStateLiveData(){
        return baseNextPageHandler.getLoadMoreStateMutableLiveData();
    }

    public void loadNextPage() {
        ListAppointmentViewModel.PickOption pickOption = roleOptionMutableLiveData.getValue();
        if(pickOption == null || pickOption.getIduser()==null || pickOption.getOption()==null){
            return;
        }
        baseNextPageHandler.queryNextPage(pickOption);
    }

    public void loadHistoryNextPage(){
        ListAppointmentViewModel.PickOption pickOption = roleOptionMutableLiveData.getValue();
        if(pickOption == null || pickOption.getIduser()==null || pickOption.getOption()==null){
            return;
        }
        baseHistoryNextPageHandler.queryNextPage(pickOption);
    }

    public MutableLiveData<PickOption> getRoleOptionMutableLiveData() {
        return roleOptionMutableLiveData;
    }

    public static class PickOption{

        public static final int ON_PROGRESS = 1;
        public static final int HISTORY = 2;

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof PickOption))return false;
            if(option.getType()==((PickOption) obj).option.getType() &&
                    iduser.equals(((PickOption) obj).iduser))return true;
            return false;
        }

        Option option;
        String iduser;
        int historyOrOnProgress;

        public int getHistoryOrOnProgress() {
            return historyOrOnProgress;
        }

        public void setHistoryOrOnProgress(int historyOrOnProgress) {
            this.historyOrOnProgress = historyOrOnProgress;
        }

        public PickOption(Option option, String iduser,int historyOrOnProgress) {
            this.option = option;
            this.iduser = iduser;
            this.historyOrOnProgress = historyOrOnProgress;
        }

        public Option getOption() {
            return option;
        }

        public void setOption(Option option) {
            this.option = option;
        }

        public String getIduser() {
            return iduser;
        }

        public void setIduser(String iduser) {
            this.iduser = iduser;
        }


    }

}
