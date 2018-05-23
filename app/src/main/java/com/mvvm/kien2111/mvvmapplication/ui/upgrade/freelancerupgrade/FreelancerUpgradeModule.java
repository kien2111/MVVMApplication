package com.mvvm.kien2111.mvvmapplication.ui.upgrade.freelancerupgrade;

import com.mvvm.kien2111.mvvmapplication.ui.upgrade.common.IndicatorPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 03/05/2018.
 */
@Module
public class FreelancerUpgradeModule {
    @Provides
    IndicatorPagerAdapter provideIndicatorPagerAdapter(FreelancerUpgradeActivity freelancerUpgradeActivity){
        return new IndicatorPagerAdapter(freelancerUpgradeActivity.getSupportFragmentManager());
    }
}
