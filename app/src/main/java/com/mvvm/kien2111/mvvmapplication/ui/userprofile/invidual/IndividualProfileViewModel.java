package com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.CategoryRepository;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.model.Approve_Publish;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 20/04/2018.
 */

public class IndividualProfileViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<Resource<List<Category>>> resourceMutableLiveData = new MutableLiveData<>();
    @Inject
    public IndividualProfileViewModel(EventBus eventBus,
                                      CategoryRepository categoryRepository,
                                      UserRepository userRepository) {
        super(eventBus);
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        compositeDisposable.add(categoryRepository
                .getListCategory()
                .subscribe(resourceMutableLiveData::setValue, throwable -> {
                    resourceMutableLiveData.setValue(Resource.error(throwable.getMessage(),throwable));
                }));
    }

    public MutableLiveData<Resource<List<Category>>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }
}
