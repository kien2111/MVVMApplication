package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentAdminManageuserAlluserBinding;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentFavouriteprofileBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile.FavouriteProfileViewModel;

import java.util.ArrayList;

/**
 * Created by donki on 3/7/2018.
 */

public class AllUserFragment extends BaseFragment<AllUserViewModel,FragmentAdminManageuserAlluserBinding> implements IAllUserNagivator{
    @Override
    protected AllUserViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(AllUserViewModel.class);
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
        View view =super.onCreateView(inflater, container, savedInstanceState);

        ArrayList<String> listUser = new ArrayList<>();
        listUser.add("Khanh");
        listUser.add("Ninh");
        listUser.add("Khai");
        listUser.add("Khanh");
        listUser.add("Ninh");
        listUser.add("Khai");
        AdapterListAllUser adapterListAllUser = new AdapterListAllUser(getContext(),listUser);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mFragmentBinding.rcAllUsermanage.setLayoutManager(mLayoutManager);
        mFragmentBinding.rcAllUsermanage.setItemAnimator(new DefaultItemAnimator());
        mFragmentBinding.rcAllUsermanage.setAdapter(adapterListAllUser);
        return  view;
    }
}
