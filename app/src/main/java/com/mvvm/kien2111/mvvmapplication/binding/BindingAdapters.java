package com.mvvm.kien2111.mvvmapplication.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

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
}
