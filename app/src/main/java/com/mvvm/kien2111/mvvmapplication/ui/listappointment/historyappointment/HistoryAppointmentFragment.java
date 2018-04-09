package com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentHistoryappointmentBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class HistoryAppointmentFragment extends BaseFragment<HistoryAppointmentViewModel,FragmentHistoryappointmentBinding> {
    private static String KEY_APPOINTMENT_USER = "key appointment user";
    public static HistoryAppointmentFragment newInstance(){
        HistoryAppointmentFragment fragment = new HistoryAppointmentFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected HistoryAppointmentViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(HistoryAppointmentViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_historyappointment;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
