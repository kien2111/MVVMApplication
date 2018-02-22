package com.mvvm.kien2111.mvvmapplication.authenticate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by WhoAmI on 29/01/2018.
 */

public class AuthenticatorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        AuthenticatorImpl authenticator = new AuthenticatorImpl(this);
        return authenticator.getIBinder();
    }
}
