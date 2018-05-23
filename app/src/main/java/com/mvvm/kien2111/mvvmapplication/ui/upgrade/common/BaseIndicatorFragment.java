package com.mvvm.kien2111.mvvmapplication.ui.upgrade.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.databinding.IncludeUpgradeLayoutBinding;

/**
 * Created by WhoAmI on 03/05/2018.
 */

public abstract class BaseIndicatorFragment extends Fragment implements View.OnClickListener{
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

    private FragmentType fragmentType;

    private IncludeUpgradeLayoutBinding mBinding;

    public FragmentType getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(FragmentType fragmentType) {
        this.fragmentType = fragmentType;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    public BaseIndicatorFragment(){}



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        setUpLayout();
        return mBinding.getRoot();
    }

    protected abstract int getLayoutId();


    private void setUpLayout() {
        mBinding.btn.setBackgroundColor(getBtnBackGroundColor());
        mBinding.mainContainer.setBackgroundColor(getBackGroundColorContainer());
        mBinding.title.setTextColor(getTitleColor());
        mBinding.btn.setTextColor(getBtnTextColor());
        mBinding.content.setText(getContentText());
        mBinding.btn.setText(getBtnText());
        mBinding.title.setText(getTitleText());
        mBinding.img.setImageResource(getImageResource());
        mBinding.content.setTextColor(getContentColor());
        /*mBinding.setVariable(BR.btn_bg_color,getArguments().getInt(KEY_BTN_BG_COLOR,0));
        mBinding.setVariable(BR.bg_color,getArguments().getInt(KEY_BG_COLOR,0));
        mBinding.setVariable(BR.title_color,getArguments().getInt(KEY_COLOR_TITLE,0));
        mBinding.setVariable(BR.btn_text_color,getArguments().getInt(KEY_BTN_TEXT_COLOR,0));
        mBinding.setVariable(BR.content,getArguments().getString(KEY_CONTENT,""));
        mBinding.setVariable(BR.price,getArguments().getDouble(KEY_PRICE,0d));
        mBinding.setVariable(BR.title,getArguments().getString(KEY_TITLE_CONTENT));
        mBinding.setVariable(BR.main_img,getArguments().getInt(KEY_IMG_MAIN,0));
        mBinding.setVariable(BR.content_color,getArguments().getInt(KEY_COLOR_CONTENT,0));*/
        mBinding.executePendingBindings();
    }

    protected abstract String getContentText();

    protected abstract int getImageResource();

    protected abstract String getTitleText();

    protected abstract String getBtnText();

    protected abstract int getContentColor();

    protected abstract int getBtnTextColor();

    protected abstract int getTitleColor();

    protected abstract int getBackGroundColorContainer();

    protected abstract int getBtnBackGroundColor();
}
