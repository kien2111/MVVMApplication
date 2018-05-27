package com.mvvm.kien2111.fastjob.data.remote;

import com.mvvm.kien2111.fastjob.data.remote.model.RateRequest;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.rate.RateWrapper;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by WhoAmI on 06/04/2018.
 */

public interface RateService {

    @GET("/Rates/getlistrate")
    Flowable<RateWrapper> getNextPageRate(@Query("iduser") String iduser
            , @Query("page") int page);

    @GET("/Rates/getlistrate")
    Single<RateWrapper> getRateList(@Query("iduser")String iduser);

    @POST("/Rates/ratefreelancer")
    Completable doRate(@Body RateRequest rateRequest);
}
