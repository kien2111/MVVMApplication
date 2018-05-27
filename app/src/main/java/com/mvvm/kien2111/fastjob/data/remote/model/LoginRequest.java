package com.mvvm.kien2111.fastjob.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 24/02/2018.
 */

public final class LoginRequest {
    private LoginRequest(){

    }
    public static class FacebookLoginRequest{
        @Expose
        @SerializedName("fb_access_token")
        private String fbAccessToken;

        @Expose
        @SerializedName("fb_user_id")
        private String fbUserId;

        public FacebookLoginRequest(String fbUserId, String fbAccessToken) {
            this.fbUserId = fbUserId;
            this.fbAccessToken = fbAccessToken;
        }
        public String getFbAccessToken() {
            return fbAccessToken;
        }

        public String getFbUserId() {
            return fbUserId;
        }
    }
    public static class GoogleLoginRequest {

        @Expose
        @SerializedName("google_user_id")
        private String googleUserId;

        public String getGoogleUserId() {
            return googleUserId;
        }

        public String getIdToken() {
            return idToken;
        }

        @Expose
        @SerializedName("google_id_token")
        private String idToken;

        public GoogleLoginRequest(String googleUserId, String idToken) {
            this.googleUserId = googleUserId;
            this.idToken = idToken;
        }
    }
    public static class ExpressLoginRequest{
        public ExpressLoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Expose
        @SerializedName("username")
        private String username;

        @Expose
        @SerializedName("password")
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }




    }
}
