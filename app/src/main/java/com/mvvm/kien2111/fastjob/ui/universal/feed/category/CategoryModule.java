package com.mvvm.kien2111.fastjob.ui.universal.feed.category;

import android.support.v7.widget.GridLayoutManager;

import com.mvvm.kien2111.fastjob.binding.FragmentBindingComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 05/03/2018.
 */
@Module
public class CategoryModule {
    @Provides
    CategoryAdapter provideCategoryAdapter(FragmentBindingComponent fragmentBindingComponent,CategoryFragment categoryFragment){
        return new CategoryAdapter(fragmentBindingComponent,categoryFragment);
    }
    @Provides
    FragmentBindingComponent provideFragmentBindingComponent(CategoryFragment categoryFragment){
        return new FragmentBindingComponent(categoryFragment);
    }
    @Provides
    static GridLayoutManager provideGridlayoutManager(CategoryFragment categoryFragment){
        return new GridLayoutManager(categoryFragment.getActivity(),NUM_COLUMN_GRIDLAYOUTMANAGER);
    }
    private static int NUM_COLUMN_GRIDLAYOUTMANAGER = 2;
}
