package com.mvvm.kien2111.mvvmapplication.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvvm.kien2111.mvvmapplication.R;

/**
 * Created by WhoAmI on 18/05/2018.
 */

public class NoAvailableData extends LinearLayout {

    private static final float DEFAULT_TEXT_SIZE = 12f;
    private static final int DEFAULT_TEXT_COLOR = android.R.color.white;
    private static final float DEFAULT_IMG_WIDTH = 1f;
    private static final float DEFAULT_IMG_HEIGHT = 1f;
    private ImageView img_drawable;
    private TextView txt_label;
    private float dimensionTextSize;
    private Drawable drawable;
    private int text_color;
    private String content_txt;
    private float dimensionImgWidth;
    private float dimensionImgHeight;
    public NoAvailableData(Context context) {
        super(context);
    }

    public NoAvailableData(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context,attrs);
    }

    public NoAvailableData(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(context,attrs);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoAvailableData);
        try{
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.no_data_available_layout,this,true);
            img_drawable = view.findViewById(R.id.img_drawable);
            txt_label = view.findViewById(R.id.txt_label);
            dimensionTextSize = typedArray.getDimension(R.styleable.NoAvailableData_no_available_textsize,DEFAULT_TEXT_SIZE);
            drawable = typedArray.getDrawable(R.styleable.NoAvailableData_no_available_img);
            text_color = typedArray.getColor(R.styleable.NoAvailableData_no_available_textcolor,context.getResources().getColor(DEFAULT_TEXT_COLOR));
            content_txt = typedArray.getString(R.styleable.NoAvailableData_no_available_text_content);
            dimensionImgWidth = typedArray.getDimension(R.styleable.NoAvailableData_no_available_img_width,DEFAULT_IMG_WIDTH);
            dimensionImgHeight = typedArray.getDimension(R.styleable.NoAvailableData_no_available_img_height,DEFAULT_IMG_HEIGHT);

            img_drawable.getLayoutParams().height = Math.round(dimensionImgHeight);
            img_drawable.getLayoutParams().width = Math.round(dimensionImgWidth);
            img_drawable.requestLayout();
            img_drawable.setImageDrawable(drawable);
            txt_label.setTextColor(text_color);
            txt_label.setTextSize(dimensionTextSize);
            txt_label.setText(content_txt);
        }finally {
            typedArray.recycle();
        }

    }
}
