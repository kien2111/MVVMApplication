package com.mvvm.kien2111.mvvmapplication.ui.universal.feed.category;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Insert;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentCategoryBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 05/03/2018.
 */

public class CategoryFragment extends BaseFragment<CategoryViewModel,FragmentCategoryBinding> implements CategoryAdapter.CategoryClickCallBack{

    @Inject
    NavigationController navigationController;

    @Override
    protected CategoryViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(CategoryViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupCategoryAdapter();
    }

    @Inject
    CategoryAdapter categoryAdapter;
    private void setupCategoryAdapter() {
        mFragmentBinding.recycleViewCategories.setAdapter(categoryAdapter);
        mViewModel.getResourceCategoriesLiveData().observe(this,listResource -> {
            switch (listResource.status){
                case SUCCESS:
                    if(listResource!=null && listResource.getData()!=null){
                        categoryAdapter.changeDataSet(listResource.data);
                    }else{
                        categoryAdapter.changeDataSet(Collections.emptyList());
                    }
                    break;
                case LOADING:
                    Toast.makeText(CategoryFragment.this.getContext(),"Loading",Toast.LENGTH_LONG).show();
                    break;
                case ERROR:
                    handleError(listResource.message);
                    break;
            }

        });
    }

    private void handleError(String message) {
        //show error
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    public void onClick(Category category) {
        if(category!=null){
            navigationController.navigateToDetailCategory(category);
        }

    }
}
