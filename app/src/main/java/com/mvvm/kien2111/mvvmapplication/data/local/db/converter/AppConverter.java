package com.mvvm.kien2111.mvvmapplication.data.local.db.converter;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;


import com.google.gson.Gson;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.mvvmapplication.model.Approve_Publish;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.City;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.District;
import com.mvvm.kien2111.mvvmapplication.model.Deposit_Fee;
import com.mvvm.kien2111.mvvmapplication.model.Gender;
import com.mvvm.kien2111.mvvmapplication.model.Option;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
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
    public static List<Integer> integerToList(String data){
        if(data==null){
            return Collections.emptyList();
        }
        return StringUtil.splitToIntList(data);
    }
    @TypeConverter
    public static String IntListToString(List<Integer> ints){
        return StringUtil.joinIntoString(ints);
    }
    @TypeConverter
    public static String StringListToString(List<String> strings) {
        return StringUtil.joinStringoString(strings);
    }
    @TypeConverter
    public static Gender intToGender(int data){
        return Gender.mapGender(data);
    }
    @TypeConverter
    public static int GenderToInt(Gender gender){
        return gender.getType();
    }
    @TypeConverter
    public static int PriorityToInt(Priority priority){
        if(priority==null){
            return Priority.ALL.getType();
        }
        return priority.getType();
    }
    @TypeConverter
    public static Priority IntToPriority(int type){
        return Priority.mapPriority(type);
    }

    @TypeConverter
    public static RecentSearch.SearchType IntToSearchType(int type){
        return RecentSearch.SearchType.mapSearchType(type);
    }
    @TypeConverter
    public static int SearchTypeToInt(@NonNull RecentSearch.SearchType searchType){
        return searchType.getType();
    }

    @TypeConverter
    public static Approve_Publish IntToApprovePublish(int type){
        return Approve_Publish.mapApprovePublish(type);
    }
    @TypeConverter
    public static int ApprovePublishToInt(Approve_Publish approve_publish){
        return approve_publish.getType();
    }

    @TypeConverter
    public static String CategoryToString(Category category){
        return new Gson().toJson(category);
    }
    @TypeConverter
    public static Category StringToCategory(String json){
        return new Gson().fromJson(json,Category.class);
    }
    @TypeConverter
    public static User stringToUser(String json){
        return new Gson().fromJson(json,User.class);
    }
    @TypeConverter
    public static String userToString(User user){
        return new Gson().toJson(user);
    }

    @TypeConverter
    public static Option optionToInt(int type){
        return Option.mapOption(type);
    }

    @TypeConverter
    public static int IntToPickOption(Option option){
        return option.getType();
    }

    @TypeConverter
    public static ListAppointmentViewModel.PickOption pickOptionToString(String json){
        return new Gson().fromJson(json, ListAppointmentViewModel.PickOption.class);
    }
    @TypeConverter
    public static String stringTopickoption(ListAppointmentViewModel.PickOption pickOption){
        return new Gson().toJson(pickOption);
    }

    @TypeConverter
    public static City stringToCity(String json){
        return new Gson().fromJson(json,City.class);
    }

    @TypeConverter
    public static String cityToString(City city){
        return new Gson().toJson(city);
    }

    @TypeConverter
    public static District stringToDistrict(String json){
        return new Gson().fromJson(json,District.class);
    }

    @TypeConverter
    public static String districtToString(District district){
        return new Gson().toJson(district);
    }

    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
    @TypeConverter
    public static AppointmentModel.StatusAppointment intToStatusAppointment(int type){
        return AppointmentModel.StatusAppointment.mapType(type);
    }
    @TypeConverter
    public static int StatusAppointmentToInt(AppointmentModel.StatusAppointment statusAppointment){
        return statusAppointment.getType();
    }
    @TypeConverter
    public static Timestamp longToTimeStamp(Long time){
        if(time==null){
            return null;
        }else{
            return new Timestamp(time);
        }
    }
    @TypeConverter
    public static Long TimeStampToLong(Timestamp timestamp){
        if(timestamp==null){
            return null;
        }else{
            return timestamp.getTime();
        }
    }
    @TypeConverter
    public static String DepositFeeToString(Deposit_Fee deposit_fee){
        return new Gson().toJson(deposit_fee);
    }
    @TypeConverter
    public static Deposit_Fee StringToDepositFee(String json){
        return new Gson().fromJson(json,Deposit_Fee.class);
    }
}
