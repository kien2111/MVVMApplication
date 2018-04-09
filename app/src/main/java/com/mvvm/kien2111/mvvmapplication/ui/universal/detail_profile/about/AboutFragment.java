package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.about;

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
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentAboutProfileBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 04/04/2018.
 */

public class AboutFragment extends BaseFragment<AboutViewModel,FragmentAboutProfileBinding> {
    private static String KEY_PROFILE_MODEL = "key profile model";
    public static AboutFragment newInstance(ProfileModel profileModel){
        AboutFragment aboutFragment = new AboutFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROFILE_MODEL,profileModel);
        aboutFragment.setArguments(bundle);
        return aboutFragment;
    }

    @Override
    protected AboutViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(AboutViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_profile;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
