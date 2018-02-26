package com.mvvm.kien2111.mvvmapplication.ui.login;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public interface LoginNavigator {
    void handleError(Throwable throwable);
    void openMainActivity();
}
