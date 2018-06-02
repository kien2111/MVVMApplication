package com.mvvm.kien2111.fastjob.ui.universal.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.mvvm.kien2111.fastjob.R;

/**
 * Created by WhoAmI on 28/05/2018.
 */

public class CustomTab extends FrameLayout {
    private boolean mIsRevealing;

    public CustomTab(Context context, int position) {
        super(context);

        LayoutInflater li = LayoutInflater.from(context);
        if (position == 0) {
            li.inflate(R.layout.tab, this, true);
        } else {
            li.inflate(R.layout.tab2, this, true);
        }
    }

    public boolean isIsRevealing() {
        return mIsRevealing;
    }

    public void setIsRevealing(boolean mIsRevealing) {
        this.mIsRevealing = mIsRevealing;
    }
}