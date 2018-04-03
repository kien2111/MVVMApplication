package com.mvvm.kien2111.mvvmapplication.ui.universal.notification;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.dagger.Injectable;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentNotificationBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class NotificationFragment extends BaseFragment<NotificationViewModel,FragmentNotificationBinding> implements Injectable{
    @Override
    protected NotificationViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(NotificationViewModel.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMnotification;
    }
}
