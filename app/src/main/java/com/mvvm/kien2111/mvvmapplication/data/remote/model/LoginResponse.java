package com.mvvm.kien2111.mvvmapplication.data.remote.model;

/**
 * Created by WhoAmI on 24/02/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class LoginResponse {

    @Expose
    @SerializedName("access_token")
    private String accessToken;

    @Expose
    @SerializedName("fb_profile_pic_url")
    private String fbProfilePicUrl;

    @Expose
    @SerializedName("google_profile_pic_url")
    private String googleProfilePicUrl;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("server_profile_pic_url")
    private String serverProfilePicUrl;

    @Expose
    @SerializedName("email")
    private String userEmail;

    @Expose
    @SerializedName("user_id")
    private int userId;


    public void setFbProfilePicUrl(String fbProfilePicUrl) {
        this.fbProfilePicUrl = fbProfilePicUrl;
    }

    public void setGoogleProfilePicUrl(String googleProfilePicUrl) {
        this.googleProfilePicUrl = googleProfilePicUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServerProfilePicUrl(String serverProfilePicUrl) {
        this.serverProfilePicUrl = serverProfilePicUrl;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Expose
    @SerializedName("auth_token_type")
    private String auth_token_type;

    @Expose
    @SerializedName("user_name")
    private String userName;

    public String getAccessToken() {
        return accessToken;
    }

    public String getFbProfilePicUrl() {
        return fbProfilePicUrl;
    }

    public String getGoogleProfilePicUrl() {
        return googleProfilePicUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getServerProfilePicUrl() {
        return serverProfilePicUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
    public String getAuth_token_type() {
        return auth_token_type;
    }

    public void setAuth_token_type(String auth_token_type) {
        this.auth_token_type = auth_token_type;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
