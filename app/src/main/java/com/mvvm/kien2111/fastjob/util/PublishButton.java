package com.mvvm.kien2111.fastjob.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.model.Approve_Publish;

/**
 * Created by WhoAmI on 15/05/2018.
 */

public class PublishButton extends LinearLayout implements View.OnClickListener{
    private static final float DEFAULT_TEXT_SIZE = 12f;
    private static final int DEFAULT_TEXT_COLOR = android.R.color.white;
    private static final int[] drawables = {R.drawable.ic_publish_24dp,R.drawable.ic_public_24dp,R.drawable.ic_conflict_24dp, R.drawable.ic_lock_24dp,};
    private static final int[] drawablesBackground = {R.drawable.ripple_white_publishbutton,R.drawable.ripple_colorprimary_publishbutton};
    private OnClickPublish onClickPublish;
    public interface OnClickPublish{
        void onClick(View v);
    }

    private float dimensionTextSize;
    private int text_color;
    private Drawable bg_drawable;
    private String content_txt;
    private ImageView img;
    private TextView txt;
    private LinearLayout background;
    private Approve_Publish approve_publish;
    public void setDrawableFollowApprove_Publish(Approve_Publish approve_publish){
        switch (approve_publish){
            case NOT_DO_ANYTHING:
                img.setImageResource(drawables[0]);
                img.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                background.setBackground(ContextCompat.getDrawable(getContext(),drawablesBackground[1]));
                txt.setTextColor(ContextCompat.getColor(getContext(),android.R.color.white));
                txt.setText(R.string.publish_do_nothing);
                break;
            case ACCEPT:
                img.setImageResource(drawables[1]);
                img.setColorFilter(ContextCompat.getColor(getContext(), R.color.light_blue), android.graphics.PorterDuff.Mode.SRC_IN);
                background.setBackground(ContextCompat.getDrawable(getContext(),drawablesBackground[0]));
                txt.setTextColor(ContextCompat.getColor(getContext(),R.color.light_blue));
                txt.setText(R.string.publish_accept);
                break;
            case CONFLICT:
                img.setImageResource(drawables[2]);
                img.setColorFilter(ContextCompat.getColor(getContext(), R.color.light_blue), android.graphics.PorterDuff.Mode.SRC_IN);
                background.setBackground(ContextCompat.getDrawable(getContext(),drawablesBackground[0]));
                txt.setTextColor(ContextCompat.getColor(getContext(),R.color.light_blue));
                txt.setText(R.string.publish_conflict);
                break;
            case ADMIN_BLOCKED:
                img.setImageResource(drawables[3]);
                img.setColorFilter(ContextCompat.getColor(getContext(),R.color.light_light_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                background.setBackground(ContextCompat.getDrawable(getContext(),drawablesBackground[0]));
                txt.setTextColor(ContextCompat.getColor(getContext(),R.color.light_light_gray));
                txt.setText(R.string.publish_admin_blocked);
                break;
        }
    }

    public PublishButton(Context context) {
        super(context);
    }

    public PublishButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context,attrs);
    }

    public PublishButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(context,attrs);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PublishButton);
        try{
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_btn_image,this,true);
            background = view.findViewById(R.id.background);
            img = view.findViewById(R.id.img);
            txt = view.findViewById(R.id.txt);
            approve_publish = Approve_Publish.mapApprovePublish(typedArray.getInt(R.styleable.PublishButton_statusapprove_publish,0));
            dimensionTextSize = typedArray.getDimension(R.styleable.PublishButton_publish_text_size,DEFAULT_TEXT_SIZE);
            bg_drawable = typedArray.getDrawable(R.styleable.PublishButton_publish_bg_drawable);
            text_color = typedArray.getColor(R.styleable.PublishButton_publish_text_color,context.getResources().getColor(DEFAULT_TEXT_COLOR));
            content_txt = typedArray.getString(R.styleable.PublishButton_publish_text);
            background.setBackground(bg_drawable);
            txt.setTextColor(text_color);
            txt.setTextSize(dimensionTextSize);
            txt.setText(content_txt);
            setDrawableFollowApprove_Publish(approve_publish);
        }finally {
            typedArray.recycle();
        }

    }

    public void setOnClickPublish(OnClickPublish onClickPublish) {
        this.onClickPublish = onClickPublish;
        this.background.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onClickPublish!=null){
            onClickPublish.onClick(background);
        }
    }

    public float getDimensionTextSize() {
        return dimensionTextSize;
    }

    public void setDimensionTextSize(float dimensionTextSize) {
        this.dimensionTextSize = dimensionTextSize;
        this.txt.setTextSize(dimensionTextSize);
    }

    public int getText_color() {
        return text_color;
    }

    public void setText_color(int text_color) {
        this.text_color = text_color;
        this.txt.setTextColor(text_color);
    }

    public Drawable getBg_drawable() {
        return bg_drawable;
    }

    public void setBg_drawable(Drawable bg_drawable) {
        this.bg_drawable = bg_drawable;
        this.background.setBackground(bg_drawable);
    }

    public String getContent_txt() {
        return content_txt;
    }

    public void setText(String content_txt) {
        if(content_txt!=null){
            this.content_txt = content_txt;
            this.txt.setText(content_txt);
        }
    }
}
