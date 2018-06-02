package com.mvvm.kien2111.fastjob.data;

import android.graphics.Bitmap;

import com.mvvm.kien2111.fastjob.AppExecutors;
import com.mvvm.kien2111.fastjob.data.local.db.dao.CategoryDao;
import com.mvvm.kien2111.fastjob.data.local.db.dao.RecentSearchDao;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.fastjob.data.remote.UserService;
import com.mvvm.kien2111.fastjob.model.Resource;
import com.mvvm.kien2111.fastjob.util.ImageUtil;
import com.mvvm.kien2111.fastjob.util.LimitFetch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
