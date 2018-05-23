package com.mvvm.kien2111.mvvmapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mvvm.kien2111.mvvmapplication.util.NetworkUtil;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    public interface ListenNetWorkChange{
        void onNetWorkOff();
        void onNetWorkOn();
    }

    private ListenNetWorkChange netWorkChange;

    public void setNetWorkChange(ListenNetWorkChange netWorkChange) {
        this.netWorkChange = netWorkChange;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(netWorkChange!=null){
            if(!NetworkUtil.isNetworkConnected(context)){
                netWorkChange.onNetWorkOff();
            }else{
                netWorkChange.onNetWorkOn();
            }

        }

    }
}
