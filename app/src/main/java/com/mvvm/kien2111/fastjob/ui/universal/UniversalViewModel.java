package com.mvvm.kien2111.fastjob.ui.universal;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.CategoryRepository;
import com.mvvm.kien2111.fastjob.data.ProfileRepository;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.local.db.entity.Category;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.fastjob.model.Priority;
import com.mvvm.kien2111.fastjob.model.Role;

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
