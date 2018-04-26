package com.mvvm.kien2111.mvvmapplication.data.remote.model;

/**
 * Created by WhoAmI on 24/02/2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.model.User;

public class LoginResponse implements Parcelable {

    private String access_token;
    private User user;

    @Expose
    @SerializedName("fb_profile_pic_url")
    private String fbProfilePicUrl;

    @Expose
    @SerializedName("google_profile_pic_url")
    private String googleProfilePicUrl;
    @Expose
    @SerializedName("auth_token_type")
    private String auth_token_type;

    public LoginResponse(){

    }

    protected LoginResponse(Parcel in) {
        access_token = in.readString();
        fbProfilePicUrl = in.readString();
        googleProfilePicUrl = in.readString();
        auth_token_type = in.readString();
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getServerPicUrl(){
        return user.getAvatar();
    }

    public void setFbProfilePicUrl(String fbProfilePicUrl) {
        this.fbProfilePicUrl = fbProfilePicUrl;
    }

    public void setGoogleProfilePicUrl(String googleProfilePicUrl) {
        this.googleProfilePicUrl = googleProfilePicUrl;
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getFbProfilePicUrl() {
        return fbProfilePicUrl;
    }

    public String getGoogleProfilePicUrl() {
        return googleProfilePicUrl;
    }

    public String getAuth_token_type() {
        return auth_token_type;
    }

    public void setAuth_token_type(String auth_token_type) {
        this.auth_token_type = auth_token_type;
    }
    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(auth_token_type);
        dest.writeString(access_token);
        dest.writeString(fbProfilePicUrl);
        dest.writeString(googleProfilePicUrl);
    }


}
