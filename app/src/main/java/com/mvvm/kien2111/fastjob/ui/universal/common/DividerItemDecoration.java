package com.mvvm.kien2111.fastjob.ui.universal.common;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by WhoAmI on 03/04/2018.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable drawable;
    public DividerItemDecoration(Drawable drawable){
        this.drawable = drawable;
    }
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + drawable.getIntrinsicHeight();

            drawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            drawable.draw(canvas);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view)==0){
            return;
        }
        outRect.top = drawable.getIntrinsicHeight();

    }
}
