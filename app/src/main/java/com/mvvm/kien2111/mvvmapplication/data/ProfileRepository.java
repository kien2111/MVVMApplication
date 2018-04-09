package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.db.RoomDb;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileNextPageResult;
import com.mvvm.kien2111.mvvmapplication.data.remote.ProfileService;
import com.mvvm.kien2111.mvvmapplication.exception.ApiException;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.ProfileWrapper;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchResult;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by WhoAmI on 18/03/2018.
 */

public class ProfileRepository {
    private final AppExecutors appExecutors;
    private final ProfileService service;
    private final HashMap<String,ProfileWrapper> cached = new HashMap<>();
    private final RoomDb roomDb;
    @Inject
    public ProfileRepository(AppExecutors appExecutors,ProfileService service,RoomDb roomDb){
        this.appExecutors = appExecutors;
        this.service = service;
        this.roomDb = roomDb;
    }

    public Flowable<Resource<SearchResult>> getSearchResult(String query){
        return service.getSearchResult(query)
                .map(Resource::success)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Resource<List<ProfileModel>>> getProfile(String idcategory){
        return service.getProfiles(idcategory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(profileWrapper -> {
                    appExecutors.getDiskIO().execute(()->{
                        List<String> profilesid = profileWrapper.getProfilesId();
                        ProfileNextPageResult profileNextPageResult = new ProfileNextPageResult(profileWrapper.getQuery(),
                                profilesid,
                                profileWrapper.getTotalcount(),
                                profileWrapper.getNextpage());
                        roomDb.beginTransaction();
                        try{
                            roomDb.profileDao().insert(profileWrapper.getListprofile());
                            roomDb.profileDao().insert(profileNextPageResult);
                            roomDb.setTransactionSuccessful();
                        }finally {
                            roomDb.endTransaction();
                        }
                    });

                })
                .toFlowable()
                .flatMap(profileWrapper -> {
                    return roomDb.profileDao()
                            .findNextPageResultFlowable(idcategory)
                            .switchMap(result -> {return roomDb.profileDao().loadOrdered(result.idprofiles);});
                })
                .map(ProfileModelList -> {
                    return Resource.success(ProfileModelList);
                });
    }
    /*public LiveData<Resource<Boolean>> fetchNextPageHighRateProfile(String idcategory){


        Completable.fromRunnable(fetchNextPageTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        //appExecutors.getNetworkIO().execute(fetchNextPageTask);
        return fetchNextPageTask.getResourceMutableLiveData();
    }*/
    public Flowable<Resource<Boolean>> fetchNextPageProfile(String idcategory){
        return Flowable.create(emitter -> {
            FetchNextPageTask fetchNextPageTask =
                    new FetchNextPageTask(service,idcategory,roomDb,emitter);
            Completable.fromRunnable(fetchNextPageTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        },BackpressureStrategy.LATEST);
    }


    static class FetchNextPageTask implements Runnable{
        private final ProfileService profileService;
        private final String query;
        private final RoomDb roomDb;
        private final FlowableEmitter emitter;
        public FetchNextPageTask(ProfileService profileService,String query,RoomDb roomDb,FlowableEmitter emitter){
            this.profileService = profileService;
            this.query = query;
            this.roomDb = roomDb;
            this.emitter = emitter;
        }

        @Override
        public void run() {
            ProfileNextPageResult current = roomDb.profileDao().findProfileNextPageResult(query);
            if(current==null){
                emitter.onNext(null);
                return;
            }
            final Integer nextPage = current.next;
            if(nextPage ==null || nextPage<0){
                emitter.onNext(Resource.success(true));
                return;
            }
            try{
                ProfileWrapper response = profileService
                        .getNextPageProfiles(query,nextPage).blockingSingle();
                if(response != null){
                    List<String> ids = new ArrayList<>();
                    ids.addAll(current.idprofiles);
                    ids.addAll(response.getProfilesId());
                    ProfileNextPageResult merged = new ProfileNextPageResult(query,
                            ids,
                            response.getTotalcount(),
                            response.getNextpage());
                    try {
                        roomDb.beginTransaction();
                        roomDb.profileDao().insert(merged);
                        roomDb.profileDao().insert(response.getListprofile());
                        roomDb.setTransactionSuccessful();
                    }finally {
                        roomDb.endTransaction();
                    }
                    emitter.onNext(Resource.success(response.getNextpage()!=null));
                }else{
                    emitter.onNext(Resource.error("Can't load more profile",true));
                }
            }
            catch (Exception e){
                emitter.onNext(Resource.error(e.getMessage(),true));
            }finally {
                emitter.onComplete();
            }
            /*ProfileNextPageResult current = roomDb.profileDao().findProfileNextPageResult(query);
            if(current==null){
                resourceMutableLiveData.postValue(null);
                return;
            }
            final Integer nextPage = current.next;
            if(nextPage ==null){
                resourceMutableLiveData.postValue(Resource.success(true));
                return;
            }
            try{
                Response<HighRateProfileWrapper> response = profileService
                        .getNextPageHighRateProfiles(query,nextPage).execute();
                if(response.isSuccessful()){
                    List<String> ids = new ArrayList<>();
                    ids.addAll(current.idprofiles);
                    ids.addAll(response.body().getProfilesId());
                    ProfileNextPageResult merged = new ProfileNextPageResult(query,
                            ids,
                            response.body().getTotalcount(),
                            response.body().getNextpage());
                    try {
                        roomDb.beginTransaction();
                        roomDb.profileDao().insert(merged);
                        roomDb.profileDao().insert(response.body().getListprofile());
                        roomDb.setTransactionSuccessful();
                    }finally {
                        roomDb.endTransaction();
                    }
                resourceMutableLiveData.postValue(Resource.success(response.body().getNextpage()!=null));
                }else{
                    resourceMutableLiveData.postValue(Resource.error(response.errorBody().string(),true));
                }
            }
            catch (IOException e){
                resourceMutableLiveData.postValue(Resource.error(e.getMessage(),true));
            }*/
        }
    }
}
