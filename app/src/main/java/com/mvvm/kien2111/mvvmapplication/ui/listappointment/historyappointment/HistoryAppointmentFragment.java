package com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentHistoryappointmentBinding;
import com.mvvm.kien2111.mvvmapplication.model.Option;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentActivity;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentHistoryAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class HistoryAppointmentFragment extends BaseFragment<ListAppointmentViewModel,FragmentHistoryappointmentBinding> {

    @Named("business")
    @Inject
    AppointmentHistoryAdapter appointmentBusinessHistoryAdapter;

    @Named("freelancer")
    @Inject
    AppointmentHistoryAdapter appointmentFreelancerHistoryAdapter;

    private static String KEY_APPOINTMENT_USER = "key appointment user";
    public static HistoryAppointmentFragment newInstance(){
        HistoryAppointmentFragment fragment = new HistoryAppointmentFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof ListAppointmentActivity.PickRoleMessage){
            mViewModel.pickOption(
                    new ListAppointmentViewModel.PickOption(((ListAppointmentActivity.PickRoleMessage) message).option,
                            mViewModel.getPreferenceLiveData().getValue().getUserId(),
                            ListAppointmentViewModel.PickOption.HISTORY)
            );
        }else{
            switch (message.getState()){
                case SUCCESS:
                    ((BaseActivity)getActivity()).showDialog("Thông báo",message.getMessage());
                    break;
                case FAIL:
                    ((BaseActivity)getActivity()).showDialog("Lỗi",message.getMessage());
                    break;
            }
            //error
        }
    }

    @Override
    protected ListAppointmentViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(ListAppointmentViewModel.class);
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
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mViewModel.getHeaderName().set("Người làm việc tự do");
        setUpDefaultPickOption();
        setUpPickOption();
        setUpHistoryAdapter();
        return v;
    }

    private void setUpDefaultPickOption() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                mViewModel.pickOption(new ListAppointmentViewModel.PickOption(
                        Option.FREELANCER,
                        user.getUserId(),
                        ListAppointmentViewModel.PickOption.HISTORY
                ));
            }
        });
    }

    private void setUpPickOption() {
        mViewModel.getPickOptionMutableLiveData().observe(this,pickOption -> {
            if(pickOption!=null){
                if(pickOption.getOption() == Option.FREELANCER){
                    mFragmentBinding.recycleViewAppointment.swapAdapter(appointmentFreelancerHistoryAdapter,true);
                    //.swapAdapter(appointmentFreelancerAdapter,true);
                    mViewModel.getHeaderName().set("Người làm việc tự do");
                }else{
                    mFragmentBinding.recycleViewAppointment.swapAdapter(appointmentBusinessHistoryAdapter,true);
                    //.swapAdapter(appointmentEmployerAdapter,true);
                    mViewModel.getHeaderName().set("Nhà tuyển dụng");
                }
            }else{

            }
        });
    }

    private void setUpHistoryAdapter() {
        mFragmentBinding.recycleViewAppointment.setAdapter(appointmentFreelancerHistoryAdapter);
        mFragmentBinding.recycleViewAppointment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(mViewModel.getPickOptionMutableLiveData().getValue().getOption()== Option.FREELANCER){
                    if(lastPosition== appointmentFreelancerHistoryAdapter.getItemCount()-1){
                        mViewModel.loadHistoryNextPage();
                    }
                }else{
                    if(lastPosition== appointmentBusinessHistoryAdapter.getItemCount()-1){
                        mViewModel.loadHistoryNextPage();
                    }
                }
            }
        });
        mViewModel.getResourceHistoryLiveData().observe(this,listResource -> {
            if(mViewModel.getPickOptionMutableLiveData().getValue().getOption()== Option.FREELANCER){
                appointmentFreelancerHistoryAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
            }else{
                appointmentBusinessHistoryAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
            }
            if(listResource!=null && listResource.getData()!=null && listResource.getData().size()>0){
                mFragmentBinding.noavailabledata.setVisibility(View.GONE);
            }else{
                mFragmentBinding.noavailabledata.setVisibility(View.VISIBLE);
            }
            mFragmentBinding.executePendingBindings();
        });
        mViewModel.getLoadMoreHistoryStateLiveData().observe(this,loadMoreState -> {
            if(loadMoreState==null){
                mFragmentBinding.setLoadingMoreState(false);
            }else{
                mFragmentBinding.setLoadingMoreState(loadMoreState.isRunning);
            }
            mFragmentBinding.executePendingBindings();
        });
    }


}
