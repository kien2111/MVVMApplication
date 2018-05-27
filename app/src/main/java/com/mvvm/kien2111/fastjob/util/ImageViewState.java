package com.mvvm.kien2111.fastjob.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mvvm.kien2111.fastjob.R;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class ImageViewState extends ImageView {

    private static final Integer DEFAULT_DRAWABLE_ON = R.drawable.ic_expand_less_gray_24dp;
    private static final Integer DEFAULT_DRAWABLE_OFF = R.drawable.ic_expand_more_gray_24dp;
    private Integer mIdDrawableOn;
    private Integer mIdDrawableOff;
    private boolean mIsExpand = false;


    public ImageViewState(Context context) {
        super(context);
    }

    public ImageViewState(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context,attrs);
    }

    public ImageViewState(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(context,attrs);
    }

    private void loadAttributes(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ImageViewState,0,0);
        mIdDrawableOn = typedArray.getInteger(R.styleable.ImageViewState_drawableOn,
                0);
        mIdDrawableOff = typedArray.getInteger(R.styleable.ImageViewState_drawableOff,
                0);
        setImageResource(mIdDrawableOff);
        typedArray.recycle();
    }

    @Override
    public void setImageResource(int resId) {
        mIsExpand = !mIsExpand;
        super.setImageResource(resId);
    }

    public void toggleView(){
        if(this.mIsExpand){
            setImageResource(mIdDrawableOn);
        }else{
            setImageResource(mIdDrawableOff);
        }
    }


}
