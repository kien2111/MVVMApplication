package com.mvvm.kien2111.fastjob.ui.userprofile;

import com.mvvm.kien2111.fastjob.ui.universal.common.ViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 20/04/2018.
 */
@Module
public class UserProfileModule {
    @Provides
    ViewPagerAdapter provideViewPagerAdapter(UserProfileActivity activity){
        return new ViewPagerAdapter(activity.getSupportFragmentManager());
    }
}
