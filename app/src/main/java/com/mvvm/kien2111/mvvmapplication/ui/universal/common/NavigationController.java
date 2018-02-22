package com.mvvm.kien2111.mvvmapplication.ui.universal.common;

import android.support.v4.app.FragmentManager;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
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
    public NavigationController(UniversalActivity activity){
        fragmentManager = activity.getSupportFragmentManager();
        this.containerId = R.id.container;
    }
    public void navigateToSearch(){
        SearchFragment searchFragment = new SearchFragment();
        fragmentManager.beginTransaction().replace(containerId,searchFragment).commitAllowingStateLoss();
    }
    public void navigateToUser(){
        UserFragment userFragment = new UserFragment();
        fragmentManager.beginTransaction().replace(containerId,userFragment).commitAllowingStateLoss();
    }
    public void navigateToFeed(){
        FeedFragment feedFragment = new FeedFragment();
        fragmentManager.beginTransaction().replace(containerId,feedFragment).commitAllowingStateLoss();
    }
    public void navigateToFavouriteProfile(){
        FavouriteProfileFragment favouriteProfileFragment = new FavouriteProfileFragment();
        fragmentManager.beginTransaction().replace(containerId,favouriteProfileFragment).commitAllowingStateLoss();
    }
    public void navigateToNotification(){
        NotificationFragment notificationFragment = new NotificationFragment();
        fragmentManager.beginTransaction().replace(containerId,notificationFragment).commitAllowingStateLoss();
    }
}
