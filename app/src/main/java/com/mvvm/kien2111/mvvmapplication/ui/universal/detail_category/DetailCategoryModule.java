package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 17/03/2018.
 */
@Module
public class DetailCategoryModule {
    @Provides
    FragmentBindingComponent provideFragmentComponent(DetailCategoryFragment detailCategoryFragment){
        return new FragmentBindingComponent(detailCategoryFragment);
    }

    @Provides
    ProfileAdapter provideProfileAdapter(FragmentBindingComponent fragmentBindingComponent, DetailCategoryFragment detailCategoryFragment){
        return new ProfileAdapter(fragmentBindingComponent,detailCategoryFragment);
    }
}
