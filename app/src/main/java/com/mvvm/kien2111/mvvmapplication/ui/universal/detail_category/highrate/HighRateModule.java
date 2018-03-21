package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category.highrate;

import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 16/03/2018.
 */
@Module
public class HighRateModule {
    @Provides
    FragmentBindingComponent provideFragmentComponent(HighRateFragment highRateFragment){
        return new FragmentBindingComponent(highRateFragment);
    }
    @Provides
    HighRateProfileAdapter provideProfileAdapter(FragmentBindingComponent fragmentBindingComponent,HighRateFragment highRateFragment){
        return new HighRateProfileAdapter(fragmentBindingComponent,highRateFragment);
    }
}
