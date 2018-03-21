package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.db.RoomDb;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.ProfileDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileNextPageResult;
import com.mvvm.kien2111.mvvmapplication.data.remote.ProfileService;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.HighRateProfileModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate.HighRateProfileWrapper;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by WhoAmI on 18/03/2018.
 */

public class ProfileRepository {
    private final AppExecutors appExecutors;
    private final ProfileService service;
    private final HashMap<String,HighRateProfileWrapper> cached = new HashMap<>();
    private final RoomDb roomDb;
    @Inject
    public ProfileRepository(AppExecutors appExecutors,ProfileService service,RoomDb roomDb){
        this.appExecutors = appExecutors;
        this.service = service;
        this.roomDb = roomDb;
    }

    public Flowable<Resource<List<HighRateProfileModel>>> getHighRateProfile(String idcategory){
        return service.getHighRateProfiles(idcategory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(highRateProfileWrapper -> {
                    List<String> profilesid = highRateProfileWrapper.getProfilesId();
                    ProfileNextPageResult profileNextPageResult = new ProfileNextPageResult(highRateProfileWrapper.getQuery(),
                            profilesid,
                            highRateProfileWrapper.getTotalcount(),
                            highRateProfileWrapper.getNextpage());
                    roomDb.beginTransaction();
                    try{
                        roomDb.profileDao().insert(highRateProfileWrapper.getListprofile());
                        roomDb.profileDao().insert(profileNextPageResult);
                        roomDb.setTransactionSuccessful();
                    }finally {
                        roomDb.endTransaction();
                    }
                })
                .toFlowable()
                .flatMap(highRateProfileWrapper -> {
                    return roomDb.profileDao()
                            .findNextPageResultFlowable(idcategory)
                            .switchMap(result -> {return roomDb.profileDao().loadOrdered(result.idprofiles);});
                }).map(highRateProfileModelList -> {
                    return Resource.success(highRateProfileModelList);
                });
    }
    public LiveData<Resource<Boolean>> fetchNextPageHighRateProfile(String idcategory){
        FetchNextPageTask fetchNextPageTask =
                new FetchNextPageTask(service,idcategory,roomDb);
        appExecutors.getNetworkIO().execute(fetchNextPageTask);
        return fetchNextPageTask.getResourceMutableLiveData();
    }

    private List<HighRateProfileModel> parseToResource(HighRateProfileWrapper highRateProfileWrapper){
        return highRateProfileWrapper.getListprofile();
    }
    static class FetchNextPageTask implements Runnable{
        private final ProfileService profileService;
        private final MutableLiveData<Resource<Boolean>> resourceMutableLiveData = new MutableLiveData<>();
        private final String query;
        private final RoomDb roomDb;
        public FetchNextPageTask(ProfileService profileService,String query,RoomDb roomDb){
            this.profileService = profileService;
            this.query = query;
            this.roomDb = roomDb;
        }

        public LiveData<Resource<Boolean>> getResourceMutableLiveData() {
            return resourceMutableLiveData;
        }

        @Override
        public void run() {
            ProfileNextPageResult current = roomDb.profileDao().findProfileNextPageResult(query);
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
            }
        }
    }
}
