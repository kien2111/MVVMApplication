package com.mvvm.kien2111.fastjob.ui.universal.feed.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mvvm.kien2111.fastjob.BuildConfig;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.util.GlideApp;

/**
 * Created by WhoAmI on 28/05/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View mCustomInfoWindowView;
    private Context context;

    public CustomInfoWindowAdapter(Context context){
        this.context = context;
        mCustomInfoWindowView =((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_layout, null);}

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        ((TextView)mCustomInfoWindowView.findViewById(R.id.txt_name)).setText(marker.getTitle());
        ((TextView)mCustomInfoWindowView.findViewById(R.id.txt_summary)).setText(marker.getSnippet());
        ((TextView)mCustomInfoWindowView.findViewById(R.id.txt_rate_point)).setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_star_rate,0);
        ((TextView)mCustomInfoWindowView.findViewById(R.id.txt_rate_point)).setText(String.valueOf(((ProfileModel)marker.getTag()).getRating()));
        GlideApp.with(context)
                .asBitmap()
                .load(BuildConfig.IMG_URL+((ProfileModel)marker.getTag()).getAvatar())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ((ImageView)mCustomInfoWindowView.findViewById(R.id.profile_image)).setImageBitmap(resource);
                    }
                });
        return mCustomInfoWindowView;
    }
}
