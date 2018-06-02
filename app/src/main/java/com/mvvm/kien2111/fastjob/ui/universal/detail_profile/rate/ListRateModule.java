package com.mvvm.kien2111.fastjob.ui.universal.detail_profile.rate;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.mvvm.kien2111.fastjob.binding.FragmentBindingComponent;

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
    @Provides
    DividerItemDecoration provideDecoration(ListRateFragment listRateFragment){
        return new DividerItemDecoration(listRateFragment.getContext(), LinearLayoutManager.VERTICAL);
    }
}
