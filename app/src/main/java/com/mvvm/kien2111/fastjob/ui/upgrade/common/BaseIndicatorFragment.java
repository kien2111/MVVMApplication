package com.mvvm.kien2111.fastjob.ui.upgrade.common;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.databinding.IncludeUpgradeLayoutBinding;
import com.mvvm.kien2111.fastjob.model.Pakage_Upgrade;
import com.mvvm.kien2111.fastjob.model.Priority;
import com.mvvm.kien2111.fastjob.model.User;
import com.mvvm.kien2111.fastjob.ui.upgrade.freelancerupgrade.FreelancerUpgradeViewModel;
import com.mvvm.kien2111.fastjob.util.StringUtil;
import com.mvvm.kien2111.fastjob.util.UpgradeProfileButton;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public class BaseIndicatorFragment extends BaseFragment<FreelancerUpgradeViewModel,IncludeUpgradeLayoutBinding> implements UpgradeProfileButton.OnClickUpgrade,View.OnClickListener{
    /*private static final String KEY_PRICE = "key price";
    private static final String KEY_CONTENT = "key content";
    private static final String KEY_TITLE_CONTENT = "key title content";
    private static final String KEY_COLOR_TITLE = "key color title";
    private static final String KEY_BTN_BG_COLOR = "key btn bg color";
    private static final String KEY_BG_COLOR = "key background color";
    private static final String KEY_BTN_TEXT_COLOR = "key btn text color";
    private static final String KEY_IMG_MAIN = "key img main";
    private static final String KEY_COLOR_CONTENT = "key color content";*/

    /*public static IndicatorFragment newInstance(
            FragmentType fragmentType,
            Double price,
            String content,
            String title_content,
            Integer color_title,
            Integer btn_bg_color,
            Integer bg_color,
            Integer btn_text_color,
            Integer img_main,
            Integer color_content
    ){
        IndicatorFragment indicatorFragment = new IndicatorFragment();
        indicatorFragment.fragmentType = fragmentType;
        Bundle bundle = new Bundle();
        bundle.putDouble(KEY_PRICE,price);
        bundle.putString(KEY_CONTENT,content);
        bundle.putString(KEY_TITLE_CONTENT,title_content);
        bundle.putInt(KEY_COLOR_TITLE,color_title);
        bundle.putInt(KEY_BTN_BG_COLOR,btn_bg_color);
        bundle.putInt(KEY_BG_COLOR,bg_color);
        bundle.putInt(KEY_BTN_TEXT_COLOR,btn_text_color);
        bundle.putInt(KEY_IMG_MAIN,img_main);
        bundle.putInt(KEY_COLOR_CONTENT,color_content);
        indicatorFragment.setArguments(bundle);
        return indicatorFragment;
    }*/


    private static final String KEY_TYPE = "key type";
    private static final String KEY_PAKAGE_UPGRADE = "key pakage upgrade";

    private static final String KEY_USER_DATA = "key user data";
    public static BaseIndicatorFragment newInstance(FragmentType type
    , Pakage_Upgrade pakage_upgrade){
        BaseIndicatorFragment fragment = new BaseIndicatorFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_TYPE,type);
        bundle.putParcelable(KEY_PAKAGE_UPGRADE,pakage_upgrade);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected FreelancerUpgradeViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(FreelancerUpgradeViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_upgrade_layout;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    private FragmentType fragmentType;

    @Override
    public void onClick(View v) {
        switch (fragmentType){
            case FREELANCER_BASIC:
                eventBus.post(new UpgradeTaskMessage(fragmentType,
                        new RequestUpgradeModel(mViewModel.getPreferenceLiveData().getValue().getUserId(),
                                ((Pakage_Upgrade)getArguments().getParcelable(KEY_PAKAGE_UPGRADE)).getIdpakage_update(),
                                Priority.BASIC)));
                break;
            case FREELANCER_MEDIUM:
                eventBus.post(new UpgradeTaskMessage(fragmentType,
                        new RequestUpgradeModel(mViewModel.getPreferenceLiveData().getValue().getUserId(),
                                ((Pakage_Upgrade)getArguments().getParcelable(KEY_PAKAGE_UPGRADE)).getIdpakage_update(),
                                Priority.MEDIUM)));
                break;
            case FREELANCER_PREMIUM:
                eventBus.post(new UpgradeTaskMessage(fragmentType,
                        new RequestUpgradeModel(mViewModel.getPreferenceLiveData().getValue().getUserId(),
                                ((Pakage_Upgrade)getArguments().getParcelable(KEY_PAKAGE_UPGRADE)).getIdpakage_update(),
                                Priority.PREMIUM)));
                break;

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public BaseIndicatorFragment(){}

    protected View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
        setUpLayout();
        return v;
    }

    private void setUpLayout() {
        try{
            /*User user = ((User)getArguments().getParcelable(KEY_USER_DATA));


            setCurrrentLevelDrawableButton(user);
            mBinding.executePendingBindings();*/
            mFragmentBinding.setViewHolder( getArguments().getParcelable(KEY_TYPE));
            mFragmentBinding.title.setText((((Pakage_Upgrade)getArguments().getParcelable(KEY_PAKAGE_UPGRADE)).getPakage_name()));
            mFragmentBinding.btn.setText(String.format("%s đ", StringUtil.formatDecimal((((Pakage_Upgrade)getArguments().getParcelable(KEY_PAKAGE_UPGRADE)).getPakage_fee()))));
            mFragmentBinding.btn.setOnClickUpgrade(this);
            fragmentType = getArguments().getParcelable(KEY_TYPE);
            mViewModel.getPreferenceLiveData().observe(this,user -> {
                if(user!=null){
                    setCurrrentLevelDrawableButton(user);
                }
            });
            mViewModel.getLastestRequest().observe(this,requestUpdateModelResource -> {
                if(requestUpdateModelResource!=null){
                    setOnProcessButton(requestUpdateModelResource.getData());
                }else{
                    //ignore
                }
            });
            mFragmentBinding.executePendingBindings();
        }catch (Exception e){

        }
    }

    private void setCurrrentLevelDrawableButton(User user) {
        if(user.getProfile().getPriority()== Priority.BASIC && fragmentType==FragmentType.FREELANCER_BASIC){
            mFragmentBinding.btn.setDrawableFollowStatusRequestUpgrade(RequestUpgradeModel.Status_Request_Update_Profile.CURRENT_LEVEL);
        }else if(user.getProfile().getPriority()== Priority.MEDIUM && fragmentType==FragmentType.FREELANCER_MEDIUM){
            mFragmentBinding.btn.setDrawableFollowStatusRequestUpgrade(RequestUpgradeModel.Status_Request_Update_Profile.CURRENT_LEVEL);
        }else if(user.getProfile().getPriority()== Priority.PREMIUM && fragmentType==FragmentType.FREELANCER_PREMIUM){
            mFragmentBinding.btn.setDrawableFollowStatusRequestUpgrade(RequestUpgradeModel.Status_Request_Update_Profile.CURRENT_LEVEL);
        }else{
            //do nothing
        }
    }

    private void setOnProcessButton(RequestUpgradeModel requestUpgradeModel){
        if(requestUpgradeModel!=null){
            if(requestUpgradeModel.getLevel_expected()==Priority.BASIC && fragmentType==FragmentType.FREELANCER_BASIC){
                mFragmentBinding.btn.setDrawableFollowStatusRequestUpgrade(RequestUpgradeModel.Status_Request_Update_Profile.ON_PROCESS);
            }else if(requestUpgradeModel.getLevel_expected()==Priority.MEDIUM && fragmentType==FragmentType.FREELANCER_MEDIUM){
                mFragmentBinding.btn.setDrawableFollowStatusRequestUpgrade(RequestUpgradeModel.Status_Request_Update_Profile.ON_PROCESS);
            }else if(requestUpgradeModel.getLevel_expected()==Priority.PREMIUM && fragmentType==FragmentType.FREELANCER_PREMIUM){
                mFragmentBinding.btn.setDrawableFollowStatusRequestUpgrade(RequestUpgradeModel.Status_Request_Update_Profile.ON_PROCESS);
            }
        }else{
            //do nothing
        }


    }


    public static class UpgradeTaskMessage extends BaseMessage{
        FragmentType fragmentType;
        RequestUpgradeModel requestUpgradeModel;
        public UpgradeTaskMessage(FragmentType fragmentType,RequestUpgradeModel requestUpgradeModel){
            this.fragmentType = fragmentType;
            this.requestUpgradeModel = requestUpgradeModel;
        }

        public FragmentType getFragmentType() {
            return fragmentType;
        }

        public RequestUpgradeModel getRequestUpdateModel() {
            return requestUpgradeModel;
        }
    }
}
