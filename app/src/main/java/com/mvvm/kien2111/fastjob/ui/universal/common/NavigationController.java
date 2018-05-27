package com.mvvm.kien2111.fastjob.ui.universal.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.ui.universal.UniversalActivity;
import com.mvvm.kien2111.fastjob.ui.universal.detail_category.DetailCategoryFragment;
import com.mvvm.kien2111.fastjob.ui.universal.detail_profile.DetailProfileFragment;
import com.mvvm.kien2111.fastjob.ui.universal.feed.FeedFragment;
import com.mvvm.kien2111.fastjob.ui.universal.search.SearchFragment;
import com.mvvm.kien2111.fastjob.ui.universal.user.UserFragment;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    private final static String KEY_SEARCH_FRAGMENT = "search-fragment";
    private final static String KEY_USER_FRAGMENT = "user-fragment";
    private final static String KEY_FEED_FRAGMENT = "feed-fragment";
    @Inject
    NavigationController(UniversalActivity activity){
        fragmentManager = activity.getSupportFragmentManager();
        this.containerId = R.id.container;
    }
    public void navigateToSearch(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = (SearchFragment) fragmentManager.findFragmentByTag(KEY_SEARCH_FRAGMENT);
        //Fragment searchFragment = arrayMap.get(KEY_SEARCH_FRAGMENT);
        if(searchFragment==null){
            searchFragment = new SearchFragment();
            transaction.addToBackStack(KEY_SEARCH_FRAGMENT);
            //this.arrayMap.put(KEY_SEARCH_FRAGMENT,searchFragment);
        }
        transaction.setCustomAnimations(R.anim.fade_in_fragment,R.anim.fade_out_fragment,R.anim.fade_in_fragment,R.anim.fade_out_fragment);
        transaction.replace(containerId,searchFragment,KEY_SEARCH_FRAGMENT);
        transaction.setPrimaryNavigationFragment(searchFragment);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToUser(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        UserFragment userFragment = (UserFragment) fragmentManager.findFragmentByTag(KEY_USER_FRAGMENT);
        if(userFragment==null){
            userFragment = new UserFragment();
            transaction.addToBackStack(KEY_USER_FRAGMENT);
        }
        transaction.setCustomAnimations(R.anim.fade_in_fragment,R.anim.fade_out_fragment,R.anim.fade_in_fragment,R.anim.fade_out_fragment);
        transaction.replace(containerId,userFragment,KEY_USER_FRAGMENT);
        transaction.setPrimaryNavigationFragment(userFragment);
        transaction.commitAllowingStateLoss();
    }
    public void navigateToFeed(){
        if(fragmentManager.getPrimaryNavigationFragment() instanceof DetailCategoryFragment || fragmentManager.getPrimaryNavigationFragment() instanceof DetailProfileFragment){
            //do nothing
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment feedFragment = fragmentManager.findFragmentByTag(KEY_FEED_FRAGMENT);
        if(!(feedFragment != null && feedFragment instanceof FeedFragment)){
            feedFragment = new FeedFragment();
            transaction.addToBackStack(KEY_FEED_FRAGMENT);
        }
        transaction.setCustomAnimations(R.anim.fade_in_fragment,R.anim.fade_out_fragment,R.anim.fade_in_fragment,R.anim.fade_out_fragment);
        transaction.replace(containerId,feedFragment,KEY_FEED_FRAGMENT);
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
            transaction.setCustomAnimations(R.anim.fade_in_fragment,R.anim.fade_out_fragment,R.anim.fade_in_fragment,R.anim.fade_out_fragment);
            transaction.replace(containerId,detailProfileFragment,"detailprofile_"+profileModel.getIdprofile())
                    .addToBackStack("detailprofile_"+profileModel.getIdprofile());
            transaction.addSharedElement(imageView, ViewCompat.getTransitionName(imageView));
            transaction.setPrimaryNavigationFragment(detailProfileFragment);
            transaction.commit();
        }
    }

    public void navigateToDetailProfile(ProfileModel profileModel){
        if(fragmentManager.getPrimaryNavigationFragment() instanceof DetailProfileFragment
                && ((DetailProfileFragment)fragmentManager.getPrimaryNavigationFragment()).getCurrentProfile()!=null){
            //do nothing
        }else{
            DetailProfileFragment detailProfileFragment = DetailProfileFragment.newInstance(profileModel);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in_fragment,R.anim.fade_out_fragment,R.anim.fade_in_fragment,R.anim.fade_out_fragment);
            transaction.replace(containerId,detailProfileFragment,"detailprofile_"+profileModel.getIdprofile())
                    .addToBackStack("detailprofile_"+profileModel.getIdprofile());
            transaction.setPrimaryNavigationFragment(detailProfileFragment);
            transaction.commit();
        }
    }

    public void refreshDetailProfile(ProfileModel profileModel){
        try {
            DetailProfileFragment detailProfileFragment =
                    (DetailProfileFragment) fragmentManager.findFragmentByTag("detailprofile_"+profileModel.getIdprofile());
            if(detailProfileFragment!=null){
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.detach(detailProfileFragment);
                transaction.attach(detailProfileFragment);
                transaction.commit();
            }
        }catch (ClassCastException ex){
            Timber.d(ex);
        }finally {
            //do nothing
        }
    }

    public void navigateToDetailCategory(Category category){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_right,R.animator.slide_out_left,R.animator.slide_in_left,R.animator.slide_out_right);
        DetailCategoryFragment detailCategoryFragment = (DetailCategoryFragment)fragmentManager.findFragmentByTag(category.getNamecategory());
        if(detailCategoryFragment==null){
            detailCategoryFragment = DetailCategoryFragment.newInstance(category);
        }
        transaction.setCustomAnimations(R.anim.fade_in_fragment,R.anim.fade_out_fragment,R.anim.fade_in_fragment,R.anim.fade_out_fragment);
        transaction.replace(containerId,detailCategoryFragment).addToBackStack(category.getNamecategory());
        transaction.setPrimaryNavigationFragment(detailCategoryFragment);
        transaction.commitAllowingStateLoss();
    }
}
