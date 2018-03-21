package com.mvvm.kien2111.mvvmapplication.ui.universal.feed;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 08/02/2018.
 */
@Module
public class FeedModule {
    @Provides
    ViewPagerAdapter provideViewPagerAdapter(FeedFragment feedFragment){
        return new ViewPagerAdapter(feedFragment.getChildFragmentManager());
    }

}
