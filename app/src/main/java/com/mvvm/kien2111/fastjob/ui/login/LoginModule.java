package com.mvvm.kien2111.fastjob.ui.login;

import com.mvvm.kien2111.fastjob.model.Credential;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 21/01/2018.
 */
@Module
public class LoginModule {

    @Provides
    public Credential provideCredential(){return new Credential.Builder().build();}

}
