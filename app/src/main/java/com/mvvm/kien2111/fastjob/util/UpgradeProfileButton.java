package com.mvvm.kien2111.fastjob.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.ui.upgrade.common.RequestUpgradeModel;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public class UpgradeProfileButton extends LinearLayout implements View.OnClickListener{
   /* private static final int DEFAULT_ONPROCESS_DRAWABLE = 0;
    private static final int DEFAULT_CURRENT_LEVEL_DRAWABLE = 0;
    private static final int DEFAULT_NORMAL_DRAWABLE = 0;*/
    private static final float DEFAULT_TEXT_SIZE = 12f;
    private static final int DEFAULT_TEXT_COLOR = android.R.color.white;
    private static final int[] drawables = {R.drawable.ic_onprocess_24dp,R.drawable.ic_whatshot_24dp};
    private static final int[] drawablesBackground = {R.drawable.ripple_freelancer_basic,R.drawable.ripple_freelancer_medium,R.drawable.ripple_freelancer_premium};
    private OnClickUpgrade onClickUpgrade;
    public interface OnClickUpgrade{
        void onClick(View v);
    }
    private RequestUpgradeModel.Status_Request_Update_Profile request_update_profile;
    private float dimensionTextSize;
    private int text_color;
    private Drawable bg_drawable;
    private String content_txt;
    private ImageView img;
    private TextView txt;
    private LinearLayout background;

    public void setDrawableFollowStatusRequestUpgrade(RequestUpgradeModel.Status_Request_Update_Profile requestUpgrade){
        switch (requestUpgrade){
            case ON_PROCESS:
                img.setImageResource(drawables[0]);
                break;
            case CURRENT_LEVEL:
                img.setImageResource(drawables[1]);
                break;
        }
    }

    public UpgradeProfileButton(Context context) {
        super(context);
    }

    public UpgradeProfileButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context,attrs);
    }

    public UpgradeProfileButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(context,attrs);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UpgradeProfileButton);
        try{
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_btn_image,this,true);
            background = view.findViewById(R.id.background);
            img = view.findViewById(R.id.img);
            txt = view.findViewById(R.id.txt);
            request_update_profile = RequestUpgradeModel.Status_Request_Update_Profile.mapStatusRequest(typedArray.getInt(R.styleable.UpgradeProfileButton_statusrequestupgrade,0));
            dimensionTextSize = typedArray.getDimension(R.styleable.UpgradeProfileButton_text_size,DEFAULT_TEXT_SIZE);
            bg_drawable = typedArray.getDrawable(R.styleable.UpgradeProfileButton_bg_drawable);
            text_color = typedArray.getColor(R.styleable.UpgradeProfileButton_text_color,context.getResources().getColor(DEFAULT_TEXT_COLOR));
            content_txt = typedArray.getString(R.styleable.UpgradeProfileButton_text);
            background.setBackground(bg_drawable);
            txt.setTextColor(text_color);
            txt.setTextSize(dimensionTextSize);
            txt.setText(content_txt);
            setDrawableFollowStatusRequestUpgrade(request_update_profile);
        }finally {
            typedArray.recycle();
        }

    }

    public void setOnClickUpgrade(OnClickUpgrade onClickUpgrade) {
        this.onClickUpgrade = onClickUpgrade;
        this.background.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onClickUpgrade!=null){
            onClickUpgrade.onClick(v);
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
