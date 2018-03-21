package com.mvvm.kien2111.mvvmapplication.ui.universal.common;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.DetailCategoryFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.notification.NotificationFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.user.UserFragment;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    NavigationController(UniversalActivity activity){
        fragmentManager = activity.getSupportFragmentManager();
        this.containerId = R.id.container;
    }
    public void navigateToSearch(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = (SearchFragment) fragmentManager.findFragmentByTag("search");
        if(searchFragment==null){
            searchFragment = new SearchFragment();
        }
        transaction.replace(containerId,searchFragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToUser(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        UserFragment userFragment = (UserFragment) fragmentManager.findFragmentByTag("user");
        if(userFragment==null){
            userFragment = new UserFragment();
        }
        transaction.replace(containerId,userFragment);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToFeed(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FeedFragment feedFragment = (FeedFragment) fragmentManager.findFragmentByTag("feed");
        if(feedFragment==null){
            feedFragment = new FeedFragment();
        }
        transaction.replace(containerId,feedFragment,"feed").addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToFavouriteProfile(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FavouriteProfileFragment favouriteProfileFragment = (FavouriteProfileFragment) fragmentManager.findFragmentByTag("favouriteprofile");
        if(favouriteProfileFragment==null){
            favouriteProfileFragment = new FavouriteProfileFragment();
        }
        transaction.replace(containerId,favouriteProfileFragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToNotification(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        NotificationFragment notificationFragment = (NotificationFragment) fragmentManager.findFragmentByTag("notification");
        if(notificationFragment==null){
            notificationFragment = new NotificationFragment();
        }
        transaction.replace(containerId,notificationFragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToDetailCategory(Category category){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DetailCategoryFragment detailCategoryFragment = (DetailCategoryFragment)fragmentManager.findFragmentByTag(category.getNamecategory());
        if(detailCategoryFragment==null){
            detailCategoryFragment = new DetailCategoryFragment();
        }
        transaction.replace(containerId,detailCategoryFragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}
