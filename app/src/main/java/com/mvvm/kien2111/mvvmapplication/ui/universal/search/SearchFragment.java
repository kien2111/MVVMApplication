package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.dagger.Injectable;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentSearchBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.DividerItemDecoration;
import com.mvvm.kien2111.mvvmapplication.util.RxBindingSearchView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class SearchFragment extends BaseFragment<SearchViewModel,FragmentSearchBinding> implements Injectable {

    @Inject
    SearchAdapter expandableListAdapter;
    @Inject
    DividerItemDecoration dividerItemDecoration;

    @Override
    protected SearchViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(SearchViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        //setUpToolBar();
        setUpExpandableAdapter();
        return view;
    }

    private void setUpExpandableAdapter() {
        mFragmentBinding.expandableListview.setAdapter(expandableListAdapter);
        mFragmentBinding.expandableListview.addItemDecoration(dividerItemDecoration);
        //expandableListAdapter.expandAll(mFragmentBinding.expandableListview);//expand all group
        mViewModel.getResourceSearchResultLiveData().observe(this,searchResultResource -> {
            if(searchResultResource==null){

            }else{
                switch (searchResultResource.status){
                    case LOADING:break;
                    case ERROR:((BaseActivity)getActivity()).showErrorDialog("Error",searchResultResource.message);break;
                    case SUCCESS:expandableListAdapter.refreshDataSet(searchResultResource.getData());break;
                }
            }
        });
    }




    private void setUpToolBar(){
        //AppCompatActivity activity = (BaseActivity)getActivity();
        //activity.setSupportActionBar(mFragmentBinding.toolbar);
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMsearch;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_search,menu);
        menu.findItem(R.id.menu_toolbarsearch).setVisible(true);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();

        ImageView mSearchButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        mSearchButton.setImageResource(R.drawable.ic_search_white_24dp);
        ImageView searchCloseIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.ic_close_white_24dp);
        searchView.setQueryHint("Search");

        disposable = RxBindingSearchView.fromSearchView(searchView)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String query) throws Exception {
                        mViewModel.setNewQuery(query);
                    }
                });
    }

    Disposable disposable;

    @Override
    public void onDestroy() {
        if(!disposable.isDisposed())
            disposable.dispose();
        super.onDestroy();
    }
}
