package com.mvvm.kien2111.mvvmapplication.ui.listappointment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityListAppoitmentBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class ListAppointmentActivity extends BaseActivity<ListAppointmentViewModel,ActivityListAppoitmentBinding>{
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_list_appoitment;
    }

    @Override
    protected ListAppointmentViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(ListAppointmentViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.VMlistappointment;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        super.onCreate(savedInstanceState);
        DefaultStateViewActivity();
    }
    public void DefaultStateViewActivity(){
        List<ICommand> commands = new ArrayList<>();
        commands.add(new SwitchOnButton(mActivityBinding.onProgressButton));
        new ToggleButton(commands).toggle();
    }

    public void onClickOnProgressButton(View v){
        List<ICommand> commands = new ArrayList<>();
        commands.add(new SwitchOnButton(mActivityBinding.onProgressButton));
        commands.add(new SwitchOffButton(mActivityBinding.historyAppointmentButton));
        commands.add(new ChangeCurrentViewPager(mActivityBinding.viewPager,0));
        new ToggleButton(commands).toggle();
    }
    public void onClickHistoryAppointmentButton(View v){
        List<ICommand> commands = new ArrayList<>();
        commands.add(new SwitchOnButton(mActivityBinding.historyAppointmentButton));
        commands.add(new SwitchOffButton(mActivityBinding.onProgressButton));
        commands.add(new ChangeCurrentViewPager(mActivityBinding.viewPager,1));
        new ToggleButton(commands).toggle();
    }



    static class ToggleButton{
       private List<ICommand> takeCommand;
       public ToggleButton(List<ICommand> command){
           this.takeCommand = command;
       }
       public void toggle(){
           for(ICommand command :takeCommand){
               command.execute();
           }
       }

    }
    static class SwitchOnButton implements ICommand{
        private final Button targetView;
        public SwitchOnButton(Button targetView){
            this.targetView = targetView;
        }
        @Override
        public void execute() {
            this.targetView.setSelected(true);
            this.targetView.setActivated(true);
        }
    }
    static class ChangeCurrentViewPager implements ICommand{
        private final ViewPager viewPager;
        private final int switchPage;
        public ChangeCurrentViewPager(ViewPager viewPager,int switchPage){
            this.switchPage = switchPage;
            this.viewPager = viewPager;
        }

        @Override
        public void execute() {
            this.viewPager.setCurrentItem(switchPage);
        }
    }
    static class SwitchOffButton implements ICommand{
        private final Button targetView;
        public SwitchOffButton(Button button){
            this.targetView = button;
        }
        @Override
        public void execute() {
            this.targetView.setSelected(false);
            this.targetView.setActivated(false);
        }
    }
    interface ICommand{
        void execute();
    }
}
