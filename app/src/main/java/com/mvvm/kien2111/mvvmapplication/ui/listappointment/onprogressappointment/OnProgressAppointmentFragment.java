package com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentOnprogressappointmentBinding;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentActivity;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class OnProgressAppointmentFragment extends BaseFragment<OnProgressAppointmentViewModel,FragmentOnprogressappointmentBinding>{

    @Inject
    AppointmentAdapter appointmentEmployerAdapter;

    @Inject
    AppointmentAdapter appointmentFreelancerAdapter;

    public static OnProgressAppointmentFragment newInstance(){
        OnProgressAppointmentFragment fragment = new OnProgressAppointmentFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof ListAppointmentActivity.PickOptionMessage){
            mViewModel.pickOption(
                    ((ListAppointmentActivity.PickOptionMessage) message).pickOption
            );
        }else{
            //error
        }
    }

    @Override
    protected OnProgressAppointmentViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(OnProgressAppointmentViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onprogressappointment;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Inject
    PreferenceHelper preferenceHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mViewModel.pickOption(new ListAppointmentViewModel.PickOption(
                ListAppointmentViewModel.PickOption.Option.FREELANCER,
                preferenceHelper.getUserData().getUser().getUserId()
        ));
        mViewModel.getHeaderName().set("Người làm việc tự do");
        setUpPickOption();
        setUpListAppointmentOnProgress();
        return view;
    }

    private void setUpPickOption() {
        mViewModel.getPickOptionMutableLiveData().observe(this,pickOption -> {
            if(pickOption!=null){
                if(pickOption.getOption() == ListAppointmentViewModel.PickOption.Option.FREELANCER){
                    mFragmentBinding.recycleViewAppointment
                            .swapAdapter(appointmentFreelancerAdapter,true);
                    mViewModel.getHeaderName().set("Người làm việc tự do");
                }else{
                    mFragmentBinding.recycleViewAppointment
                            .swapAdapter(appointmentEmployerAdapter,true);
                    mViewModel.getHeaderName().set("Nhà tuyển dụng");
                }
            }else{

            }
        });
    }

    @Inject
    DividerItemDecoration itemDecoration;

    private void setUpListAppointmentOnProgress() {
        mFragmentBinding.recycleViewAppointment.setAdapter(appointmentFreelancerAdapter);
        mFragmentBinding.recycleViewAppointment.addItemDecoration(itemDecoration);
        mFragmentBinding.recycleViewAppointment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(mViewModel.getPickOptionMutableLiveData().getValue().getOption()== ListAppointmentViewModel.PickOption.Option.FREELANCER){
                    if(lastPosition== appointmentFreelancerAdapter.getItemCount()-1){
                        mViewModel.loadNextPage();
                    }
                }else{
                    if(lastPosition== appointmentEmployerAdapter.getItemCount()-1){
                        mViewModel.loadNextPage();
                    }
                }
            }
        });
        mViewModel.getResourceLiveData().observe(this,listResource -> {
            if(mViewModel.getPickOptionMutableLiveData().getValue().getOption()== ListAppointmentViewModel.PickOption.Option.FREELANCER){
                appointmentFreelancerAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
            }else{
                appointmentEmployerAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
            }
            mFragmentBinding.executePendingBindings();
        });
        mViewModel.getLoadMoreStateLiveData().observe(this,loadMoreState -> {
            if(loadMoreState==null){
                mFragmentBinding.setLoadingMoreState(false);
            }else{
                mFragmentBinding.setLoadingMoreState(loadMoreState.isRunning);
            }
            mFragmentBinding.executePendingBindings();
        });
    }
}
