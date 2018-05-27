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

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.FragmentAdminManageuserUngradeuserBinding;
import com.mvvm.kien2111.fastjob.model.BlockUser;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser.AdapterListAllUser;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser.IUserClickItemList;
import com.mvvm.kien2111.fastjob.ui.admin.user.fragment.edituser.UserEditProfileActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by donki on 3/7/2018.
 */

public class UngradeUserFragment extends BaseFragment<UngradeUserViewModel, FragmentAdminManageuserUngradeuserBinding> implements IUserClickItemList {
    private AdapterListAllUser adapterListAllUser;

    @Override
    protected UngradeUserViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(UngradeUserViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message) {
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
                        adapterListAllUser = new AdapterListAllUser(getContext(), listResource.getData(), this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        mFragmentBinding.rcUngradeUsermanage.setLayoutManager(mLayoutManager);
                        mFragmentBinding.rcUngradeUsermanage.setItemAnimator(new DefaultItemAnimator());
                        mFragmentBinding.rcUngradeUsermanage.setAdapter(adapterListAllUser);
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
            List<BlockUser> listUser = adapterListAllUser.getlistUser();
            if (listUser != null && listUser.size() > 0) {
                mViewModel.unlockUser(listUser);
            }
        }
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
}
