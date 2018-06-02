package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.model;

import com.github.mikephil.charting.data.BarEntry;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;

import java.util.ArrayList;

/**
 * Created by donki on 5/4/2018.
 */

public class DataChart extends BaseMessage {
    private ArrayList<String> label;
    private ArrayList<BarEntry> entries;

    public DataChart(ArrayList label, ArrayList entries) {
        this.label = label;
        this.entries = entries;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {
        this.label = label;
    }

    public ArrayList<BarEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<BarEntry> entries) {
        this.entries = entries;
    }
}
