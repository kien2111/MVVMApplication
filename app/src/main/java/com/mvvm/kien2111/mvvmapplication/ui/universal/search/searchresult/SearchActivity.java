package com.mvvm.kien2111.mvvmapplication.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivitySearchBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.DividerItemDecoration;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SuggestionSearchProvider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 05/05/2018.
 */

public class SearchActivity extends BaseActivity<SearchViewModel,ActivitySearchBinding> {

    @Inject
    SearchAdapter expandableListAdapter;
    @Inject
    DividerItemDecoration dividerItemDecoration;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected SearchViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(SearchViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    private static String GMS_SEARCH_ACTION = "com.google.android.gms.actions.SEARCH_ACTION";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar();
        setUpSearchAdapter();
        handleIntent(getIntent());
    }

    private void setUpSearchAdapter() {
        mActivityBinding.expandableListview.setAdapter(expandableListAdapter);
        mActivityBinding.expandableListview.addItemDecoration(dividerItemDecoration);
        //expandableListAdapter.expandAll(mFragmentBinding.expandableListview);//expand all group
        mViewModel.getResourceSearchResultLiveData().observe(this,searchResultResource -> {
            if(searchResultResource==null){

            }else{
                switch (searchResultResource.status){
                    case LOADING:break;
                    case ERROR:showDialog("Error",searchResultResource.message);break;
                    case SUCCESS:expandableListAdapter.refreshDataSet(searchResultResource.getData());break;
                }
            }
        });
    }

    private void setUpToolbar() {
        setSupportActionBar(mActivityBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        mSearchmenuItem.setVisible(true);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        ImageView searchVoiceIcon =  searchView.findViewById(android.support.v7.appcompat.R.id.search_voice_btn);
        searchView.setIconifiedByDefault(false);
        searchVoiceIcon.setImageResource(R.drawable.ic_keyboard_voice_gray_24dp);
        searchView.setSubmitButtonEnabled(true);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            searchView.setSearchableInfo(info);
        }
        searchView.setFocusable(false);
        searchView.setFocusableInTouchMode(false);
        mSearchmenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                animateSearchToolbar(1, true, true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (mSearchmenuItem.isActionViewExpanded()) {
                    animateSearchToolbar(1, false, false);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void animateSearchToolbar(int numberOfMenuIcon, boolean containsOverflow, boolean show) {
        Toolbar mToolbar = mActivityBinding.toolbar;
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));

        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = mToolbar.getWidth() -
                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(mToolbar,
                        isRtl(getResources()) ? mToolbar.getWidth() - width : width, mToolbar.getHeight() / 2, 0.0f, (float) width);
                createCircularReveal.setDuration(250);
                createCircularReveal.start();
            } else {
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (-mToolbar.getHeight()), 0.0f);
                translateAnimation.setDuration(220);
                mToolbar.clearAnimation();
                mToolbar.startAnimation(translateAnimation);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = mToolbar.getWidth() -
                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(mToolbar,
                        isRtl(getResources()) ? mToolbar.getWidth() - width : width, mToolbar.getHeight() / 2, (float) width, 0.0f);
                createCircularReveal.setDuration(250);
                createCircularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    }
                });
                createCircularReveal.start();
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-mToolbar.getHeight()));
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setDuration(220);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mToolbar.startAnimation(animationSet);
            }
        }
    }

    private boolean isRtl(Resources resources) {
        return resources.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    private static int getThemeColor(Context context, int id) {
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(new int[]{id});
        int result = a.getColor(0, 0);
        a.recycle();
        return result;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                handleDeleteRecentQuery();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void handleDeleteRecentQuery() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SuggestionSearchProvider.AUTHORITY, SuggestionSearchProvider.MODE);
        suggestions.clearHistory();
    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())|| intent.getAction().equals(GMS_SEARCH_ACTION)){
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionSearchProvider.AUTHORITY, SuggestionSearchProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            mViewModel.setNewQuery(query);
        }
    }
}
