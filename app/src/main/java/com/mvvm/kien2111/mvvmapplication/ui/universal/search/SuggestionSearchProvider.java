package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by WhoAmI on 05/05/2018.
 */

public class SuggestionSearchProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = SuggestionSearchProvider.class.getCanonicalName();
    public final static int MODE = DATABASE_MODE_QUERIES;
    public SuggestionSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
