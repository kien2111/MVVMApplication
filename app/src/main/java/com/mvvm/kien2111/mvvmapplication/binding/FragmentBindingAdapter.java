package com.mvvm.kien2111.mvvmapplication.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.mvvm.kien2111.mvvmapplication.BuildConfig;
import com.mvvm.kien2111.mvvmapplication.util.ScreenUtil;

import java.security.MessageDigest;

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
    @BindingAdapter(value = {"imageUrl","defaultImg","errorImg"},requireAll = false)
    public void setImageUrl(ImageView imageView, String url, Drawable defaultImg,Drawable error){
        Glide.with(fragment)
                .load(BuildConfig.IMG_URL+url)
                .apply(new RequestOptions()
                       .fitCenter()
                .error(error).placeholder(defaultImg).dontAnimate())
                .into(imageView);
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
