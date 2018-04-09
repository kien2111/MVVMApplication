package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 17/03/2018.
 */
@Module
public class DetailProfileModule {
    @Provides
    ViewPagerAdapter provideViewPagerAdapter(DetailProfileFragment detailProfileFragment){
        return new ViewPagerAdapter(detailProfileFragment.getChildFragmentManager());
    }
}
