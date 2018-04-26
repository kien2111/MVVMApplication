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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 17/04/2018.
 */

public class AppointmentAdapter extends BaseAdapter<AppointmentModel,AppointmentItemBinding> {

    private final android.databinding.DataBindingComponent dataBindingComponent;
    public AppointmentAdapter(FragmentBindingComponent fragmentBindingComponent){
        this.dataBindingComponent = fragmentBindingComponent;
        this.setHasStableIds(true);
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
        mBinding.setAppointmentmodel(item);
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

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
