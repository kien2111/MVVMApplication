package com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.AppointmentTaskRequest;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentOnprogressappointmentBinding;
import com.mvvm.kien2111.mvvmapplication.model.Option;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentActivity;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentBusinessAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentFreelancerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentFreelancerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.RecyclerItemTouchHelper;
import com.mvvm.kien2111.mvvmapplication.util.ImageUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class OnProgressAppointmentFragment extends BaseFragment<ListAppointmentViewModel,FragmentOnprogressappointmentBinding> {

    @Inject
    AppointmentBusinessAdapter appointmentEmployerAdapter;

    @Inject
    AppointmentFreelancerAdapter appointmentFreelancerAdapter;

    public static OnProgressAppointmentFragment newInstance(){
        OnProgressAppointmentFragment fragment = new OnProgressAppointmentFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof ListAppointmentActivity.PickRoleMessage){
            mViewModel.pickOption(new ListAppointmentViewModel.PickOption(
                    ((ListAppointmentActivity.PickRoleMessage) message).option,
                    mViewModel.getPreferenceLiveData().getValue().getUserId(),
                    ListAppointmentViewModel.PickOption.ON_PROGRESS
            ));
        }else{
            switch (message.getState()){
                case SUCCESS:
                    ((BaseActivity)getActivity()).showDialog("Thông báo",message.getMessage());
                    break;
                case FAIL:
                    ((BaseActivity)getActivity()).showDialog("Lỗi",message.getMessage());
                    break;
            }
        }

        //error
    }

    @Override
    protected ListAppointmentViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(ListAppointmentViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onprogressappointment;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpDeffaultPickOption();

        mViewModel.getHeaderName().set("Người làm việc tự do");
        setUpPickOption();
        setUpListAppointmentOnProgress();
        return view;
    }

    private void setUpDeffaultPickOption() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                mViewModel.pickOption(new ListAppointmentViewModel.PickOption(
                        Option.FREELANCER,
                        user.getUserId(),
                        ListAppointmentViewModel.PickOption.ON_PROGRESS
                ));
            }
        });
    }

    private void setUpPickOption() {
        mViewModel.getPickOptionMutableLiveData().observe(this,pickOption -> {
            if(pickOption!=null){
                if(pickOption.getOption() == Option.FREELANCER){
                    mFragmentBinding.recycleViewAppointment.setAdapter(appointmentFreelancerAdapter);
                            //.swapAdapter(appointmentFreelancerAdapter,true);
                    mViewModel.getHeaderName().set("Người làm việc tự do");
                }else{
                    mFragmentBinding.recycleViewAppointment.setAdapter(appointmentEmployerAdapter);
                            //.swapAdapter(appointmentEmployerAdapter,true);
                    mViewModel.getHeaderName().set("Nhà tuyển dụng");
                }
            }else{

            }
        });
    }

    @Inject
    DividerItemDecoration itemDecoration;

    private void setUpListAppointmentOnProgress() {
        RecyclerItemTouchHelper itemTouchHelperCallback = new RecyclerItemTouchHelper(getContext(), new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                // backup of removed item for undo purpose
                //final AppointmentModel acceptedItem = cartList.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                //appointmentFreelancerAdapter.removeItem(viewHolder.getAdapterPosition());
                Toast.makeText(OnProgressAppointmentFragment.this.getActivity(),"onSwipe",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAcceptClick(int position) {
                mViewModel.acceptAppointment(
                        new AppointmentTaskRequest(appointmentFreelancerAdapter.getLstData().get(position).getIdappointment())
                );
                Toast.makeText(getActivity(),"onAcceptClick "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeclineClick(int position) {
                mViewModel.declineAppointment(
                        new AppointmentTaskRequest(appointmentFreelancerAdapter.getLstData().get(position).getIdappointment())
                );
                Toast.makeText(getActivity(),"onDeclineClick "+position,Toast.LENGTH_SHORT).show();
            }
        });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mFragmentBinding.recycleViewAppointment);
        mFragmentBinding.recycleViewAppointment.setAdapter(appointmentFreelancerAdapter);
        mFragmentBinding.recycleViewAppointment.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                itemTouchHelperCallback.onDraw(c);
            }
        });
        mFragmentBinding.recycleViewAppointment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(mViewModel.getPickOptionMutableLiveData().getValue().getOption()== Option.FREELANCER){
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
        mViewModel.getOnProgressResourceLiveData().observe(this,listResource -> {
            if(mViewModel.getPickOptionMutableLiveData().getValue().getOption()== Option.FREELANCER){
                appointmentFreelancerAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());

            }else{
                appointmentEmployerAdapter.changeDataSet(listResource==null? Collections.emptyList():listResource.getData());
            }
            if(listResource!=null && listResource.getData()!=null && listResource.getData().size()>0){
                mFragmentBinding.noavailabledata.setVisibility(View.GONE);
            }else{
                mFragmentBinding.noavailabledata.setVisibility(View.VISIBLE);
            }
            mFragmentBinding.executePendingBindings();
        });
        mViewModel.getLoadMoreOnProgressStateLiveData().observe(this,loadMoreState -> {
            if(loadMoreState==null){
                mFragmentBinding.setLoadingMoreState(false);
            }else{
                mFragmentBinding.setLoadingMoreState(loadMoreState.isRunning);
            }
            mFragmentBinding.executePendingBindings();
        });
    }
}
