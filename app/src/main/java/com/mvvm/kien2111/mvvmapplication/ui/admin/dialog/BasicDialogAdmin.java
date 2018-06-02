package com.mvvm.kien2111.mvvmapplication.ui.admin.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import javax.inject.Inject;

/**
 * Created by donki on 4/19/2018.
 */

public class BasicDialogAdmin {

    BasicDialogAdminListener mListener;
    AlertDialog.Builder builder;

    AlertDialog dialog;

    @Inject
    public BasicDialogAdmin(Context context) {
        builder = new AlertDialog.Builder(context);
    }

    public BasicDialogAdmin setmListener(BasicDialogAdminListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public interface BasicDialogAdminListener {
        void onClickOK();

        void onClickCancel();
    }

    public BasicDialogAdmin setView(View view) {
        this.builder.setView(view);
        return this;
    }


    public BasicDialogAdmin setTitle(String title) {
        this.builder.setTitle(title);
        return this;
    }

    public BasicDialogAdmin setTitleButton(String titleOK, String titleCancel) {
        this.builder.setNegativeButton(titleOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onClickOK();
                    dialog.dismiss();
                }
            }
        });

        this.builder.setPositiveButton(titleCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onClickCancel();
                    dialog.dismiss();
                }
            }
        });
        return this;
    }
    public BasicDialogAdmin setSigleTitleButton(String titleOK) {
        this.builder.setNegativeButton(titleOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onClickOK();
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    public void show() {
        dialog = builder.create();
        dialog.show();
    }
    public void cancel() {
        if(dialog == null){
            dialog = builder.create();
        }
        dialog.cancel();
    }
    public void dismiss(){
        if(dialog == null){
            dialog = builder.create();
        }
        dialog.dismiss();
    }
}
