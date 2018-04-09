package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate;

import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 04/04/2018.
 */
@Module
public class ListRateModule {
    @Provides
    FragmentBindingComponent provideFragmentBindingComponent(ListRateFragment listRateFragment){
        return new FragmentBindingComponent(listRateFragment);
    }
    @Provides
    RateAdapter provideRateAdapter(ListRateFragment listRateFragment,FragmentBindingComponent fragmentBindingComponent){
        return new RateAdapter(fragmentBindingComponent,listRateFragment);
    }
}
