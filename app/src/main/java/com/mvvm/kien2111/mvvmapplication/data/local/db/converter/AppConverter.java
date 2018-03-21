package com.mvvm.kien2111.mvvmapplication.data.local.db.converter;

import android.arch.persistence.room.TypeConverter;


import com.mvvm.kien2111.mvvmapplication.model.Gender;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by WhoAmI on 20/03/2018.
 */

public class AppConverter {
    @TypeConverter
    public static List<String> stringToIntList(String data){
        if(data==null){
            return Collections.emptyList();
        }
        return StringUtil.splitToStringList(data);
    }
    @TypeConverter
    public static String StringListToString(List<String> ints) {
        return StringUtil.joinStringoString(ints);
    }
    @TypeConverter
    public static Gender intToGender(int data){
        switch (data){
            case 0:return Gender.MALE;
            case 1:return Gender.FEMAIL;
            case 2:return Gender.UNKNOWN;
            default:return Gender.MALE;
        }
    }
    @TypeConverter
    public static int GenderToInt(Gender gender){
        return gender.getType();
    }
    @TypeConverter
    public static int PriorityToInt(Priority priority){
        return priority.getType();
    }
    @TypeConverter
    public static Priority IntToPriority(int type){
        switch (type){
            case 0:return Priority.BASIC;
            case 1:return Priority.MEDIUM;
            case 2:return Priority.PREMIUM;
            default:return Priority.BASIC;
        }
    }
}
