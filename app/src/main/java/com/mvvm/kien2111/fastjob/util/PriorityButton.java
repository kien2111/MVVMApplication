package com.mvvm.kien2111.fastjob.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.model.Priority;

/**
 * Created by WhoAmI on 01/05/2018.
 */

public class PriorityButton extends Button {

    private int priority;

    public PriorityButton(Context context) {
        super(context);
    }
    public PriorityButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context,attrs);

    }

    public PriorityButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(context,attrs);
    }

    private void loadAttributes(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.PriorityButton,0,0);
        priority = typedArray.getInt(R.styleable.PriorityButton_Priority,
                0);
        setStateChangeWhenPriorityChange(Priority.mapPriority(priority));
        typedArray.recycle();
    }

    public void setStateChangeWhenPriorityChange(Priority priorityChange){
        switch (priorityChange){
            case FREE:
                break;
            case BASIC:
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.bronze_medal_48px,0,0,0);
                break;
            case MEDIUM:
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.silver_medal_48px,0,0,0);
                break;
            case PREMIUM:
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.gold_medal_48px,0,0,0);
                break;
            default:
                break;
        }
    }
}
