package com.mvvm.kien2111.fastjob.ui.admin.statistical;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.ActivityAdminStatisticalBinding;
import com.mvvm.kien2111.fastjob.model.Income;
import com.mvvm.kien2111.fastjob.model.RequestStaticfy;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by donki on 3/9/2018.
 */

public class AdminStatisticalActivity extends BaseActivity<AdminStatisticalViewModel, ActivityAdminStatisticalBinding> implements IAdminStatisticalNavigator, DatePickerDialog.OnDateSetListener {
    private List<Income> listIncome = new ArrayList<>();
    private int flag = 0;
    private int mPosition = 0;
    @Inject
    Calendar calendarstart;
    @Inject
    Calendar calendarend;

    @Inject
    SimpleDateFormat simpleDateFormat;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_admin_statistical;
    }

    @Override
    protected AdminStatisticalViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(AdminStatisticalViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {

    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSpinner();
        getDataChartDefaul();
        mViewModel.getResourceLiveData().observe(this, listResource -> {
            switch (listResource.status) {
                case SUCCESS:
                    if (listResource.getData().size() > 0 && listResource.getData() != null) {
                        listIncome = listResource.getData();
                        setupChart();
                    }
                    break;
                case LOADING:
                    break;
                case ERROR:
                    handleError(listResource.message);
                    break;
            }
        });
        setupSpinner();
    }

    public void getDataChartDefaul(){
        Calendar caldefaulstart = Calendar.getInstance();
        Calendar caldefaulend =Calendar.getInstance();
        caldefaulstart.add(Calendar.DATE,-5);
        calendarstart= caldefaulstart;
        calendarend= caldefaulend;
        mActivityBinding.tvStarttime.setText(simpleDateFormat.format(calendarstart.getTime()));
        mActivityBinding.tvEndtime.setText(simpleDateFormat.format(caldefaulend.getTime()));
        getDataServer(null);
    }
    //show chart
    void setupChart() {
        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < listIncome.size(); i++) {
            labels.add(listIncome.get(i).getDate());
            BarEntry barEntry = new BarEntry(listIncome.get(i).getTotal(), i);
            entries.add(barEntry);
        }
        BarDataSet bardataset = new BarDataSet(entries, "$");
        BarData data = new BarData(labels, bardataset);
        mActivityBinding.barchart.setData(data);
        mActivityBinding.barchart.invalidate();
    }

    //set data
    public void getDataServer(View view) {
        RequestStaticfy requestStaticfy = new RequestStaticfy(calendarstart.getTime(), calendarend.getTime(), mPosition);
        mViewModel.filter(requestStaticfy);
    }


    public enum OptionFilter{
        BYDAY("By Day"),BYMONTH("By Month"),BYYEAR("By Year");
        private String name;
        OptionFilter(String name){this.name = name;}

        @Override
        public String toString() {
            return name;
        }
    }

    //show spinner
    public void setupSpinner() {
        ArrayAdapter<OptionFilter> dataAdapter = new ArrayAdapter<OptionFilter>(this,
                android.R.layout.simple_spinner_item, Arrays.asList(OptionFilter.values()));
        mActivityBinding.spinnerSelectDate.setAdapter(dataAdapter);

        mActivityBinding.spinnerSelectDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (((OptionFilter)parent.getSelectedItem()).ordinal()) {
                    case 0:
                        mPosition = 0;
                        break;
                    case 1:
                        mPosition = 1;
                        SimpleDateFormat sdfmonth= new SimpleDateFormat("MM/yyyy");
                        mActivityBinding.tvStarttime.setText(sdfmonth.format(calendarstart.getTime()));
                        mActivityBinding.tvEndtime.setText(sdfmonth.format(calendarend.getTime()));
                        break;
                    case 2:
                        mPosition = 2;
                        SimpleDateFormat sdfyear= new SimpleDateFormat("yyyy");
                        mActivityBinding.tvStarttime.setText(sdfyear.format(calendarstart.getTime()));
                        mActivityBinding.tvEndtime.setText(sdfyear.format(calendarend.getTime()));
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

    //set start time
    public void setstarttime(View view) {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, this,
                calendarstart.get(Calendar.YEAR), calendarstart.get(Calendar.MONTH), calendarstart.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setTitle("Select startday");
        pickerDialog.show();
        flag = 0;
    }

    //set start time
    public void setendtime(View view) {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, this,
                calendarend.get(Calendar.YEAR), calendarend.get(Calendar.MONTH), calendarend.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setTitle("Select end");
        pickerDialog.show();
        flag = 1;
    }

    //set data
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (flag == 0) {
            calendarstart.set(year, month, dayOfMonth);
            mActivityBinding.tvStarttime.setText(simpleDateFormat.format(calendarstart.getTime()));
        } else {
            calendarend.set(year, month, dayOfMonth);
            mActivityBinding.tvEndtime.setText(simpleDateFormat.format(calendarend.getTime()));
        }
    }

    //finish
    public void finish(View view) {
        finish();
    }

    //turn off activity
    @Override
    public void finishActivity() {
        finish();
    }

    static class MessageShowStatistify extends BaseMessage {
    }

    private void handleError(String message) {
    }
}
