package com.mvvm.kien2111.fastjob.ui.signup;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.base.BaseViewModel;
import com.mvvm.kien2111.fastjob.data.UserRepository;
import com.mvvm.kien2111.fastjob.data.remote.model.SignUpRequest;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 30/01/2018.
 */

public class SignUpViewModel extends BaseViewModel {
    private final UserRepository userRepository;
    @Inject
    public SignUpViewModel(EventBus eventBus,UserRepository userRepository){
        super(eventBus);
        this.userRepository = userRepository;
    }

    public void signUp(final SignUpRequest signUpRequest){
        compositeDisposable.add(userRepository.signUp(signUpRequest).subscribe(() -> {
            eventBus.post(BaseMessage.success("Đăng kí tài khoản thành công"));
        },throwable -> {
            eventBus.post(BaseMessage.error(throwable));
        }));
    }
}
