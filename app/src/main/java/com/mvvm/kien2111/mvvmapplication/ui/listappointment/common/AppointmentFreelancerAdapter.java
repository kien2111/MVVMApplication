package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.databinding.AppointmentItemBinding;
import com.mvvm.kien2111.mvvmapplication.databinding.HeaderItemExpandableBinding;
import com.mvvm.kien2111.mvvmapplication.util.AnimationUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by WhoAmI on 17/04/2018.
 */

public class AppointmentAdapter extends BaseAdapter<AppointmentModel,AppointmentItemBinding> {

    private final android.databinding.DataBindingComponent dataBindingComponent;
    private final DecimalFormat decimalFormat;
    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dateFormat;
    public AppointmentAdapter(FragmentBindingComponent fragmentBindingComponent){
        this.dataBindingComponent = fragmentBindingComponent;
        this.decimalFormat = buildDecimalFormat();
        this.timeFormat = buildTimeFormat();
        this.dateFormat = buildDateFormat();
        this.setHasStableIds(true);
    }

    private SimpleDateFormat buildTimeFormat() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault());
    }

    private SimpleDateFormat buildDateFormat(){
        return new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
    }

    private DecimalFormat buildDecimalFormat(){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        return decimalFormat;
    }

    @Override
    protected BaseViewHolder<AppointmentItemBinding> instantiateViewHolder(AppointmentItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected AppointmentItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.appointment_item,parent,false,dataBindingComponent);
    }

    @Override
    protected void bind(AppointmentItemBinding mBinding, AppointmentModel item) {
        mBinding.txtTime.setText(String.format("%s",timeFormat.format(item.getEstimmate_time())));
        mBinding.txtDepositMoney.setText(String.format("%s đ",decimalFormat.format(item.getDeposit_fee().getFee())));
        mBinding.txtStatusAppointment.setText(item.getStatus().getName());
        mBinding.txtNameReceiveAppointment.setText(item.getUser_who_receive_appointment().getRealname());
        mBinding.txtDate.setText(String.format("%s",dateFormat.format(item.getEstimmate_time())));
        mBinding.executePendingBindings();
    }

    @Override
    protected boolean areContentsTheSame(AppointmentModel olditem, AppointmentModel newitem) {
        return false;
    }

    @Override
    protected boolean areItemsTheSame(AppointmentModel olditem, AppointmentModel newitem) {
        return false;
    }

    public void removeItem(int position) {
        getLstData().remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(AppointmentModel item, int position) {
        getLstData().add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
