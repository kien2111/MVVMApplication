package com.mvvm.kien2111.mvvmapplication.data.remote;

import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.ProfileWrapper;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.ProfileWrapper;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchResult;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by WhoAmI on 18/03/2018.
 */

public interface ProfileService {
    @GET("/Profiles/getprofile")
    Flowable<ProfileWrapper> getNextPageProfiles(@Query("idcategory") String id
            , @Query("page") int page,@Query("filter")String filter);
    @GET("/Profiles/getprofile")
    Single<ProfileWrapper> getProfiles(@Query("idcategory") String id,@Query("filter") String filter);

    @GET("/Profiles/search")
    Flowable<SearchResult> getSearchResult(@Query("query") String query);
}
