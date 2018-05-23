package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.DividerItemDecoration;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 08/02/2018.
 */
@Module
public class SearchModule {
    @Provides
    FragmentBindingComponent provideFragmentComponent(SearchFragment searchFragment){
        return new FragmentBindingComponent(searchFragment);
    }
    @Provides
    ExpandableListAdapter provideExpandableListAdapter(SearchFragment searchFragment, FragmentBindingComponent fragmentBindingComponent){
        return new ExpandableListAdapter(searchFragment.getContext(),fragmentBindingComponent);
    }
    @Provides
    DividerItemDecoration provideDeviderItemDecoration(SearchFragment searchFragment){
        return new DividerItemDecoration(searchFragment.getContext().getResources().getDrawable(R.drawable.seperator_recycleview));
    }

    @Provides
    RecentSearchAdapter provideRecentSearchAdapter(SearchFragment searchFragment,FragmentBindingComponent fragmentBindingComponent){
        return new RecentSearchAdapter(fragmentBindingComponent,searchFragment);
    }
}
