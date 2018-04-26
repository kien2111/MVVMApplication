package com.mvvm.kien2111.mvvmapplication.ui.userprofile.invidual.pickcategory;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseDialog;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.databinding.DialogPickCategoryBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by WhoAmI on 23/04/2018.
 */

public class PickCategoryDialog extends BaseDialog<DialogPickCategoryBinding> implements View.OnClickListener,PickCategoryAdapter.ListenerClickCategory {
    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_pick_category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setUpRecycleViewData();
        setOnClick();
        return view;
    }

    private void setOnClick() {
        mDialogBinding.cancleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancle_button:
                this.dismiss();
                break;
        }
    }

    private void setUpRecycleViewData() {
        PickCategoryAdapter pickCategoryAdapter = new PickCategoryAdapter(new FragmentBindingComponent(this),this);
        pickCategoryAdapter.changeDataSet(getArguments().getParcelableArrayList(KEY_LIST_CATEGORY));
        mDialogBinding.recycleViewCategory.addItemDecoration(new DividerItemDecoration(this.getContext(),
                DividerItemDecoration.VERTICAL));
        mDialogBinding.recycleViewCategory.setAdapter(pickCategoryAdapter);
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

    @Override
    public void onClickCategory(Category category) {
        if(category!=null){
            if(listenerPickCategoryDialog!=null){
                listenerPickCategoryDialog.onClickCategory(category);
                this.dismiss();
            }
        }
    }

    private final static String KEY_LIST_CATEGORY = "key list category";

    public static PickCategoryDialog newInstance(List<Category> lstCategory){
        Bundle bundle = new Bundle();
        PickCategoryDialog pickCategoryDialog = new PickCategoryDialog();
        bundle.putParcelableArrayList(KEY_LIST_CATEGORY, (ArrayList<? extends Parcelable>) lstCategory);
        pickCategoryDialog.setArguments(bundle);
        return pickCategoryDialog;
    }

    public void setOnClickListenerPickCategoryDialog(ListenerPickCategoryDialog listenerPickCategoryDialog){
        this.listenerPickCategoryDialog = listenerPickCategoryDialog;
    }
    private ListenerPickCategoryDialog listenerPickCategoryDialog;

    public interface ListenerPickCategoryDialog{
        void onClickCategory(Category category);
    }

}
