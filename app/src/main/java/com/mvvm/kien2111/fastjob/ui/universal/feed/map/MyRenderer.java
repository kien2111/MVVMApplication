package com.mvvm.kien2111.fastjob.ui.universal.feed.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;

/**
 * Created by WhoAmI on 31/05/2018.
 */

public class MyRenderer extends DefaultClusterRenderer<ProfileModel> {
    public MyRenderer(Context context, GoogleMap map, ClusterManager<ProfileModel> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onClusterItemRendered(ProfileModel clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
        marker.setTag(clusterItem);
    }

    @Override
    protected void onBeforeClusterItemRendered(ProfileModel item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
    }
}
