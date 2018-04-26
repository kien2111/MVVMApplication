package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

/**
 * Created by WhoAmI on 24/04/2018.
 */

public class ProfileRequest {
    public String query;
    public String filter;

    public ProfileRequest(String query, String filter) {
        this.query = query;
        this.filter = filter;
    }
}
