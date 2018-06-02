package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.upgradeuser;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.FragmentAdminManageuserUngradeuserBinding;
import com.mvvm.kien2111.fastjob.model.BlockUser;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.ui.admin.dialog.BasicDialogAdmin;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser.AdapterListAllUser;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser.IUserClickItemList;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.edituser.UserEditProfileActivity;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.model.TriggerSentFragment;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.model.TriggerSentFragmentBlog;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.model.TriggerSentFragment;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.model.TriggerSentFragmentBlog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 3/7/2018.
 */

public class UngradeUserFragment extends BaseFragment<UngradeUserViewModel, FragmentAdminManageuserUngradeuserBinding> implements IUserClickItemList, BasicDialogAdmin.BasicDialogAdminListener {
    private AdapterListAllUser adapterListAllUser;
    private List<User> listUser= new ArrayList<>();
    private List<User> listUserUnblock = new ArrayList<>();
    private BasicDialogAdmin basicDialogAdminProgress;

    @Override
    protected UngradeUserViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(UngradeUserViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
        if (message instanceof UngradeUserViewModel.ResponseUnBlockServer){
            basicDialogAdminProgress.dismiss();
            if(message.getCause()!=null){
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("UnBlock error!!");
                basicDialogAdmin.setTitle("UnBlock User")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .setmListener(this)
                        .show();

            }
            else{
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("UnBlock success!!");
                basicDialogAdmin.setTitle("UnBlock User")
                        .setSigleTitleButton("OK")
                        .setView(view)
                        .setmListener(this)
                        .show();
            }
        }
        if(message instanceof TriggerSentFragment){
            for(int i = 0; i<((TriggerSentFragmentBlog) message).getList().size(); i++){
                listUser.add(((TriggerSentFragmentBlog) message).getList().get(i));
                adapterUser();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_admin_manageuser_ungradeuser;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mViewModel.getResourceMutableLiveData().observe(this, listResource -> {
            switch (listResource.status) {
                case SUCCESS:
                    if(listResource.getData().size()>0 && listResource.getData()!=null) {
                        listUser=listResource.getData();
                        adapterUser();
                    }
                    break;
                case LOADING:
                    Log.d("adaad", "ádfasdfsdf800800");
                    break;
                case ERROR:
                    Log.d("adaad", "ádfasdfsdf");
                    //handleError(listResource.message);
                    break;
            }
        });
        return view;
    }

    //call unlock user
    public void callUnlockUser() {

        if (adapterListAllUser != null) {

            basicDialogAdminProgress= new BasicDialogAdmin(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_dialog_progressbar,null,false);
            basicDialogAdminProgress.setView(view)
                    .show();
            List<BlockUser> listUser = adapterListAllUser.getlistUser();
            if (listUser != null && listUser.size() > 0) {
                mViewModel.unlockUser(listUser);
            }
        }
    }
    void adapterUser(){
        adapterListAllUser = new AdapterListAllUser(getContext(), listUser, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mFragmentBinding.rcUngradeUsermanage.setLayoutManager(mLayoutManager);
        mFragmentBinding.rcUngradeUsermanage.setItemAnimator(new DefaultItemAnimator());
        mFragmentBinding.rcUngradeUsermanage.setAdapter(adapterListAllUser);
    }

    //click item list recycleview
    @Override
    public void onClickItemList(User user) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        Intent intent = new Intent(getContext(), UserEditProfileActivity.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    @Override
    public void onClickOK() {
        for(BlockUser userunblock : adapterListAllUser.getlistUser()){
            for(int i=0;i<listUser.size();i++){
                if(listUser.get(i).getUserId()== userunblock.getIduser()){
                    listUserUnblock.add(listUser.get(i));
                    listUser.remove(listUser.get(i));
                }
            }
        }
        Log.d("datta2",(listUserUnblock.get(0).getUserName()));
        adapterUser();
        eventBus.post(new TriggerSentFragment(listUserUnblock));
    }

    @Override
    public void onClickCancel() {

    }
}
