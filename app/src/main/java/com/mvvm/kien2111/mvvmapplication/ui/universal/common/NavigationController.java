package com.mvvm.kien2111.mvvmapplication.ui.universal.common;

import android.os.Build;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.DetailCategoryFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.DetailProfileFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.FeedFragment;
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
        transaction.setPrimaryNavigationFragment(searchFragment);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToUser(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        UserFragment userFragment = (UserFragment) fragmentManager.findFragmentByTag("user");
        if(userFragment==null){
            userFragment = new UserFragment();
        }
        transaction.replace(containerId,userFragment);
        transaction.setPrimaryNavigationFragment(userFragment);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToFeed(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment feedFragment = fragmentManager.findFragmentByTag("feed");
        if(!(feedFragment != null && feedFragment instanceof FeedFragment)){
            feedFragment = new FeedFragment();
        }
        transaction.replace(containerId,feedFragment,"feed").addToBackStack("feed");
        transaction.commitAllowingStateLoss();
        /*if(fragmentManager.getPrimaryNavigationFragment() instanceof DetailCategoryFragment
                    && ((DetailCategoryFragment) fragmentManager.getPrimaryNavigationFragment()).getCurrentCategory()!=null){
            //do nothing
        }else{
            FeedFragment feedFragment = new FeedFragment();
            transaction.replace(containerId,feedFragment,"feed").addToBackStack(null);
            transaction.setPrimaryNavigationFragment(feedFragment);
            transaction.commitAllowingStateLoss();
        }*/
    }

    public void navigateToDetailProfile(ProfileModel profileModel, ImageView imageView){
        if(fragmentManager.getPrimaryNavigationFragment() instanceof DetailProfileFragment
                && ((DetailProfileFragment)fragmentManager.getPrimaryNavigationFragment()).getCurrentProfile()!=null){
            //do nothing
        }else{
            DetailProfileFragment detailProfileFragment = DetailProfileFragment.newInstance(profileModel);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addSharedElement(imageView, ViewCompat.getTransitionName(imageView));
            transaction.replace(containerId,detailProfileFragment,"detailprofile "+profileModel.getName())
                    .addToBackStack("detailprofile "+profileModel.getName()+" "+System.currentTimeMillis());

            transaction.setPrimaryNavigationFragment(detailProfileFragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public void navigateToDetailCategory(Category category){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_right,R.animator.slide_out_left,R.animator.slide_in_left,R.animator.slide_out_right);
        DetailCategoryFragment detailCategoryFragment = (DetailCategoryFragment)fragmentManager.findFragmentByTag(category.getNamecategory());
        if(detailCategoryFragment==null){
            detailCategoryFragment = DetailCategoryFragment.newInstance(category);
        }
        transaction.replace(containerId,detailCategoryFragment).addToBackStack(null);
        transaction.setPrimaryNavigationFragment(detailCategoryFragment);
        transaction.commitAllowingStateLoss();
    }
}
