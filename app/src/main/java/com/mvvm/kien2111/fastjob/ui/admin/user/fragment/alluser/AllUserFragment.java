package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.FragmentAdminManageuserAlluserBinding;
import com.mvvm.kien2111.fastjob.model.BlockUser;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.ui.admin.dialog.BasicDialogAdmin;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.edituser.UserEditProfileActivity;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.model.TriggerSentFragment;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.model.TriggerSentFragmentBlog;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 3/7/2018.
 */

public class AllUserFragment extends BaseFragment<AllUserViewModel, FragmentAdminManageuserAlluserBinding> implements IAllUserNagivator, IUserClickItemList, BasicDialogAdmin.BasicDialogAdminListener {

    AdapterListAllUser adapterListAllUser;
    private List<User> listUser= new ArrayList<>();
    private List<User> listUserBlock = new ArrayList<>();
    private BasicDialogAdmin basicDialogAdminProgress;
    public static User userItem= new User();

    @Override
    protected AllUserViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(AllUserViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
        if (message instanceof AllUserViewModel.ResponseBlockServer) {
            basicDialogAdminProgress.dismiss();
            if(message.getCause()!=null){
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Block error!!");
                basicDialogAdmin.setTitle("Block User")
                        .setView(view)
                        .setSigleTitleButton("OK")
                        .setmListener(this)
                        .show();
            }
            else{
                BasicDialogAdmin basicDialogAdmin = new BasicDialogAdmin(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_view_dialog,null,false);
                TextView tv= view.findViewById(R.id.tv_content);
                tv.setText("Block success!!");
                basicDialogAdmin.setTitle("Block User")
                        .setSigleTitleButton("OK")
                        .setView(view)
                        .setmListener(this)
                        .show();
            }
        }
        if(message instanceof TriggerSentFragment){

            for(int i=0;i<((TriggerSentFragment) message).getList().size();i++){
                listUser.add(((TriggerSentFragment) message).getList().get(i));
                adapterUser();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_admin_manageuser_alluser;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMmanagealluser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getData();
        return view;
    }

    public void getData(){
        mViewModel.getResourceMutableLiveData().observe(this, listResource -> {
            if(listResource!=null){
                switch (listResource.status) {
                    case SUCCESS:
                        if(listResource.getData()!=null && listResource.getData().size()>0) {
                            listUser= listResource.getData();
                            adapterUser();
                        }
                        break;
                    case LOADING:
                        break;
                    case ERROR:
                        handleError(listResource.message);
                        break;
                }
            }
        });
    }

    void adapterUser(){
        adapterListAllUser = new AdapterListAllUser(getContext(), listUser, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mFragmentBinding.rcAllUsermanage.setLayoutManager(mLayoutManager);
        mFragmentBinding.rcAllUsermanage.setItemAnimator(new DefaultItemAnimator());
        mFragmentBinding.rcAllUsermanage.setAdapter(adapterListAllUser);
    }

    private void handleError(String message) {

    }


    //Block a list user
    public void callBlockUser() {
        if (adapterListAllUser != null) {
            basicDialogAdminProgress= new BasicDialogAdmin(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_dialog_progressbar,null,false);
            basicDialogAdminProgress.setView(view)
                    .show();

            List<BlockUser> listUser = adapterListAllUser.getListBlockUser();
            if (listUser != null && listUser.size() > 0) {
                mViewModel.blockUser(listUser);
            }
        }
    }
    @Override
    public void onClickItemList(User usersent) {
        userItem= usersent;
        /*Bundle bundle = new Bundle();
        bundle.putParcelable("data",usersent);
        Intent intent = new Intent(getContext(), UserEditProfileActivity.class);
        intent.putExtra("ParcelKey",bundle);
        startActivity(intent);*/

        Intent intent = new Intent(getContext(), UserEditProfileActivity.class);
        intent.putExtra("Property", usersent);
        startActivity(intent);
    }
    //  get user item
    public static User getUserItem(){
        return  userItem;
    }
    @Override
    public void onClickOK() {
        for(BlockUser userblock : adapterListAllUser.getListBlockUser()){
            for(int i=0;i<listUser.size();i++){
                if(listUser.get(i).getUserId()== userblock.getIduser()){
                    listUserBlock.add(listUser.get(i));
                    listUser.remove(listUser.get(i));
                }
            }
        }
        adapterUser();
        eventBus.post(new TriggerSentFragmentBlog(listUserBlock));
    }

    @Override
    public void onClickCancel() {

    }
}
