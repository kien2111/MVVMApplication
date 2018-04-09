package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentDetailcategoryBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.DetailProfileFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category.CategoryFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailCategoryFragment extends BaseFragment<DetailCategoryViewModel,FragmentDetailcategoryBinding> implements ProfileAdapter.ProfileClickCallback {

    private static final String KEY_PICK_CATEGORY = "KEY_PICK_CATEGORY";
    public static DetailCategoryFragment newInstance(Category category){
        DetailCategoryFragment detailCategoryFragment = new DetailCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PICK_CATEGORY,category);
        detailCategoryFragment.setArguments(bundle);
        return detailCategoryFragment;
    }

    public Category getCurrentCategory(){
        return getArguments().getParcelable(KEY_PICK_CATEGORY);
    }

    @Override
    protected DetailCategoryViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(DetailCategoryViewModel.class);
    }

    @Inject
    NavigationController navigationController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null && getArguments().getParcelable(KEY_PICK_CATEGORY)!=null){

        }else{
            navigationController.navigateToFeed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        setHasOptionsMenu(true);
        setUpToolBar(getArguments().getParcelable(KEY_PICK_CATEGORY));
        setUpProfileAdapter();
        mViewModel.setIdCategory(((Category)getArguments().getParcelable(KEY_PICK_CATEGORY)).getIdcategory());
        return view;
    }

    @Inject
    ProfileAdapter profileAdapter;
    @SuppressLint("RestrictedApi")
    private void setUpToolBar(Category category){
        UniversalActivity activity = (UniversalActivity)getActivity();
        //activity.setSupportActionBar(mFragmentBinding.toolbar);
        activity.getDrawerToggle().setDrawerIndicatorEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setTitle(category.getNamecategory());
        /*mFragmentBinding.toolbar.setTitle(category.getNamecategory());
        mFragmentBinding.toolbar.setTitleTextColor(Color.WHITE);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Toast.makeText(DetailCategoryFragment.this.getActivity(),"back",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpProfileAdapter() {
        mFragmentBinding.profileAdapterRecycleview.setAdapter(profileAdapter);
        mFragmentBinding.profileAdapterRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(lastPosition== profileAdapter.getItemCount()-1){
                    mViewModel.loadNextPage();
                }
            }
        });
        mViewModel.getResourceListProfileMutableLiveData().observe(this,listResource -> {
            switch (listResource.getStatus()){
                case SUCCESS:
                    profileAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
                    break;
                case LOADING:
                    break;
            }

            mFragmentBinding.executePendingBindings();
        });
        mViewModel.getLoadMoreStateLiveData().observe(this,loadMoreState -> {
            if(loadMoreState==null){
                mFragmentBinding.setLoadingMoreState(false);
            }else{
                mFragmentBinding.setLoadingMoreState(loadMoreState.isRunning);
            }
            mFragmentBinding.executePendingBindings();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detailcategory;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onClick(ProfileModel profileModel,ImageView sharedImageView) {
        navigationController.navigateToDetailProfile(profileModel,sharedImageView);
    }
}
