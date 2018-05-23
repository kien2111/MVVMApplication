package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.databinding.AppointmentBusinessItemBinding;
import com.mvvm.kien2111.mvvmapplication.util.AnimationUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by WhoAmI on 01/05/2018.
 */

public class AppointmentBusinessAdapter extends BaseAdapter<AppointmentModel,AppointmentBusinessItemBinding> {
    //act as freelancer
    private final android.databinding.DataBindingComponent dataBindingComponent;
    private final DecimalFormat decimalFormat;
    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dateFormat;
    public AppointmentBusinessAdapter(FragmentBindingComponent fragmentBindingComponent){
        this.dataBindingComponent = fragmentBindingComponent;
        this.decimalFormat = buildDecimalFormat();
        this.timeFormat = buildTimeFormat();
        this.dateFormat = buildDateFormat();
        this.setHasStableIds(true);
    }

    @Override
    protected boolean areContentsTheSame(AppointmentModel olditem, AppointmentModel newitem) {
        return false;
    }

    @Override
    protected boolean areItemsTheSame(AppointmentModel olditem, AppointmentModel newitem) {
        return false;
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
    protected BaseViewHolder<AppointmentBusinessItemBinding> instantiateViewHolder(AppointmentBusinessItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected AppointmentBusinessItemBinding createBinding(ViewGroup parent) {
        AppointmentBusinessItemBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.appointment_business_item,parent,false,dataBindingComponent);
        mBinding.imvExpandClickView.setOnClickListener(v->{
            if(View.GONE == mBinding.contactContainer.getVisibility()){
                AnimationUtil.expand(mBinding.contactContainer);
                mBinding.imvExpandClickView.setImageResource(R.drawable.ic_expand_less_gray_24dp);
            }else{
                AnimationUtil.collapse(mBinding.contactContainer);
                mBinding.imvExpandClickView.setImageResource(R.drawable.ic_expand_more_gray_24dp);
            }
        });
        return mBinding;
    }

    @Override
    protected void bind(AppointmentBusinessItemBinding mBinding, AppointmentModel item) {
        mBinding.txtTime.setText(String.format("%s",timeFormat.format(item.getEstimmate_time())));
        mBinding.txtDepositMoney.setText(String.format("%s đ",decimalFormat.format(item.getDeposit_fee().getFee())));
        mBinding.txtStatusAppointment.setText(item.getStatus().getName());
        mBinding.txtNameReceiveAppointment.setText(item.getUser_who_receive_appointment().getRealname());
        mBinding.txtDate.setText(String.format("%s",dateFormat.format(item.getEstimmate_time())));
        mBinding.emailUserReceiveAppointment.setText(item.getUser_who_receive_appointment().getEmail());
        mBinding.phonenumberUserReceiveAppointment.setText(item.getUser_who_receive_appointment().getPhone_individual());
        dataBindingComponent.getFragmentBindingAdapter().setCircleImageUrl(
                mBinding.imvAvatar,item.getUser_who_receive_appointment().getAvatar());
        mBinding.setIdappointment(item.getIdappointment());
        mBinding.executePendingBindings();
    }
}
