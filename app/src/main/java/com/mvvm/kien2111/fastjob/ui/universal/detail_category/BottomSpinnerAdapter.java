package com.mvvm.kien2111.fastjob.ui.universal.detail_category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by WhoAmI on 24/04/2018.
 */

public class BottomSpinnerAdapter<T> extends ArrayAdapter<T> {
    private final List<T> data ;
    private Context context;
    public BottomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        this.data = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ((TextView)v).setText(data.get(position).toString());
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getDropDownView(position, convertView,parent);
        return v;
    }
}
