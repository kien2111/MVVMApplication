package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.MotionEvent;
import android.view.View;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.databinding.AppointmentBusinessItemBinding;
import com.mvvm.kien2111.mvvmapplication.databinding.AppointmentFreelancerItemBinding;
import com.mvvm.kien2111.mvvmapplication.util.ImageUtil;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

/**
 * Created by WhoAmI on 01/05/2018.
 */
enum ButtonsState {
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}
public class RecyclerItemTouchHelper extends ItemTouchHelper.Callback {
    private RecyclerItemTouchHelperListener listener;
    private Paint p;
    private Context context;
    private RectF buttonInstance = null;
    private boolean swipeBack = true;
    private RecyclerView.ViewHolder currentItemViewHolder = null;
    private static final float buttonWidth = 300;
    private static final float iconSize = 20;
    private static final float marginIconwithText = 20;

    private ButtonsState buttonShowedState = ButtonsState.GONE;
    public RecyclerItemTouchHelper(Context context,RecyclerItemTouchHelperListener listener) {
        this.listener = listener;
        this.context = context;
        p = new Paint();
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            if((((BaseViewHolder) viewHolder).mBinding) instanceof AppointmentFreelancerItemBinding){
                /*final View foregroundView = ((AppointmentFreelancerItemBinding)((BaseViewHolder) viewHolder).mBinding).foreground;
                getDefaultUIUtil().onSelected(foregroundView);*/
            }

        }
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack) {
                    if (dX < -buttonWidth) buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                    else if (dX > buttonWidth) buttonShowedState  = ButtonsState.LEFT_VISIBLE;

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    RecyclerItemTouchHelper.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (listener != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                            listener.onAcceptClick(viewHolder.getAdapterPosition());
                        }
                        else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                            listener.onDeclineClick(viewHolder.getAdapterPosition());
                        }
                    }
                    buttonShowedState = ButtonsState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        if((((BaseViewHolder) viewHolder).mBinding) instanceof AppointmentFreelancerItemBinding){
            /*final View foregroundView = ((AppointmentFreelancerItemBinding)((BaseViewHolder) viewHolder).mBinding).foreground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);*/
        }

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if((((BaseViewHolder) viewHolder).mBinding) instanceof AppointmentFreelancerItemBinding){
            /*final View foregroundView = ((AppointmentFreelancerItemBinding)((BaseViewHolder) viewHolder).mBinding).foreground;
            getDefaultUIUtil().clearView(foregroundView);*/
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        if((((BaseViewHolder) viewHolder).mBinding) instanceof AppointmentFreelancerItemBinding){
            /*final View foregroundView = ((AppointmentFreelancerItemBinding)((BaseViewHolder) viewHolder).mBinding).foreground;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);*/


            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                if(buttonShowedState!=ButtonsState.GONE){
                    if(buttonShowedState==ButtonsState.LEFT_VISIBLE)dX = Math.max(dX,buttonWidth);
                    if(buttonShowedState==ButtonsState.RIGHT_VISIBLE)dX = Math.min(dX,-buttonWidth);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }else{
                    setTouchListener(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
                }
                if(buttonShowedState==ButtonsState.GONE){
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                currentItemViewHolder = viewHolder;

            }

        }
    }

    private void drawText(String text, Canvas c, RectF container,RectF icon, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, container.centerX()-(textWidth/2), icon.bottom+marginIconwithText+(textSize/2), p);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
        void onAcceptClick(int position);
        void onDeclineClick(int position);
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }



    private void drawButtons(Canvas c, RecyclerView.ViewHolder currentItemViewHolder) {
        View itemView = currentItemViewHolder.itemView;
        float buttonWidthWithoutPadding = buttonWidth - 20;

        p.setColor(Color.parseColor("#388E3C"));
        RectF leftButton = new RectF((float) itemView.getLeft(), (float) itemView.getTop(),itemView.getLeft()+buttonWidthWithoutPadding,(float) itemView.getBottom());
        c.drawRect(leftButton,p);
        Bitmap accept_icon = ImageUtil.getBitmap(ContextCompat.getDrawable(context,R.drawable.ic_accept_white_24dp));
        RectF icon_rect_accept = new RectF(
                (leftButton.centerX()-accept_icon.getWidth()/2)-iconSize,
                (leftButton.centerY()-accept_icon.getHeight()/2)-iconSize,
                (leftButton.centerX()+accept_icon.getWidth()/2)+iconSize,
                (leftButton.centerY()+accept_icon.getHeight()/2)+iconSize);
        c.drawBitmap(accept_icon,null,icon_rect_accept,p);
        drawText("Accept",c,leftButton,icon_rect_accept,p);


        p.setColor(Color.parseColor("#D32F2F"));
        RectF rightButton = new RectF(itemView.getRight()-buttonWidthWithoutPadding, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
        c.drawRect(rightButton,p);
        Bitmap decline_icon = ImageUtil.getBitmap(ContextCompat.getDrawable(context,R.drawable.ic_clear_white_24dp));
        RectF icon_rect_decline = new RectF(
                (rightButton.centerX()-decline_icon.getWidth()/2)-iconSize,
                (rightButton.centerY()-decline_icon.getHeight()/2)-iconSize,
                (rightButton.centerX()+decline_icon.getWidth()/2)+iconSize,
                (rightButton.centerY()+decline_icon.getHeight()/2)+iconSize);
        c.drawBitmap(decline_icon,null,icon_rect_decline,p);
        drawText("Decline",c,rightButton,icon_rect_decline,p);
        buttonInstance = null;

        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton;
        }
        else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton;
        }
    }
}
