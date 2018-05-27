package com.mvvm.kien2111.fastjob.data.remote;

import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.data.remote.model.ApprovePublishRequest;
import com.mvvm.kien2111.fastjob.model.Pakage_Upgrade;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.ProfileWrapper;
import com.mvvm.kien2111.fastjob.ui.universal.search.SearchResult;
import com.mvvm.kien2111.fastjob.ui.upgrade.common.RequestUpgradeModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by WhoAmI on 18/03/2018.
 */

public interface ProfileService {
    @GET("/Profiles/getprofile")
    Flowable<ProfileWrapper> getNextPageProfiles(@Query("idcategory") String id,
                                                 @Query("page") int page,
                                                 @Query("distid") String distid,
                                                 @Query("cityid") String cityid,
                                                 @Query("salaryFrom") Double salaryfrom,
                                                 @Query("salaryTo")Double salaryTo,
                                                 @Query("level")int priority);
    @GET("/Profiles/getprofile")
    Flowable<ProfileWrapper> getProfiles(@Query("idcategory") String id,
                                         @Query("distid") String distid,
                                         @Query("cityid") String cityid,
                                         @Query("salaryFrom") Double salaryfrom,
                                         @Query("salaryTo")Double salaryTo,
                                         @Query("level")int priority);

    @GET("/Profiles/search")
    Flowable<SearchResult> getSearchResult(@Query("query") String query);

    @PUT("/Profiles/dotaskpublishprofile")
    Completable doTaskPublishProfile(@Body ApprovePublishRequest approvePublishRequest);

    @POST("/Profiles/upgradeprofile")
    Completable doTaskUpgradeProfile(@Body RequestUpgradeModel requestUpdateModel);

    @GET("/Profiles/getlastestrequestupgrade")
    Flowable<RequestUpgradeModel> getLastestOnProcessRequestUpgradeProfile(@Query("iduser") String iduser);

    @GET("/Profiles/getallpakageupgrade")
    Flowable<List<Pakage_Upgrade>> getListPakageUpgrade();

    @GET("/Profiles/fetchdetaiprofilewithid")
    Single<ProfileModel> fetchDetaiProfileWithId(@Query("idprofile") String idprofile);

    @DELETE("/Profiles/deletelastestupgraderequest")
    Completable deleteLastestUpgradeRequest(@Query("profile_id") String idprofile);
}
