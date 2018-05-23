package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseDialog;
import com.mvvm.kien2111.mvvmapplication.binding.BindingAdapters;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.DialogRateBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.DetailProfileFragment;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by WhoAmI on 11/04/2018.
 */

public class RateDialog extends BaseDialog<DialogRateBinding>{
    public interface RateListener{
        void onDoRate(float average_point,String content);
        void onDismiss();
    }
    private static String KEY_PROFILE_MODEL = "key profile";
    public static RateDialog newInstance(ProfileModel profileModel){
        RateDialog rateDialog = new RateDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROFILE_MODEL,profileModel);
        rateDialog.setArguments(bundle);
        return rateDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;

        window.setLayout((int) (width * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);

        window.setGravity(Gravity.CENTER);
    }

    public void setOnListenRate(RateListener listenRate){
        this.rateListener = listenRate;
    }
    RateListener rateListener;
    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_rate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mDialogBinding.acceptBtn.setOnClickListener(v-> rateListener.onDoRate(mDialogBinding.ratting.getRating(),mDialogBinding.edtContent.getText().toString()));
        mDialogBinding.dismissBtn.setOnClickListener(v-> rateListener.onDismiss());
        ProfileModel profileModel = getArguments().getParcelable(KEY_PROFILE_MODEL);
        mDialogBinding.setProfilemodel(profileModel);
        BindingAdapters.setImageUrl(mDialogBinding.imvAvatar,
                profileModel.getAvatar(),
                ContextCompat.getDrawable(mActivity,R.drawable.defaultimage),
                ContextCompat.getDrawable(mActivity,R.drawable.errorimg));
        return view;
    }

}
