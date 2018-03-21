package com.mvvm.kien2111.mvvmapplication.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by WhoAmI on 08/03/2018.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void toggleShow(View view, boolean show){
        view.setVisibility(show? View.VISIBLE : view.GONE);
    }
}
