package com.mvvm.kien2111.mvvmapplication.ui.userprofile;

import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;

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
    @Provides
    User provideUser(PreferenceHelper preferenceHelper){
        return preferenceHelper.getUserData().getUser();
    }
}
