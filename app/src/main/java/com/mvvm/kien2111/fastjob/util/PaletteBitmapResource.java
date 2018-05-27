package com.mvvm.kien2111.fastjob.util;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Util;

/**
 * Created by WhoAmI on 29/04/2018.
 */

public class PaletteBitmapResource implements Resource<PaletteBitmap> {
    private final PaletteBitmap paletteBitmap;
    private final BitmapPool bitmapPool;

    public PaletteBitmapResource(@NonNull PaletteBitmap paletteBitmap, @NonNull BitmapPool bitmapPool) {
        this.paletteBitmap = paletteBitmap;
        this.bitmapPool = bitmapPool;
    }

    @NonNull
    @Override
    public Class<PaletteBitmap> getResourceClass() {
        return PaletteBitmap.class;
    }

    @Override public PaletteBitmap get() {
        return paletteBitmap;
    }

    @Override public int getSize() {
        return Util.getBitmapByteSize(paletteBitmap.bitmap);
    }

    @Override public void recycle() {
        bitmapPool.put(paletteBitmap.bitmap);
        paletteBitmap.bitmap.recycle();
    }
}
