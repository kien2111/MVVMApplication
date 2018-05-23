package com.mvvm.kien2111.mvvmapplication.ui.createappointment;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.AppointmentRepository;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.mvvmapplication.model.Deposit_Fee;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 27/04/2018.
 */

public class CreateAppointmentViewModel extends BaseViewModel {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<Deposit_Fee> deposit_feeMutableLiveData = new MutableLiveData<>();
    private final PreferenceLiveData preferenceLiveData;
    @Inject
    public CreateAppointmentViewModel(EventBus eventBus,
                                      PreferenceLiveData preferenceLiveData,
                                      AppointmentRepository repository,
                                      UserRepository userRepository) {
        super(eventBus);
        this.appointmentRepository = repository;
        this.userRepository = userRepository;
        this.preferenceLiveData = preferenceLiveData;
        compositeDisposable.add(appointmentRepository
                .getDepositFee()
                .subscribe(deposit_feeMutableLiveData::setValue, throwable -> {

                }));
    }
    public void createAppointment(final AppointmentModel appointmentModel){
        compositeDisposable.add(appointmentRepository
                .createAppointment(appointmentModel)
                .subscribe(() -> {
                    eventBus.post(BaseMessage.success("Success Create Appointment"));
                },throwable -> {
                    eventBus.post(BaseMessage.error(throwable));
                }));
    }

    public MutableLiveData<Deposit_Fee> getDeposit_feeMutableLiveData() {
        return deposit_feeMutableLiveData;
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }
}
