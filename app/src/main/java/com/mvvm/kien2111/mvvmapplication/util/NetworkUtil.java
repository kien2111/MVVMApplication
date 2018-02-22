package com.mvvm.kien2111.mvvmapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public final class NetworkUtil {
    private NetworkUtil(){}
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        return activeNetwork !=null && activeNetwork.isConnectedOrConnecting();
    }
}
