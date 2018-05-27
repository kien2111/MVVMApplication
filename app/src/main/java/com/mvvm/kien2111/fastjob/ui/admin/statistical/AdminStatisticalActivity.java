package com.mvvm.kien2111.fastjob.ui.admin.statistical;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.ActivityAdminStatisticalBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 3/9/2018.
 */

public class AdminStatisticalActivity extends BaseActivity<AdminStatisticalViewModel,ActivityAdminStatisticalBinding> implements IAdminStatisticalNavigator {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_statistical;
    }

    @Override
    protected AdminStatisticalViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(AdminStatisticalViewModel.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){


    }
    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupChart();
        setupSpinner();
    }
    //show chart
    void setupChart()
    {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(20f, 0));
        entries.add(new BarEntry(30f, 1));
        entries.add(new BarEntry(15f, 2));
        entries.add(new BarEntry(60f, 3));
        entries.add(new BarEntry(10f, 4));
        entries.add(new BarEntry(5, 5));

        BarDataSet bardataset = new BarDataSet(entries, "$");

        BarData data = new BarData(labels,bardataset);
        mActivityBinding.barchart.setData(data);
        mActivityBinding.barchart.invalidate();

    }
    //show spinner
    public  void setupSpinner()
    {
        List<String> list = new ArrayList<String>();
        list.add("By day");
        list.add("By month");
        list.add("By year");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,list);
        mActivityBinding.spinnerSelectDate.setAdapter(dataAdapter);

        mActivityBinding.spinnerSelectDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s= mActivityBinding.spinnerSelectDate.getSelectedItem().toString();
                switch (s){
                    case "By day":
                        break;
                    case "By month":
                        break;
                    case "By year":

                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //turn off activity
    @Override
    public void finishActivity() {
        finish();
    }
    static class MessageShowStatistify extends  BaseMessage{

    }
}
