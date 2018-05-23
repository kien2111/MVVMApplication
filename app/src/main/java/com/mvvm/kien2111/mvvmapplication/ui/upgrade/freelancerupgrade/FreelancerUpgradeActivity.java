package com.mvvm.kien2111.mvvmapplication.ui.upgrade.freelancerupgrade;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityFreelancerUpgradeBinding;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.exception.LastestRequestUpgradeProfileExistException;
import com.mvvm.kien2111.mvvmapplication.exception.ValidationRequestUpgradeException;
import com.mvvm.kien2111.mvvmapplication.interfaces.ValidateUpgradeRequest;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.model.Profile;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.rule.AbstractFactoryRule;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.common.BaseIndicatorFragment;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.common.FragmentType;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.common.IndicatorPagerAdapter;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.common.RequestUpgradeHandleCurrentLogic;
import com.mvvm.kien2111.mvvmapplication.ui.upgrade.common.RequestUpgradeModel;
import com.rd.PageIndicatorView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class FreelancerUpgradeActivity extends BaseActivity<FreelancerUpgradeViewModel,ActivityFreelancerUpgradeBinding> {

    @Inject
    IndicatorPagerAdapter indicatorPagerAdapter;

    private RequestUpgradeModel lastestRequestUpgradeModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_freelancer_upgrade;
    }

    @Override
    protected FreelancerUpgradeViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(FreelancerUpgradeViewModel.class);
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUserData();
        getLastestRequest();
        setUpFragmentAdapter();
    }

    private void getLastestRequest() {
        mViewModel.getLastestRequest().observe(this,requestUpgradeModelResource -> {
            if(requestUpgradeModelResource!=null){
                lastestRequestUpgradeModel = requestUpgradeModelResource.getData();
            }
        });
    }

    private void setUpUserData() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                currentUser = user;

            }
        });
    }

    private void setUpFragmentAdapter() {
        PageIndicatorView pageIndicatorView = mActivityBinding.pageIndicatorView;
        pageIndicatorView.setSelection(0);
        mActivityBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewModel.getListMutableLiveListPakageData().observe(this,listResource -> {
            if(listResource!=null){
                switch (listResource.getStatus()){
                    case SUCCESS:
                        indicatorPagerAdapter.addFragment(BaseIndicatorFragment.newInstance(
                                FragmentType.FREELANCER_BASIC,listResource.getData().get(0)));
                        indicatorPagerAdapter.addFragment(BaseIndicatorFragment.newInstance(
                                FragmentType.FREELANCER_MEDIUM,listResource.getData().get(1)));
                        indicatorPagerAdapter.addFragment(BaseIndicatorFragment.newInstance(
                                FragmentType.FREELANCER_PREMIUM,listResource.getData().get(2)));
                        mActivityBinding.viewPager.setAdapter(indicatorPagerAdapter);
                        break;
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof BaseIndicatorFragment.UpgradeTaskMessage){
            doUpgradeTask((BaseIndicatorFragment.UpgradeTaskMessage)message);
        }else if(message instanceof FreelancerUpgradeViewModel.TaskUpgradeRequestMessage){
            if(message.getState()== BaseMessage.State.SUCCESS){
                switch (((FreelancerUpgradeViewModel.TaskUpgradeRequestMessage) message).taskUpgrade){
                    case UPGRADE_REQUEST:
                        showDialog("Thông báo",message.getMessage());
                        break;
                    case DELETE_LASTEST_REQUEST:
                        lastestRequestUpgradeModel = null;
                        showDialog("Thông báo",message.getMessage());
                        break;
                }
            }else{
                showDialog("Lỗi",message.getMessage());
            }
        }
    }

    private void doUpgradeTask(BaseIndicatorFragment.UpgradeTaskMessage message) {
        switch (message.getFragmentType()){
            case FREELANCER_BASIC:
                doUpgradeProfile(Priority.BASIC,message.getRequestUpdateModel());
                break;
            case FREELANCER_MEDIUM:
                doUpgradeProfile(Priority.MEDIUM,message.getRequestUpdateModel());
                break;
            case FREELANCER_PREMIUM:
                doUpgradeProfile(Priority.PREMIUM,message.getRequestUpdateModel());
                break;
        }
    }

    private void doUpgradeProfile(Priority level, RequestUpgradeModel requestUpgradeModel) {
        try{
            new RequestUpgradeHandleCurrentLogic(lastestRequestUpgradeModel,mViewModel.getPreferenceLiveData().getValue(),level){
                @Override
                protected void sendRequest() {
                    mViewModel.upgradeProfile(requestUpgradeModel);
                }
            }.validateBeforeRequest();
        }
        catch (LastestRequestUpgradeProfileExistException e){
            showLastestREquestUpgradeExceptionDialog(e.getMessage());
        }
        catch (ValidationRequestUpgradeException e){
            showDialog("Lỗi",e.getMessage());
        }
    }

    public void showLastestREquestUpgradeExceptionDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Lỗi")
                .setMessage(message)
                .setNegativeButton("Hủy",(dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("Đồng ý",((dialog, which) -> {
                    mViewModel.deleteLastestUpgradeRequest(mViewModel.getPreferenceLiveData().getValue().getUserId());
                    dialog.dismiss();
                })).create();
        alertDialog.show();
    }

}
