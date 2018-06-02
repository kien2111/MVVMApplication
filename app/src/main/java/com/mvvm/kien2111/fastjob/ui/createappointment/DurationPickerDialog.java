package com.mvvm.kien2111.fastjob.ui.createappointment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseDialog;
import com.mvvm.kien2111.fastjob.databinding.DialogNumberPickerBinding;

/**
 * Created by WhoAmI on 27/04/2018.
 */

public class DurationPickerDialog extends BaseDialog<DialogNumberPickerBinding> {
    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_number_picker;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        window.setLayout((int) (width * 0.9),(int)(height*0.5));
        window.setGravity(Gravity.CENTER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initializeDefaultData();
        return view;
    }

    private void initializeDefaultData() {
        //setTitle("NumberPicker");
        Button setButton =  mDialogBinding.setButton;
        Button dismissButton =  mDialogBinding.dismissButton;
        final NumberPicker hourPicker = mDialogBinding.hourPicker;
        final NumberPicker minutePicker = mDialogBinding.minutePicker;
        final TextView txt_minute = mDialogBinding.txtMinute;
        final TextView txt_hour = mDialogBinding.txtHour;

        int maxValue = 12;
        int minValue = 0;
        int stepSizeMinute = 5;
        minutePicker.setMaxValue(maxValue);
        minutePicker.setMinValue(minValue);
        minutePicker.setDisplayedValues(this.getActivity().getResources().getStringArray(R.array.minutePickerValue));
        minutePicker.setWrapSelectorWheel(false);
        txt_minute.setText(String.format("%02d",0));
        txt_hour.setText(String.format("%02d:",0));
        minutePicker.setOnValueChangedListener((picker,oldVal,newVal)->{
            txt_minute.setText(String.format("%02d",picker.getValue()*stepSizeMinute));
        });
        hourPicker.setMaxValue(12);
        hourPicker.setMinValue(0);

        hourPicker.setWrapSelectorWheel(false);
        hourPicker.setOnValueChangedListener((picker,oldVal,newVal)->{
            txt_hour.setText(String.format("%02d:",picker.getValue()));
        });
        setButton.setOnClickListener(v -> {
            //tv.setText(String.valueOf(np.getValue())); //set the value to textview
            if(listenerDurationChange!=null){
                listenerDurationChange.onSetDuration(txt_hour.getText().toString()+txt_minute.getText().toString());
            }
            dismiss();
        });
        dismissButton.setOnClickListener(v-> {
            dismiss(); // dismiss the dialog
        });
    }

    private ListenerDurationChange listenerDurationChange;

    public void setOnListenDurationChange(ListenerDurationChange listenerDurationChange) {
        this.listenerDurationChange = listenerDurationChange;
    }

    public interface ListenerDurationChange{
        void onSetDuration(String duration);
    }

}
