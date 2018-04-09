package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.db.RoomDb;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateModel;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateNextPageResult;
import com.mvvm.kien2111.mvvmapplication.data.remote.RateService;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate.RateWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 06/04/2018.
 */

public class RateRepository {
    private final AppExecutors appExecutors;
    private final RoomDb roomDb;
    private final RateService rateService;
    @Inject
    public RateRepository(AppExecutors appExecutors, RateService rateService, RoomDb roomDb){
        this.appExecutors = appExecutors;
        this.rateService = rateService;
        this.roomDb = roomDb;
    }

    public Flowable<Resource<List<RateModel>>> fetchPageRate(String iduser){
        return rateService.getRateList(iduser)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess(rateWrapper -> appExecutors.getDiskIO().execute(()->{
                        List<Integer> rateid = rateWrapper.getRatesId();
                        RateNextPageResult rateNextPageResult = new RateNextPageResult(rateWrapper.getQuery(),
                                rateid,
                                rateWrapper.getTotalcount(),
                                rateWrapper.getNextpage());
                        roomDb.beginTransaction();
                        try{
                            roomDb.rateDao().insert(rateWrapper.getLst_rate());
                            roomDb.rateDao().insert(rateNextPageResult);
                            roomDb.setTransactionSuccessful();
                        }finally {
                            roomDb.endTransaction();
                        }
                    }))
                .toFlowable()
                .flatMap(profileWrapper -> roomDb.rateDao()
                        .findNextPageResultFlowable(iduser)
                        .switchMap(result -> roomDb.rateDao().loadOrdered(result.idrates)))
                .map(Resource::success);
    }

    public Flowable<Resource<Boolean>> fetchNextPageRate(String iduser){
        return Flowable.create(emitter -> {
           FetchNextRatePageTask task = new FetchNextRatePageTask(emitter,rateService,roomDb,iduser);
            Completable.fromRunnable(task)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }, BackpressureStrategy.LATEST);
    }

    static class FetchNextRatePageTask implements Runnable{
        private final FlowableEmitter emitter;
        private final RateService rateService;
        private final String iduser;
        private final RoomDb roomDb;
        public FetchNextRatePageTask(FlowableEmitter flowableEmitter,RateService rateService,RoomDb roomDb,String iduser){
            this.emitter = flowableEmitter;
            this.rateService = rateService;
            this.roomDb = roomDb;
            this.iduser = iduser;
        }
        @Override
        public void run() {
            RateNextPageResult current = roomDb.rateDao().findRateNextPageResult(iduser);
            if(current==null){
                emitter.onNext(null);
                return;
            }
            final Integer next = current.next;
            if(next == null || next<0){
                emitter.onNext(Resource.success(true));
                return;
            }
            try{
                RateWrapper response = rateService.getNextPageRate(iduser,next).blockingSingle();
                if(response!=null){
                    List<Integer> ids = new ArrayList<>();
                    ids.addAll(current.idrates);
                    ids.addAll(response.getRatesId());
                    RateNextPageResult merged = new RateNextPageResult(iduser,
                            ids,
                            response.getTotalcount(),
                            response.getNextpage());
                    try{
                        roomDb.beginTransaction();
                        roomDb.rateDao().insert(merged);
                        roomDb.rateDao().insert(response.getLst_rate());
                        roomDb.setTransactionSuccessful();
                    }finally {
                        roomDb.endTransaction();
                    }
                    emitter.onNext(Resource.success(response.getNextpage()!=null));
                }else{
                    emitter.onNext(Resource.error("Can't load more rate",true));
                }

            }catch (Exception e){
                emitter.onNext(Resource.error(e.getMessage(),true));
            }finally {
                emitter.onComplete();
            }
        }
    }
}
