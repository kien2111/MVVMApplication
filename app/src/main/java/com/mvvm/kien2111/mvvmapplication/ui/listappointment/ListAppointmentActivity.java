package com.mvvm.kien2111.mvvmapplication.ui.listappointment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityListAppoitmentBinding;
import com.mvvm.kien2111.mvvmapplication.model.Option;
import com.mvvm.kien2111.mvvmapplication.model.Role;
import com.mvvm.kien2111.mvvmapplication.ui.SplashActivity;
import com.mvvm.kien2111.mvvmapplication.ui.admin.main.AdminMainActivity;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.historyappointment.HistoryAppointmentFragment;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.onprogressappointment.OnProgressAppointmentFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class ListAppointmentActivity extends BaseActivity<ListAppointmentViewModel,ActivityListAppoitmentBinding>
        implements PopupMenu.OnMenuItemClickListener{
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

    @Inject
    ViewPagerAdapter viewPagerAdapter;


    public void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this,v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        popupMenu.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.appointment_menu,popupMenu.getMenu());
        popupMenu.show();
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultStateViewActivity();
        setUpViewPagerData();
    }

    private void setUpViewPagerData() {
        viewPagerAdapter.addFragment(OnProgressAppointmentFragment.newInstance(),"On Progress Appointment");
        viewPagerAdapter.addFragment(HistoryAppointmentFragment.newInstance(),"History Appointment");
        mActivityBinding.viewPager.setAdapter(viewPagerAdapter);
        mActivityBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               switch (position){
                   case 0:
                       doOnOnProgressAppointmentPage();
                       break;
                   case 1:
                       doOnHistoryAppointmentPage();
                       break;
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void defaultStateViewActivity(){
        List<ICommand> commands = new ArrayList<>();
        commands.add(new SwitchOnButton(mActivityBinding.onProgressButton));
        new ToggleButton(commands).toggle();
    }

    public void onClickOnProgressButton(View v){
        doOnOnProgressAppointmentPage();
    }
    public void onClickHistoryAppointmentButton(View v){
        doOnHistoryAppointmentPage();
    }

    private void doOnOnProgressAppointmentPage(){
        List<ICommand> commands = new ArrayList<>();
        commands.add(new SwitchOnButton(mActivityBinding.onProgressButton));
        commands.add(new SwitchOffButton(mActivityBinding.historyAppointmentButton));
        commands.add(new ChangeCurrentViewPager(mActivityBinding.viewPager,0));
        new ToggleButton(commands).toggle();
    }

    private void doOnHistoryAppointmentPage(){
        List<ICommand> commands = new ArrayList<>();
        commands.add(new SwitchOnButton(mActivityBinding.historyAppointmentButton));
        commands.add(new SwitchOffButton(mActivityBinding.onProgressButton));
        commands.add(new ChangeCurrentViewPager(mActivityBinding.viewPager,1));
        new ToggleButton(commands).toggle();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_freelancer:
                eventBus.post(new PickRoleMessage(Option.FREELANCER));
                return true;
            case R.id.action_employer:
                eventBus.post(new PickRoleMessage(Option.EMPLOYER));
                return true;
        }
        return false;
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

    public static class PickRoleMessage extends BaseMessage{
        public Option option;
        public PickRoleMessage(Option option){this.option = option;}
    }
}
