package com.mvvm.kien2111.fastjob.ui.upgrade.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.mvvm.kien2111.fastjob.R;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public enum FragmentType implements Parcelable{
    FREELANCER_BASIC(android.R.color.white,
            R.drawable.ripple_freelancer_basic,
            android.R.color.white,
            R.drawable.bronze_trophy,
            R.color.light_blue_material),
    FREELANCER_MEDIUM(android.R.color.white, R.drawable.ripple_freelancer_medium,android.R.color.white,R.drawable.silver_trophy,R.color.light_teal_material),
    FREELANCER_PREMIUM(android.R.color.white,R.drawable.ripple_freelancer_premium,android.R.color.white,R.drawable.gold_trophy,R.color.orange_material),
    BUSINESS_BASIC(android.R.color.white,R.drawable.ripple_freelancer_basic,android.R.color.white,R.drawable.bronze_trophy,R.color.blue_material),
    BUSINESS_MEDIUM(android.R.color.white,R.drawable.ripple_freelancer_basic,android.R.color.white,R.drawable.bronze_trophy,R.color.blue_material),
    BUSINESS_PREMIUM(android.R.color.white,R.drawable.ripple_freelancer_basic,android.R.color.white,R.drawable.bronze_trophy,R.color.blue_material);
    @ColorRes private int titlecolor;
    @ColorRes private int btnbgcolor;
    @ColorRes private int btntextcolor;
    @DrawableRes private int img;
    @ColorRes private int bg_color;

    FragmentType(@ColorRes int titlecolor,
                 @DrawableRes int btnbgcolor,
                 @ColorRes int btntextcolor,
                 @DrawableRes int img,
                 @ColorRes int bg_color) {
        this.titlecolor = titlecolor;
        this.btnbgcolor = btnbgcolor;
        this.btntextcolor = btntextcolor;
        this.img = img;
        this.bg_color = bg_color;
    }


    FragmentType(Parcel in) {
        titlecolor = in.readInt();
        btnbgcolor = in.readInt();
        btntextcolor = in.readInt();
        img = in.readInt();
        bg_color = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(titlecolor);
        dest.writeInt(btnbgcolor);
        dest.writeInt(btntextcolor);
        dest.writeInt(img);
        dest.writeInt(bg_color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FragmentType> CREATOR = new Creator<FragmentType>() {
        @Override
        public FragmentType createFromParcel(Parcel in) {
            return FragmentType.values()[in.readInt()];
        }

        @Override
        public FragmentType[] newArray(int size) {
            return new FragmentType[size];
        }
    };

    public int getTitlecolor() {
        return titlecolor;
    }

    public int getBtnbgcolor() {
        return btnbgcolor;
    }

    public int getBtntextcolor() {
        return btntextcolor;
    }

    public int getImg() {
        return img;
    }


    public int getBg_color() {
        return bg_color;
    }
}
