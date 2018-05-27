package com.mvvm.kien2111.fastjob.authenticate;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mvvm.kien2111.fastjob.data.UserRepository;

import javax.inject.Inject;

import dagger.android.DaggerService;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class AuthenticatorService extends DaggerService {
    @Inject
    UserRepository userRepository;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        AuthenticatorImpl authenticator = new AuthenticatorImpl(this,userRepository);
        return authenticator.getIBinder();
    }
}
