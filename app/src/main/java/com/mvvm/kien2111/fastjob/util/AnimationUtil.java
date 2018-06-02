package com.mvvm.kien2111.fastjob.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

import com.mvvm.kien2111.fastjob.R;

/**
 * Created by WhoAmI on 17/04/2018.
 */

public final class AnimationUtil {
    public static void expand(View v){
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation animation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(animation);
    }
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation animation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(animation);
    }

    public static void bothTranslateAndFade(View v){
        Animator animatorSet = AnimatorInflater.loadAnimator(v.getContext(), R.animator.slideright_and_fade);
        animatorSet.start();
    }
    public static void animationTranslateAndFade(View v){
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.slideright_and_fade);
        v.startAnimation(animation);
    }

    public static void loadToggleIn(View v){
        v.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.toggle_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation);
    }

    public static void loadToggleOut(View v){
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.toggle_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation);
    }

    public static void toggle_in(View view){
        view.setVisibility(View.VISIBLE);
        AnimationSet animationSet = new AnimationSet(true);
        Animation anim1 = new ScaleAnimation(
                0f, 0.8f, // Start and end values for the X axis scaling
                0f, 0.4f, // Start and end values for the Y axis scaling
                0, 0f, // Pivot point of X scaling
                0, 1f); // Pivot point of Y scaling
        anim1.setFillAfter(true); // Needed to keep the result of the animation
        anim1.setDuration(500);
        Animation anim2 = new ScaleAnimation(
                0.8f,1f,
                0.4f,1f,
                0,0f,
                0,1f
        );
        anim2.setFillAfter(true);
        anim2.setDuration(100);
        anim2.setStartOffset(500);

        animationSet.addAnimation(anim1);
        animationSet.addAnimation(anim2);
        view.startAnimation(animationSet);
    }

    public static void toggle_out(View view){
        AnimationSet animationSet = new AnimationSet(true);
        Animation anim1 = new ScaleAnimation(
                1.0f, 0.4f, // Start and end values for the X axis scaling
                1.0f, .8f, // Start and end values for the Y axis scaling
                0, 0f, // Pivot point of X scaling
                0, 1f); // Pivot point of Y scaling
        anim1.setFillAfter(true); // Needed to keep the result of the animation
        anim1.setDuration(500);
        Animation anim2 = new ScaleAnimation(
                .4f,0f,
                .8f,0f,
                0,0f,
                0,1f
        );
        anim2.setFillAfter(true);
        anim2.setDuration(100);
        anim2.setStartOffset(500);
        animationSet.addAnimation(anim1);
        animationSet.addAnimation(anim2);
        view.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void fade_in(View v){
        v.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.fade_in);
        v.startAnimation(animation);
    }

    public static void fade_content_out(View v){
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.fade_out_fragment);
        v.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void slide_in_up(View v){
        v.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.slide_in_up);
        v.startAnimation(animation);
    }

    public static void slide_out_down(View v){
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.slide_out_down);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation);
    }

    public static void fade_content_in(View v){
        v.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(v.getContext(),R.anim.fade_in_fragment);
        v.startAnimation(animation);
    }
}
