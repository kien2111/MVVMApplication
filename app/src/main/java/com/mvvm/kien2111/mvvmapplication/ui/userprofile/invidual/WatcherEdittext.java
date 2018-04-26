package com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileActivity;

import java.lang.ref.WeakReference;

/**
 * Created by WhoAmI on 22/04/2018.
 */

public class WatcherEdittext implements TextWatcher {

    private final WeakReference<Activity> activityWeakReference ;
    private TextChangeListener listener;

    public TextChangeListener getTextChangeListener() {
        return listener;
    }

    public interface TextChangeListener{
        void onTextChanged(String s);
    }
    public WatcherEdittext(Activity activity,TextChangeListener listener){
        this.activityWeakReference = new WeakReference<Activity>(activity);
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(listener!=null){
            listener.onTextChanged(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            ((UserProfileActivity)activityWeakReference.get()).showButtonSaveChangeAnimation();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
