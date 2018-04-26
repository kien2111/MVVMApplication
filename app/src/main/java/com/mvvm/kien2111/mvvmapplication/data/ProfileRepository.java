package com.mvvm.kien2111.mvvmapplication.data;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.db.RoomDb;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileNextPageResult;
import com.mvvm.kien2111.mvvmapplication.data.remote.ProfileService;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.City;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;
import com.mvvm.kien2111.mvvmapplication.model.Profile;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.ProfileRequest;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.ProfileWrapper;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 18/03/2018.
 */

public class ProfileRepository{
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

    public Flowable<List<City>> getAllCity(){
        return roomDb.cityDao()
                .getAllCity()
                .cache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<District>> getAllDistrict(City city){
        return roomDb.districtDao()
                .getAllDistrict(city.getCityid())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Flowable<SearchResult> getSearchResult(String query){
        return service.getSearchResult(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<ProfileModel>> getProfile(ProfileRequest request){
        return service.getProfiles(request.query,request.filter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(profileWrapper -> {
                    appExecutors.getDiskIO().execute(()->{
                        List<String> profilesid = profileWrapper.getProfilesId();
                        ProfileNextPageResult profileNextPageResult = new ProfileNextPageResult(profileWrapper.getQuery(),
                                request.filter,
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
                            .findNextPageResultFlowable(request.query)
                            .switchMap(result -> {return roomDb.profileDao().loadOrdered(result.idprofiles);});
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
    public Flowable<Boolean> fetchNextPageProfile(ProfileRequest request){
        return Flowable.create(emitter -> {
            FetchNextPageTask fetchNextPageTask =
                    new FetchNextPageTask(service,request.query,request.filter,roomDb,emitter);
            Completable.fromRunnable(fetchNextPageTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        },BackpressureStrategy.LATEST);
    }


    static class FetchNextPageTask implements Runnable{
        private final ProfileService profileService;
        private final String query;
        private final RoomDb roomDb;
        private final String filter;
        private final FlowableEmitter emitter;
        public FetchNextPageTask(ProfileService profileService,String query,String filter,RoomDb roomDb,FlowableEmitter emitter){
            this.profileService = profileService;
            this.query = query;
            this.filter = filter;
            this.roomDb = roomDb;
            this.emitter = emitter;
        }

        @Override
        public void run() {
            ProfileNextPageResult current = roomDb.profileDao().findProfileNextPageResult(query);
            if(current.filter!=null&&!current.filter.equals(this.filter)){
                //change filter reset all
                try{
                    ProfileWrapper response = profileService
                            .getNextPageProfiles(query,0,filter).blockingSingle();
                    if(response != null){
                        List<String> ids = new ArrayList<>();
                        ids.addAll(response.getProfilesId());
                        ProfileNextPageResult merged = new ProfileNextPageResult(query,
                                filter,
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

            }else{
                if(current==null){
                    emitter.onNext(null);
                    return;
                }
                final Integer nextPage = current.next;
                if(nextPage ==null || nextPage<0){
                    emitter.onNext(false);
                    return;
                }
                try{
                    //do normal
                    ProfileWrapper response = profileService
                            .getNextPageProfiles(query,nextPage,filter).blockingSingle();
                    if(response != null){
                        List<String> ids = new ArrayList<>();
                        ids.addAll(current.idprofiles);
                        ids.addAll(response.getProfilesId());
                        ProfileNextPageResult merged = new ProfileNextPageResult(query,
                                filter,
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
