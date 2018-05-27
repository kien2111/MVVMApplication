package com.mvvm.kien2111.fastjob.ui.universal.search.searchresult;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.ui.universal.common.DividerItemDecoration;
import com.mvvm.kien2111.fastjob.ui.universal.search.SearchAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 05/05/2018.
 */
@Module
public class SearchResultModule {
    @Provides
    DividerItemDecoration provideDeviderItemDecoration(SearchActivity searchActivity){
        return new DividerItemDecoration(searchActivity.getResources().getDrawable(R.drawable.seperator_recycleview));
    }

    @Provides
    SearchAdapter provideSearchAdapter(SearchActivity searchActivity){
        return new SearchAdapter(searchActivity);
    }
}
