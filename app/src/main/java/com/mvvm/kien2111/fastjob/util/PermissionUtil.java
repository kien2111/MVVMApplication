package com.mvvm.kien2111.fastjob.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import timber.log.Timber;

/**
 * Created by WhoAmI on 06/05/2018.
 */

public final class PermissionUtil {
    public static boolean checkWriteExternalPermission(Context context){
        if(Build.VERSION.SDK_INT>=23){
            if(ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }else{
                return true;
            }
        }else{
            Timber.i("API below 23 no need request permission");
            return true;
        }

    }

    public static boolean checkBothReadWritePermission(Context context){
        if(Build.VERSION.SDK_INT>=23){
            if(ContextCompat.checkSelfPermission(context,Manifest.permission_group.STORAGE)==PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                return false;
            }
        }else{
            Timber.i("API below 23 no need request permission");
            return false;
        }
    }

    public static boolean checkReadExternalPermission(Context context){
        if(Build.VERSION.SDK_INT>=23){
            if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                return false;
            }
        }else{
            Timber.i("API below 23 no need request permission");
            return false;
        }
    }

    public static void requestReadPermission(@NonNull Activity activity,int requestCode){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},requestCode);

    }

    public static void requestWritePersmission(@NonNull Activity activity,int requestCode){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);

    }

    public static void requestBothReadWritePermission(@NonNull Activity activity,int requestCode){
        ActivityCompat.requestPermissions(activity,new String[]{
                Manifest.permission_group.STORAGE
        },requestCode);
    }

    public static void requestLocationPermission(@NonNull Activity activity,int requestCode){
        ActivityCompat.requestPermissions(activity,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        },requestCode);
    }
}
