package com.mvvm.kien2111.mvvmapplication.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 11/02/2018.
 */

public class FragmentBindingAdapter {
    final Fragment fragment;
    @Inject
    public FragmentBindingAdapter(Fragment fragment){
        this.fragment = fragment;
    }
    @BindingAdapter({"imageUrl","defaultImg"})
    public void setImageUrl(ImageView imageView, String url, Drawable defaultImg){
        Glide.with(fragment).load(url).into(imageView).onLoadStarted(defaultImg);
    }
}
