package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 08/02/2018.
 */
@Module
public class SearchModule {
    @Provides
    FragmentBindingComponent provideFragmentComponent(SearchFragment highRateFragment){
        return new FragmentBindingComponent(highRateFragment);
    }
    @Provides
    ExpandableListAdapter provideExpandableListAdapter(SearchFragment searchFragment, FragmentBindingComponent fragmentBindingComponent){
        return new ExpandableListAdapter(searchFragment.getContext(),fragmentBindingComponent);
    }
    @Provides
    SearchAdapter provideSearchAdapter(SearchFragment searchFragment,FragmentBindingComponent fragmentBindingComponent){
        return new SearchAdapter(fragmentBindingComponent);
    }
}
