package com.mvvm.kien2111.mvvmapplication.ui.universal.favouriteprofile;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.dagger.Injectable;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentFavouriteprofileBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class FavouriteProfileFragment extends BaseFragment<FavouriteProfileViewModel,FragmentFavouriteprofileBinding>  {

    @Override
    protected FavouriteProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(FavouriteProfileViewModel.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favouriteprofile;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMfavouriteprofile;
    }


}
