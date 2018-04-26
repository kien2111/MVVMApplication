package com.mvvm.kien2111.mvvmapplication.base;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public abstract class BaseDialog<VB extends ViewDataBinding> extends DialogFragment {
    protected BaseActivity mActivity;
    protected VB mDialogBinding;
    protected View rootView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            BaseActivity baseActivity = (BaseActivity)context;
            this.mActivity = baseActivity;
        }
    }
    protected android.databinding.DataBindingComponent fragmentBindingComponent = new FragmentBindingComponent(this);

    @Override
    public void onDetach() {
        mActivity=null;
        mDialogBinding.unbind();
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),getLayoutRes(),container,false,fragmentBindingComponent);
        rootView = mDialogBinding.getRoot();
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    protected abstract int getLayoutRes();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean isNetworkConnected(){
        return mActivity!=null && mActivity.isNetworkConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
