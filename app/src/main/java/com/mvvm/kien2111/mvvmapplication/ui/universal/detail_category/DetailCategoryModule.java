package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.ViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 17/03/2018.
 */
@Module
public class DetailCategoryModule {
    @Provides
    ViewPagerAdapter provideViewPagerAdapter(DetailCategoryFragment detailCategoryFragment){
        return new ViewPagerAdapter(detailCategoryFragment.getChildFragmentManager());
    }

}
