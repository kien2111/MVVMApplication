package com.mvvm.kien2111.fastjob.data;

import com.mvvm.kien2111.fastjob.AppExecutors;
import com.mvvm.kien2111.fastjob.data.local.db.RoomDb;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileNextPageResult;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.fastjob.data.remote.ProfileService;
import com.mvvm.kien2111.fastjob.data.local.db.entity.City;
import com.mvvm.kien2111.fastjob.data.local.db.entity.District;
import com.mvvm.kien2111.fastjob.data.remote.model.ApprovePublishRequest;
import com.mvvm.kien2111.fastjob.data.remote.model.LoginResponse;
import com.mvvm.kien2111.fastjob.model.Pakage_Upgrade;
import com.mvvm.kien2111.fastjob.model.Priority;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.ProfileRequest;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.ProfileWrapper;
import com.mvvm.kien2111.fastjob.ui.universal.search.SearchResult;
import com.mvvm.kien2111.fastjob.ui.upgrade.common.RequestUpgradeModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 18/03/2018.
 */

public class ProfileRepository{
    private final AppExecutors appExecutors;
    private final ProfileService service;
    private final RoomDb roomDb;
    private final PreferenceHelper preferenceHelper;
    @Inject
    public ProfileRepository(AppExecutors appExecutors,
                             PreferenceHelper preferenceHelper,
                             ProfileService service,
                             RoomDb roomDb){
        this.appExecutors = appExecutors;
        this.preferenceHelper = preferenceHelper;
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

    public Flowable<List<Pakage_Upgrade>> getListPakageUpgrade(){
        return service.getListPakageUpgrade()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
    }

    public Single<ProfileModel> fetchDetaiProfileWithId(final String idprofile){
        return service.fetchDetaiProfileWithId(idprofile)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<RequestUpgradeModel> getLastestOnProcessRequestUpgradeProfile(final String iduser){
        return service.getLastestOnProcessRequestUpgradeProfile(iduser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public int getSaveCitiesPosition(){
        return preferenceHelper.getCitiesPositionPick();
    }

    public void saveCitiesPosition(int position){
        preferenceHelper.saveCitiesPositionPick(position);
    }

    public int getSaveDistrictsPosition(){
        return preferenceHelper.getDistrictPositionPick();
    }

    public void saveDistrictsPosition(int position){
        preferenceHelper.saveDistrictPositionPick(position);
    }

    public Completable upgradeProfile(final RequestUpgradeModel requestUpdateModel){
        return service.doTaskUpgradeProfile(requestUpdateModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable requestTaskPublishProfile(final ApprovePublishRequest approvePublishRequest){
        return service
                .doTaskPublishProfile(approvePublishRequest)
                .doOnComplete(() -> {
                    LoginResponse loginResponse = preferenceHelper.getUserData();
                    loginResponse.getUser().getProfile().setApprove_publish(approvePublishRequest.getApprove_publish());
                    preferenceHelper.setUserData(loginResponse);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<ProfileModel>> getProfile(ProfileRequest request){
        return service.getProfiles(request.query,
                request.distid,
                request.cityid,
                request.salaryFrom,
                request.salaryTo,
                request.priority.getType())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(profileWrapper -> {
                    appExecutors.getDiskIO().execute(()->{
                        List<String> profilesid = profileWrapper.getProfilesId();
                        ProfileNextPageResult profileNextPageResult = new ProfileNextPageResult(
                                profileWrapper.getQuery(),
                                request.distid,
                                request.cityid,
                                request.salaryFrom,
                                request.salaryTo,
                                request.priority,
                                profilesid,
                                profileWrapper.getTotalcount(),
                                profileWrapper.getNextpage());
                        roomDb.beginTransaction();
                        try{
                            roomDb.profileDao().insert(profileWrapper.getListprofile());
                            roomDb.profileDao().insert(profileNextPageResult);
                            roomDb.setTransactionSuccessful();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            roomDb.endTransaction();
                        }
                    });

                })
                .flatMap(profileWrapper -> roomDb.profileDao()
                        .findNextPageResultFlowable(request.query)
                        .switchMap(result -> roomDb.profileDao().loadOrdered(result.idprofiles)));
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
        try{
            return Flowable.create(emitter -> {
                FetchNextPageTask fetchNextPageTask =
                        new FetchNextPageTask(service,request.query,
                                request.distid,
                                request.cityid,
                                request.salaryFrom,
                                request.salaryTo,
                                request.priority,
                                roomDb,emitter);
                Completable.fromRunnable(fetchNextPageTask)
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            },BackpressureStrategy.LATEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Flowable.just(false);
    }

    public Completable deleteLastestUpgradeRequest(String profile_id) {
        return service.deleteLastestUpgradeRequest(profile_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    static class FetchNextPageTask implements Runnable{
        private final ProfileService profileService;
        private final String query;
        private final RoomDb roomDb;
        private final String distid;
        private final String cityid;
        private final Double salaryFrom;
        private final Double salaryTo;
        private final Priority priority;
        private final FlowableEmitter emitter;
        public FetchNextPageTask(ProfileService profileService,
                                 String query,
                                 String distid,
                                 String cityid,
                                 Double salaryFrom,
                                 Double salaryTo,
                                 Priority priority,
                                 RoomDb roomDb,
                                 FlowableEmitter emitter){
            this.profileService = profileService;
            this.query = query;
            this.distid = distid;
            this.cityid = cityid;
            this.salaryFrom = salaryFrom;
            this.salaryTo = salaryTo;
            this.priority = priority;
            this.roomDb = roomDb;
            this.emitter = emitter;
        }

        @Override
        public void run() {
            ProfileNextPageResult current = roomDb.profileDao().findProfileNextPageResult(query);
            /*if(current.filter!=null&&!current.filter.equals(this.filter)){
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

            }*/
            if(current==null){
                emitter.onNext(null);
                return;
            }
            Integer nextPage = current.next;
            if(nextPage ==null || nextPage<0){
                emitter.onNext(false);
                return;
            }
            if(current.cityid!=null && !current.cityid.equals(this.cityid)){
                nextPage = 1;
            }
            if(current.distid!=null && !current.distid.equals(this.distid)){
                nextPage = 1;
            }
            if((current.priority!=null && priority!=null)&& !(current.priority.getType()== this.priority.getType())){
                nextPage = 1;
            }
            if((current.salaryFrom!=null && current.salaryTo!=null)&& (salaryFrom!=0.0 && salaryTo!=0.0) &&(current.salaryFrom!=0.0 && current.salaryTo!=0.0) && (!(current.salaryFrom.equals(this.salaryFrom)) || !(current.salaryTo.equals(this.salaryTo)))){
                nextPage = 1;
            }
            try{
                //do normal
                ProfileWrapper response = profileService
                        .getNextPageProfiles(query,nextPage,distid,cityid,salaryFrom,salaryTo,priority.getType()).blockingSingle();
                if(response != null){
                    List<String> ids = new ArrayList<>();
                    ids.addAll(current.idprofiles);
                    ids.addAll(response.getProfilesId());
                    ProfileNextPageResult merged = new ProfileNextPageResult(query,
                            distid,
                            cityid,
                            salaryFrom,
                            salaryTo,
                            priority,
                            ids,
                            response.getTotalcount(),
                            response.getNextpage());
                    try {
                        roomDb.beginTransaction();
                        roomDb.profileDao().insert(merged);
                        roomDb.profileDao().insert(response.getListprofile());
                        roomDb.setTransactionSuccessful();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        roomDb.endTransaction();
                    }
                    emitter.onNext(response.getNextpage()!=null);
                }else{
                    emitter.onNext(true);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                emitter.onNext(true);
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
