package com.mvvm.kien2111.fastjob.ui.universal.feed.map;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.BuildConfig;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.databinding.FragmentGoogleMapBinding;
import com.mvvm.kien2111.fastjob.model.Profile;
import com.mvvm.kien2111.fastjob.ui.universal.UniversalActivity;
import com.mvvm.kien2111.fastjob.ui.universal.common.NavigationController;
import com.mvvm.kien2111.fastjob.util.GlideApp;
import com.mvvm.kien2111.fastjob.util.ImageUtil;
import com.mvvm.kien2111.fastjob.util.PermissionUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by WhoAmI on 27/05/2018.
 */

public class MapFragment extends BaseFragment<MapViewModel, FragmentGoogleMapBinding> implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationListener,
        ClusterManager.OnClusterClickListener<ProfileModel>, ClusterManager.OnClusterInfoWindowClickListener<ProfileModel>, ClusterManager.OnClusterItemClickListener<ProfileModel>, ClusterManager.OnClusterItemInfoWindowClickListener<ProfileModel>, View.OnClickListener {
    private GoogleMap googleMap;
    private ClusterManager<ProfileModel> mClusterManager;
    private PopupWindow mPopupWindow;
    private LocationManager locationManager;
    private static final int LOCATION_PERMISSTION_REQUEST_CODE = 100;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    @Inject
    NavigationController navigationController;

    @Override
    protected MapViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MapViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_google_map;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        setUpGoogleMap(googleMap);
    }

    @Override
    public boolean onClusterClick(Cluster<ProfileModel> cluster) {
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<ProfileModel> cluster) {

    }

    @Override
    public boolean onClusterItemClick(ProfileModel profileModel) {
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(ProfileModel profileModel) {
        navigationController.navigateToDetailProfile(profileModel);
    }

    private void setUpGoogleMap(GoogleMap googleMap){
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            PermissionUtil.requestLocationPermission(this.getActivity(),LOCATION_PERMISSTION_REQUEST_CODE);
            return;
        }
        if (googleMap != null) {
            MapsInitializer.initialize(getContext());
            this.googleMap = googleMap;
            this.googleMap.setOnMapClickListener(this);
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.snippet("zxvzxvzxc");
            markerOptions.title("kien");
            markerOptions.position(new LatLng(33.110740332852835, -90.51875352090684));
            ProfileModel profileModel = new ProfileModel();
            profileModel.setAvatar("gd.jpg");
            profileModel.setRating(4.4f);
            mClusterManager = new ClusterManager<ProfileModel>(this.getContext(),googleMap,new MarkerManager(googleMap){
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            marker.showInfoWindow();
                        }
                    }, 200);
                    return super.onMarkerClick(marker);
                }
            });
            googleMap.setOnCameraIdleListener(mClusterManager);
            googleMap.setOnMarkerClickListener(mClusterManager);
            googleMap.setOnInfoWindowClickListener(mClusterManager);
            mClusterManager.setOnClusterClickListener(this);
            mClusterManager.setOnClusterInfoWindowClickListener(this);
            mClusterManager.setOnClusterItemClickListener(this);
            mClusterManager.setOnClusterItemInfoWindowClickListener(this);
            mClusterManager.setRenderer(new MyRenderer(this.getContext(),googleMap,mClusterManager));
            mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowAdapter(this.getContext()));
            googleMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setMapToolbarEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setScrollGesturesEnabled(true);
            googleMap.getUiSettings().setTiltGesturesEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            }
            setUpDataGoogleMap();
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.110740332852835, -90.51875352090684), 13f));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case LOCATION_PERMISSTION_REQUEST_CODE:
                if ((grantResults.length!=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    setUpGoogleMap(this.googleMap);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            break;
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setOnClick();
        return view;
    }

    private void setOnClick() {
        mFragmentBinding.showFilterBtn.setOnClickListener(this);
        mFragmentBinding.mapTypeBtn.setOnClickListener(this);
    }

    private void setUpDataGoogleMap() {
        mViewModel.getProfileLiveData().observe(this,listResource -> {
            if(listResource!=null &&
                    listResource.getData()!=null &&
                    listResource.getData().size()!=0 &&
                    this.googleMap!=null &&
                    mClusterManager!=null){
                mClusterManager.clearItems();
                mClusterManager.addItems(listResource.getData());
                mClusterManager.cluster();
            }
        });
        ((UniversalActivity)getActivity()).getFilterMapController().getFilterMapModelMutableLiveData().observe(this,filterMapModel -> {
            if(filterMapModel!=null){
                mViewModel.pickAnotherFilter(filterMapModel);
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mFragmentBinding.map != null) {
            mFragmentBinding.map.onCreate(null);
            mFragmentBinding.map.onResume();
            mFragmentBinding.map.getMapAsync(this);
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

        }
    }

    private void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }

    Circle circle;
    Marker marker;
    @Override
    public void onMapClick(LatLng latLng) {
        UniversalActivity activity =((UniversalActivity)getActivity());
        //googleMap.clear();
        if(activity.isFilterControllerVisible()){
            activity.hideFilterMap();
        }else{
            activity.getFilterMapController().notifyNewFilterMapCriteria(latLng.latitude,latLng.longitude);
            if(circle!=null)circle.remove();
            if(marker!=null)marker.remove();
            circle = googleMap.addCircle(new CircleOptions().center(latLng)
                    .radius(((UniversalActivity)getActivity()).getFilterMapController().getCurrentDistance())
                    .fillColor(0x30ff0000)
                    .strokeColor(Color.BLUE)
                    .strokeWidth(2));
            marker = googleMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(ImageUtil.bitmapDescriptorFromVector(this.getContext(),R.drawable.ic_home_12dp)));
            dropPinEffect(marker);

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13f);
        googleMap.animateCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_filter_btn:
                ((UniversalActivity)getActivity()).showFilterMap();
                break;
            case R.id.map_type_btn:
                openPopUpWindow();
                break;
        }
    }

    private void openPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.popup_window_layout,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton =  customView.findViewById(R.id.btn_close);
        ImageView mDefaultImg =  customView.findViewById(R.id.img_default);
        ImageView mSatellite =  customView.findViewById(R.id.img_satellite);
        ImageView mTerrain = customView.findViewById(R.id.img_terrain);
        TextView mDefaultTitle = customView.findViewById(R.id.default_title);
        TextView mSatelliteTitle = customView.findViewById(R.id.satellite_title);
        TextView mTerrainTitle = customView.findViewById(R.id.terrain_title);
        View.OnClickListener defaultclicklistener = v -> {
            mDefaultImg.setSelected(true);
            mDefaultTitle.setSelected(true);
            mSatellite.setSelected(false);
            mSatelliteTitle.setSelected(false);
            mTerrain.setSelected(false);
            mTerrainTitle.setSelected(false);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        };

        View.OnClickListener satelliteclicklistener = v -> {
            mDefaultImg.setSelected(false);
            mDefaultTitle.setSelected(false);
            mSatellite.setSelected(true);
            mSatelliteTitle.setSelected(true);
            mTerrain.setSelected(false);
            mTerrainTitle.setSelected(false);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        };

        View.OnClickListener terrainclicklistener = v -> {
            mDefaultImg.setSelected(false);
            mDefaultTitle.setSelected(false);
            mSatellite.setSelected(false);
            mSatelliteTitle.setSelected(false);
            mTerrain.setSelected(true);
            mTerrainTitle.setSelected(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        };

        mTerrainTitle.setOnClickListener(terrainclicklistener);
        mTerrain.setOnClickListener(terrainclicklistener);
        mDefaultImg.setOnClickListener(defaultclicklistener);
        mDefaultTitle.setOnClickListener(defaultclicklistener);
        mSatelliteTitle.setOnClickListener(satelliteclicklistener);
        mSatellite.setOnClickListener(satelliteclicklistener);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(view -> {
            // Dismiss the popup window
            mPopupWindow.dismiss();
        });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mFragmentBinding.mapTypeBtn, Gravity.CENTER,0,0);
    }
}
