package com.mvvm.kien2111.mvvmapplication.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mvvm.kien2111.mvvmapplication.BuildConfig;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.util.GlideApp;

/**
 * Created by WhoAmI on 08/03/2018.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void toggleShow(View view, boolean show){
        view.setVisibility(show? View.VISIBLE : View.GONE);
    }
    @BindingAdapter(value = {"firsttextColor","secondtextcolor"},requireAll = true)
    public static void setTwoColorTextSpan(TextView textView,
                                    @ColorInt int firstcolor,
                                    @ColorInt int secondColor){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(textView.getText());
        int indexofspacecharacter = textView.getText().toString().indexOf(" ");
        builder.setSpan(new ForegroundColorSpan(firstcolor),0,indexofspacecharacter,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(secondColor),indexofspacecharacter+1,textView.getText().length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    @BindingAdapter(value={"cirleImageUrl","defaultImg","errorImg"},requireAll = false)
    public static void setImageUrl(ImageView mCircleImageView, String url, Drawable defaultImg, Drawable error){
        GlideApp.with(mCircleImageView.getContext())
                .load(BuildConfig.IMG_URL+url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(error).placeholder(defaultImg).dontAnimate())
                .into(mCircleImageView);
    }

    @BindingAdapter(value={"imageStaticCircleUrl"},requireAll = false)
    public static void setCircleImageUrl(ImageView circleImage,String url){
        GlideApp.with(circleImage.getContext())
                .asBitmap()
                .load(BuildConfig.IMG_URL+url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(ContextCompat.getDrawable(circleImage.getContext(), R.drawable.errorimg))
                        .placeholder(ContextCompat.getDrawable(circleImage.getContext(),R.drawable.errorimg))
                        .dontAnimate())
                .into(circleImage);
    }

    @BindingAdapter(value={"imageUri","defaultImg","errorImg"},requireAll = false)
    public static void setImageUri(ImageView imageView, Uri uri, Drawable defaultImg, Drawable error){
        GlideApp.with(imageView.getContext())
                .load(uri)
                .apply(new RequestOptions()
                        .error(error).placeholder(defaultImg).dontAnimate())
                .into(imageView);
    }

}
