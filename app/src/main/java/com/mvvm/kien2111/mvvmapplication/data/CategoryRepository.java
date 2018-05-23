package com.mvvm.kien2111.mvvmapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.mvvmapplication.AppExecutors;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.CategoryDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.RecentSearchDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.dao.UserDao;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.mvvmapplication.data.remote.UserService;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.retrofit.Envelope;
import com.mvvm.kien2111.mvvmapplication.util.ImageUtil;
import com.mvvm.kien2111.mvvmapplication.util.LimitFetch;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 06/03/2018.
 */

public class CategoryRepository {
    private final UserService userService;
    private final CategoryDao categoryDao;
    private final RecentSearchDao recentSearchDao;
    private AppExecutors appExecutors;
    private LimitFetch<String> limitFetch = new LimitFetch<>(2, TimeUnit.MINUTES);
    @Inject
    public CategoryRepository(UserService userService,
                              RecentSearchDao recentSearchDao,
                              CategoryDao categoryDao,
                              AppExecutors appExecutors){
        this.userService=userService;
        this.categoryDao=categoryDao;
        this.recentSearchDao = recentSearchDao;
        this.appExecutors = appExecutors;
    }
    public Single<Resource<List<Category>>> getListCategory(){
        return userService
                .getListCategory()
                .map(Resource::success)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable saveQuery(final RecentSearch recentSearch, Bitmap bitmap){
        return Completable.fromRunnable(() -> {
            try{
                recentSearch.setImagePath(ImageUtil.saveBitmap(bitmap,recentSearch.getIdquery()));
                recentSearchDao.insert(recentSearch);
            }catch (Exception e){
                e.printStackTrace();
            }

        }).subscribeOn(Schedulers.io());
    }

    public Single<Category> fetchCategoryWithId(final String id){
        return categoryDao.fetchCategoryId(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<RecentSearch>> fetchRecentSearch(){
        return recentSearchDao.fetchRecentSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
