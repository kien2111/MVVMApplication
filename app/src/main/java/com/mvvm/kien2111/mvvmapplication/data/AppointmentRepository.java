package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.WorkerThread;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.db.RoomDb;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.AppointmentNextPageResult;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileNextPageResult;
import com.mvvm.kien2111.mvvmapplication.data.remote.AppointmentService;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 17/04/2018.
 */

public class AppointmentRepository {
    private final AppExecutors appExecutors;
    private final AppointmentService appointmentService;
    private final RoomDb roomDb;
    @Inject
    public AppointmentRepository(AppExecutors appExecutors, AppointmentService appointmentService,RoomDb roomDb){
        this.appExecutors = appExecutors;
        this.appointmentService = appointmentService;
        this.roomDb = roomDb;
    }

    public Flowable<List<AppointmentModel>> fetchPageAppointment(ListAppointmentViewModel.PickOption pickOption){
        return appointmentService
                .getListAppointment(
                        pickOption.getOption().getType()
                        ,pickOption.getIduser())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(appointmentWrapper -> appExecutors.getDiskIO().execute(()-> doOnNext(appointmentWrapper)))
                .toFlowable()
                .flatMap(appointmentWrapper -> roomDb.appointmentDao()
                        .findNextPageAppointmentResultFlowable(
                                pickOption.getOption().getType()
                                ,pickOption.getIduser())
                        .switchMap(result -> roomDb.appointmentDao().loadOrdered(result.idappointments)));
    }

    @WorkerThread
    private void doOnNext(AppointmentWrapper appointmentWrapper){
        List<Integer> appointmentids = appointmentWrapper.getIntergerAppointmentsFromApi();
        AppointmentNextPageResult appointmentNextPageResult = new AppointmentNextPageResult(
                appointmentWrapper.getOption(),
                appointmentWrapper.getIduser(),
                appointmentids,
                appointmentWrapper.getTotalcount(),
                appointmentWrapper.getNextpage());
        roomDb.beginTransaction();
        try{
            roomDb.appointmentDao().insert(appointmentWrapper.getListAppointment());
            roomDb.appointmentDao().insert(appointmentNextPageResult);
            roomDb.setTransactionSuccessful();
        }finally {
            roomDb.endTransaction();
        }
    }

    public Flowable<Boolean> fetchNextPageAppointment(final ListAppointmentViewModel.PickOption pickOption){
        return Flowable.create(emitter -> {
            FetchNextPageTask fetchNextPageTask =
                    new FetchNextPageTask(appointmentService,pickOption,roomDb,emitter);
            Completable.fromRunnable(fetchNextPageTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }, BackpressureStrategy.LATEST);
    }

    static class FetchNextPageTask implements Runnable{
        FlowableEmitter emitter;
        RoomDb roomDb;
        AppointmentService service;
        ListAppointmentViewModel.PickOption pickOption;
        public FetchNextPageTask(AppointmentService service,
                                 ListAppointmentViewModel.PickOption pickOption,
                                 RoomDb roomDb,
                                 FlowableEmitter emitter){
            this.emitter = emitter;
            this.service = service;
            this.roomDb = roomDb;
            this.pickOption = pickOption;
        }
        @Override
        public void run() {
            AppointmentNextPageResult current = roomDb.appointmentDao()
                    .findAppointmentNextPageResult(pickOption.getOption().getType(),pickOption.getIduser());
            if(current==null){
                emitter.onNext(null);
                return;
            }
            final Integer nextPage = current.next;
            if(nextPage ==null || nextPage<0){
                emitter.onNext(true);
                return;
            }
            try{
                AppointmentWrapper response = service
                        .getNextPageAppointment(
                                pickOption.getOption().getType(),
                                pickOption.getIduser(),nextPage)
                        .blockingSingle();
                if(response != null){
                    List<Integer> ids = new ArrayList<>();
                    ids.addAll(current.idappointments);
                    ids.addAll(response.getIntergerAppointmentsFromApi());
                    AppointmentNextPageResult merged = new AppointmentNextPageResult(pickOption.getOption().getType(),
                            pickOption.getIduser(),
                            ids,
                            response.getTotalcount(),
                            response.getNextpage());
                    try {
                        roomDb.beginTransaction();
                        roomDb.appointmentDao().insert(merged);
                        roomDb.appointmentDao().insert(response.getListAppointment());
                        roomDb.setTransactionSuccessful();
                    }finally {
                        roomDb.endTransaction();
                    }
                    emitter.onNext(response.getNextpage()!=null);
                }else{
                    emitter.onNext(true);
                }
            }
            catch (Exception e){
                emitter.onNext(true);
            }finally {
                emitter.onComplete();
            }
        }
    }

}
