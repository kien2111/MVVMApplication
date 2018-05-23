package com.mvvm.kien2111.mvvmapplication.ui.listappointment.common;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.databinding.AppointmentFreelancerItemBinding;
import com.mvvm.kien2111.mvvmapplication.util.AnimationUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by WhoAmI on 17/04/2018.
 */

public class AppointmentFreelancerAdapter extends BaseAdapter<AppointmentModel,AppointmentFreelancerItemBinding> {
    //act as business
    private final FragmentBindingComponent dataBindingComponent;
    private final DecimalFormat decimalFormat;
    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dateFormat;


    public AppointmentFreelancerAdapter(FragmentBindingComponent fragmentBindingComponent){
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
    protected BaseViewHolder<AppointmentFreelancerItemBinding> instantiateViewHolder(AppointmentFreelancerItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected AppointmentFreelancerItemBinding createBinding(ViewGroup parent) {
        AppointmentFreelancerItemBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.appointment_freelancer_item,parent,false,dataBindingComponent);
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
    protected void bind(AppointmentFreelancerItemBinding mBinding, AppointmentModel item) {
        mBinding.txtTime.setText(String.format("%s",timeFormat.format(item.getEstimmate_time())));
        mBinding.txtDepositMoney.setText(String.format("%s đ",decimalFormat.format(item.getDeposit_fee().getFee())));
        mBinding.txtStatusAppointment.setText(item.getStatus().getName());
        mBinding.txtNameCreateAppointment.setText(item.getUser_who_create_appointment().getRealname());
        mBinding.txtDate.setText(String.format("%s",dateFormat.format(item.getEstimmate_time())));
        mBinding.emailUserCreateAppointment.setText(item.getUser_who_create_appointment().getEmail());
        mBinding.phonenumberUserCreateAppointment.setText(item.getUser_who_create_appointment().getPhone_individual());
        dataBindingComponent.getFragmentBindingAdapter().setCircleImageUrl(
                mBinding.imvAvatar,item.getUser_who_create_appointment().getAvatar());
        mBinding.setIdappointment(item.getIdappointment());
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
