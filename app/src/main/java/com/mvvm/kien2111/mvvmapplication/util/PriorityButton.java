package com.mvvm.kien2111.mvvmapplication.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.mvvm.kien2111.mvvmapplication.R;

/**
 * Created by WhoAmI on 01/05/2018.
 */

public class PriorityButton extends Button {
    private static final int[] STATE_LEVEL_BUTTON = {R.attr.priority};
    public PriorityButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
