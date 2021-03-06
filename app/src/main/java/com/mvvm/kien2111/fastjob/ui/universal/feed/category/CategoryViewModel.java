package com.mvvm.kien2111.fastjob.ui.universal.feed.category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;

import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.CategoryRepository;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.model.Resource;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 05/03/2018.
 */

public class CategoryViewModel extends BaseViewModel {
    public ObservableBoolean getLoadingState(){
        return super.getShowLoading();
    }
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<Resource<List<Category>>> resourceLiveData = new MutableLiveData<>();
    @Inject
    public CategoryViewModel(EventBus eventBus, CategoryRepository categoryRepository){
        super(eventBus);
        this.categoryRepository = categoryRepository;
        setUpListCategory();
    }

    private void setUpListCategory() {
        categoryRepository.getListCategory()
                .subscribe(listResource -> resourceLiveData.postValue(listResource),
                        throwable -> resourceLiveData.postValue(Resource.error(throwable.getMessage(),throwable)));
    }

    public LiveData<Resource<List<Category>>> getResourceCategoriesLiveData() {
        return resourceLiveData;
    }
}
