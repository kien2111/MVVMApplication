package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.databinding.AppointmentHistoryItemBinding;
import com.mvvm.kien2111.mvvmapplication.model.Option;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by WhoAmI on 07/05/2018.
 */

public class AppointmentHistoryAdapter extends BaseAdapter<AppointmentModel,AppointmentHistoryItemBinding> {

    private final FragmentBindingComponent fragmentBindingComponent;
    private final DecimalFormat decimalFormat;
    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dateFormat;
    private final Option option;
    public AppointmentHistoryAdapter(FragmentBindingComponent fragmentBindingComponent, Option option){
        this.fragmentBindingComponent = fragmentBindingComponent;
        this.decimalFormat = buildDecimalFormat();
        this.timeFormat = buildTimeFormat();
        this.dateFormat = buildDateFormat();
        this.option = option;
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
    protected BaseViewHolder<AppointmentHistoryItemBinding> instantiateViewHolder(AppointmentHistoryItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected AppointmentHistoryItemBinding createBinding(ViewGroup parent) {
        AppointmentHistoryItemBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.appointment_history_item,parent,false,fragmentBindingComponent);
        return mBinding;
    }

    @Override
    protected void bind(AppointmentHistoryItemBinding mBinding, AppointmentModel item) {
        mBinding.txtTime.setText(String.format("%s",timeFormat.format(item.getEstimmate_time())));
        mBinding.txtDepositMoney.setText(String.format("%s đ",decimalFormat.format(item.getDeposit_fee().getFee())));
        mBinding.txtStatusAppointment.setText(item.getStatus().getName());
        if(option == Option.EMPLOYER){
            mBinding.txtDisplayname.setText(item.getUser_who_receive_appointment().getRealname());
            fragmentBindingComponent.getFragmentBindingAdapter().setCircleImageUrl(
                    mBinding.imvAvatar,item.getUser_who_receive_appointment().getAvatar());
        }else if(option == Option.FREELANCER){
            mBinding.txtDisplayname.setText(item.getUser_who_create_appointment().getRealname());
            fragmentBindingComponent.getFragmentBindingAdapter().setCircleImageUrl(
                    mBinding.imvAvatar,item.getUser_who_create_appointment().getAvatar());
        }
        mBinding.txtDate.setText(String.format("%s",dateFormat.format(item.getEstimmate_time())));
        mBinding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return getLstData().get(position).hashCode();
    }

    @Override
    protected boolean areContentsTheSame(AppointmentModel olditem, AppointmentModel newitem) {
        return olditem==newitem;
    }

    @Override
    protected boolean areItemsTheSame(AppointmentModel olditem, AppointmentModel newitem) {
        return olditem.equals(newitem);
    }
}
