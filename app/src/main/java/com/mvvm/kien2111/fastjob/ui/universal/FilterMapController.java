package com.mvvm.kien2111.fastjob.ui.universal;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.model.Priority;
import com.mvvm.kien2111.fastjob.ui.universal.feed.map.FilterMapModel;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by WhoAmI on 30/05/2018.
 */

public class FilterMapController implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private final MutableLiveData<FilterMapModel> filterMapModelMutableLiveData = new MutableLiveData<>();
    private Spinner level, unit;
    private TextView currentDistance, value10, value100, value1000, mLat, mLong, filter_btn;
    private SeekBar seekBar;
    private LocationManager locationManager;
    private ImageView hide_btn;
    private int valueclick = 10;
    private final DecimalFormat df;
    private Location location;
    private Activity context;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final int DEFAULT_VALUE_SEEKBAR = 50;
    public FilterMapController(Activity context,
                               Spinner level,
                               Spinner unit,
                               TextView filter_btn,
                               TextView currentDistance,
                               TextView value10,
                               TextView value100,
                               TextView value1000,
                               TextView mLat,
                               TextView mLong,
                               SeekBar seekBar,
                               ImageView hide_btn) {
        this.df = new DecimalFormat("0.000000");
        this.level = level;
        this.unit = unit;
        this.context = context;
        this.currentDistance = currentDistance;
        this.value10 = value10;
        this.hide_btn = hide_btn;
        this.value100 = value100;
        this.value1000 = value1000;
        this.mLat = mLat;
        this.mLong = mLong;
        this.seekBar = seekBar;
        this.filter_btn = filter_btn;
        initialize();
    }

    private void initialize() {
        this.seekBar.setOnSeekBarChangeListener(this);
        this.value10.setOnClickListener(this);
        this.value100.setOnClickListener(this);
        this.value1000.setOnClickListener(this);
        this.filter_btn.setOnClickListener(this);
        this.hide_btn.setOnClickListener(this);
        final ArrayAdapter<Priority> arrayPriorityAdapter = new ArrayAdapter<>(context,
                R.layout.spinner_layout_white_textview,
                Arrays.asList(Priority.values()));
        this.level.setAdapter(arrayPriorityAdapter);
        final ArrayAdapter<Unit> arrayUnitAdapter = new ArrayAdapter<>(context,
                R.layout.spinner_layout_white_textview,
                Arrays.asList(Unit.values()));
        this.unit.setAdapter(arrayUnitAdapter);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,
                MIN_DISTANCE, locationListener);
        this.location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
        if(location!=null){
            this.mLat.setText(df.format(location.getLatitude()));
            this.mLong.setText(df.format(location.getLongitude()));
            this.seekBar.setProgress(DEFAULT_VALUE_SEEKBAR);
            this.filterMapModelMutableLiveData.setValue(new FilterMapModel.Builder()
                    .priority(Priority.ALL)
                    .setLat(location.getLatitude())
                    .setLong(location.getLongitude())
                    .distance(valueclick*this.seekBar.getProgress())
                    .unit((Unit)this.unit.getSelectedItem())
                    .build());
        }
    }

    public void notifyNewFilterMapCriteria(double mlat,double mlong){
        this.filterMapModelMutableLiveData.setValue(new FilterMapModel.Builder()
                .priority((Priority) this.level.getSelectedItem())
                .setLat(mlat)
                .setLong(mlong)
                .distance(valueclick*this.seekBar.getProgress())
                .unit((Unit)this.unit.getSelectedItem())
                .build());
    }

    public double getCurrentDistance(){
        switch ((Unit)this.unit.getSelectedItem()){
            case METER:
                return seekBar.getProgress()*valueclick;
            case KILOMETER:
                return seekBar.getProgress()*valueclick*1000;
            case CENTIMETER:
                return seekBar.getProgress()*valueclick*0.01;
            default:return 0;
        }
    }

    public MutableLiveData<FilterMapModel> getFilterMapModelMutableLiveData() {
        return filterMapModelMutableLiveData;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLat.setText(df.format(location.getLatitude()));
            mLong.setText(df.format(location.getLongitude()));
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
    };

    public enum Unit{
        KILOMETER("KM"),METER("M"),MILES("MILES"),FOOT("FOOT"),CENTIMETER("CM"),YARD("YARDS");
        private String name;
        Unit(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Unit mapOrdinal(int indice){
            for(Unit unit : values()){
                if(unit.ordinal()==indice){
                    return unit;
                }
            }
            return Unit.KILOMETER;
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.currentDistance.setText(String.format("Current Distance: %d",progress*valueclick));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.value10:
                valueclick = 10;
                this.currentDistance.setText(String.format("Current Distance: %d",seekBar.getProgress()*valueclick));
                break;
            case R.id.value100:
                valueclick = 100;
                this.currentDistance.setText(String.format("Current Distance: %d",seekBar.getProgress()*valueclick));
                break;
            case R.id.value1000:
                valueclick = 1000;
                this.currentDistance.setText(String.format("Current Distance: %d",seekBar.getProgress()*valueclick));
                break;
            case R.id.filter_btn:
                break;
            case R.id.hide_btn:
                ((UniversalActivity)context).hideFilterMap();
                break;
        }
    }

}
