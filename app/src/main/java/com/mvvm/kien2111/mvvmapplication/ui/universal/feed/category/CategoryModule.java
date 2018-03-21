package com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category;

import android.databinding.DataBindingComponent;

import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.ViewPagerAdapter;

import dagger.Binds;
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
}
