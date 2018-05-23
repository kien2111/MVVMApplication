package com.mvvm.kien2111.mvvmapplication.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mvvm.kien2111.mvvmapplication.BuildConfig;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.util.GlideApp;
import com.mvvm.kien2111.mvvmapplication.util.PaletteBitmap;
import com.mvvm.kien2111.mvvmapplication.util.PaletteBitmapTranscoder;
import com.mvvm.kien2111.mvvmapplication.util.ScreenUtil;

import java.io.File;
import java.net.URI;
import java.security.MessageDigest;
import java.util.Objects;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by WhoAmI on 11/02/2018.
 */

public class FragmentBindingAdapter {
    final Fragment fragment;
    @Inject
    public FragmentBindingAdapter(Fragment fragment){
        this.fragment = fragment;
    }
    @BindingAdapter(value = {"imageUrl","defaultImg","errorImg"},requireAll = false)
    public void setImageUrl(ImageView imageView, String url, Drawable defaultImg,Drawable error){
        GlideApp.with(fragment)
                .as(PaletteBitmap.class)
                .load(BuildConfig.IMG_URL+url)
                .apply(new RequestOptions()
                        .fitCenter()
                .error(error)
                .placeholder(defaultImg)
                .dontAnimate())
                .into(new SimpleTarget<PaletteBitmap>() {
                    @Override
                    public void onResourceReady(@NonNull PaletteBitmap resource, @Nullable Transition<? super PaletteBitmap> transition) {
                        imageView.setBackgroundColor(resource.palette.getDarkMutedColor(ContextCompat.getColor(fragment.getContext(),android.R.color.white)));
                        imageView.setImageBitmap(resource.bitmap);
                    }
                });
    }
    @BindingAdapter(value={"imageCircleUrl","defaultImg","errorImg"},requireAll = false)
    public void setCircleImageUrl(ImageView circleImage,String url,Drawable defaultImg,Drawable error){
        GlideApp.with(fragment)
                .load(BuildConfig.IMG_URL+url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(error).placeholder(defaultImg).dontAnimate())
                .into(circleImage);
    }

    @BindingAdapter(value={"imageCircleUrl"},requireAll = false)
    public void setCircleImageUrl(ImageView circleImage,String url){
        GlideApp.with(fragment)
                .load(BuildConfig.IMG_URL+url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(ContextCompat.getDrawable(fragment.getContext(),R.drawable.errorimg))
                        .placeholder(ContextCompat.getDrawable(fragment.getContext(),R.drawable.defaultimage))
                        .dontAnimate())
                .into(circleImage);
    }

    @BindingAdapter(value={"imageUri","defaultImg","errorImg"},requireAll = false)
    public void setImageUri(ImageView imageView, Uri uri, Drawable defaultImg, Drawable error){
        GlideApp.with(fragment)
                .load(uri)
                .apply(new RequestOptions()
                        .error(error)
                        .placeholder(defaultImg)
                        .dontAnimate())
                .into(imageView);
    }

    @BindingAdapter(value = {"imageCircleFilePath"},requireAll = false)
    public void setCircleImageFromFilePath(ImageView circleImageUri,String imagePath){
        try{
            if (imagePath!=null){
                GlideApp.with(fragment)
                        .load(new File(imagePath))
                        .apply(new RequestOptions()
                                .centerCrop()
                                .error(ContextCompat.getDrawable(fragment.getContext(),R.drawable.errorimg))
                                .placeholder(ContextCompat.getDrawable(fragment.getContext(),R.drawable.defaultimage))
                                .dontAnimate())
                        .into(circleImageUri);
            }
        }finally {

        }
    }


    private static class MyBitmapTransformation extends BitmapTransformation{

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            int ratio = outHeight/toTransform.getHeight();
            Bitmap result = pool.get(outWidth*ratio,outHeight*ratio, Bitmap.Config.ARGB_8888);
            if(result==null){
                result = Bitmap.createScaledBitmap(toTransform,toTransform.getWidth()*ratio,toTransform.getHeight()*ratio,true);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setAlpha(255);
            // Draw the original Bitmap onto the result Bitmap with a transformation.
            canvas.drawBitmap(toTransform, 0, 0, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }
}
