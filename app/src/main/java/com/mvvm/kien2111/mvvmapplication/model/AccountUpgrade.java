package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by donki on 5/15/2018.
 */

public class AccountUpgrade {
    private  int idrequest_update_profile;

    public AccountUpgrade(int idrequest_update_profile) {
        this.idrequest_update_profile = idrequest_update_profile;
    }

    public int getIdrequest_update_profile() {
        return idrequest_update_profile;
    }

    public void setIdrequest_update_profile(int idrequest_update_profile) {
        this.idrequest_update_profile = idrequest_update_profile;
    }
}
