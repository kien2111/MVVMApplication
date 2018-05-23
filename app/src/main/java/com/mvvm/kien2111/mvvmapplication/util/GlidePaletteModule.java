package com.mvvm.kien2111.mvvmapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by WhoAmI on 29/04/2018.
 */
@GlideModule
public class GlidePaletteModule extends AppGlideModule{

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.register(Bitmap.class,PaletteBitmap.class,new PaletteBitmapTranscoder(glide));
    }


}
