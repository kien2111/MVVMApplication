package com.mvvm.kien2111.mvvmapplication.ui.universal;

import android.accounts.Account;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.CategoryRepository;
import com.mvvm.kien2111.mvvmapplication.data.ProfileRepository;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.model.Role;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchViewModel;
import com.mvvm.kien2111.mvvmapplication.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 31/01/2018.
 */

public class UniversalViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CategoryRepository categoryRepository;
    private final PreferenceLiveData preferenceLiveData;
    @Inject
    UniversalViewModel(EventBus eventBus
            ,PreferenceLiveData preferenceLiveData
            ,ProfileRepository profileRepository
            ,CategoryRepository categoryRepository
            , UserRepository userRepository){
        super(eventBus);
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.categoryRepository = categoryRepository;
        this.preferenceLiveData = preferenceLiveData;
        this.preferenceLiveData.setUser(userRepository.getUserData().getUser());
    }
    public void updateAccessToken(String authTokenType, String authToken, Priority priority, List<Role> roles){
        //userRepository.updateAccessTokenOnly(authTokenType,authToken,priority,roles);
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }

    public Account getCurrentAccout(){
        return userRepository.getCurrentAccount();
    }

    public void fetchCategoryWithId(final String id){
        compositeDisposable.add(categoryRepository
                .fetchCategoryWithId(id).subscribe(category -> {
                    eventBus.post(new UniversalViewModel.CategoryFetchMessage(category));
                },throwable -> {
                    eventBus.post(BaseMessage.error(throwable));
                }));
    }

    public void fetchProfileWithId(final String id){
        compositeDisposable.add(profileRepository.fetchDetaiProfileWithId(id).subscribe(profileModel -> {
            eventBus.post(new ProfileFetchMessage(profileModel));
        },throwable -> {
            eventBus.post(BaseMessage.error(throwable));
        }));
    }

    public static class CategoryFetchMessage extends BaseMessage{
        public Category category;
        public CategoryFetchMessage(Category category){this.category = category;}
    }
    public static class ProfileFetchMessage extends BaseMessage{
        public ProfileModel profileModel;
        public ProfileFetchMessage (ProfileModel profileModel){this.profileModel = profileModel;}
    }
}
