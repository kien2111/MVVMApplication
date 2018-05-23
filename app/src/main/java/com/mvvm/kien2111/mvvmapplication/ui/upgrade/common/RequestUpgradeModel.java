package com.mvvm.kien2111.mvvmapplication.ui.upgrade.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.model.Priority;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public class RequestUpgradeModel implements Parcelable{
    @SerializedName("profile_id")
    @Expose
    private String profile_id;

    @SerializedName("idpakage")
    @Expose
    private int idpakage;

    @SerializedName("approve")
    @Expose
    private Status_Request_Update_Profile status;

    @SerializedName("idrequest_update_profile")
    @Expose
    private int idrequest_update_profile;

    @SerializedName("level_expected")
    @Expose
    private Priority level_expected;

    public RequestUpgradeModel(String profile_id,
                               int idpakage,
                               Priority level_expected) {
        this.profile_id = profile_id;
        this.idpakage = idpakage;
        this.level_expected = level_expected;
    }


    protected RequestUpgradeModel(Parcel in) {
        profile_id = in.readString();
        idpakage = in.readInt();
        idrequest_update_profile = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profile_id);
        dest.writeInt(idpakage);
        dest.writeInt(idrequest_update_profile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RequestUpgradeModel> CREATOR = new Creator<RequestUpgradeModel>() {
        @Override
        public RequestUpgradeModel createFromParcel(Parcel in) {
            return new RequestUpgradeModel(in);
        }

        @Override
        public RequestUpgradeModel[] newArray(int size) {
            return new RequestUpgradeModel[size];
        }
    };

    public Priority getLevel_expected() {
        return level_expected;
    }

    public void setLevel_expected(Priority level_expected) {
        this.level_expected = level_expected;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public int getIdpakage() {
        return idpakage;
    }

    public void setIdpakage(int idpakage) {
        this.idpakage = idpakage;
    }

    public Status_Request_Update_Profile getStatus() {
        return status;
    }

    public void setStatus(Status_Request_Update_Profile status) {
        this.status = status;
    }

    public int getIdrequest_update_profile() {
        return idrequest_update_profile;
    }

    public void setIdrequest_update_profile(int idrequest_update_profile) {
        this.idrequest_update_profile = idrequest_update_profile;
    }



    public enum Status_Request_Update_Profile{
        ON_PROCESS(2),CURRENT_LEVEL(3),NORMAL(1),DECLINE(5);
        private int type;
        Status_Request_Update_Profile(int type){
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static Status_Request_Update_Profile mapStatusRequest(int type){
            for(Status_Request_Update_Profile request_update_profile : values()){
                if(type==request_update_profile.getType()){
                    return request_update_profile;
                }
            }
            return NORMAL;
        }
    }
}
