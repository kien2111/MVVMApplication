package com.mvvm.kien2111.fastjob.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.fastjob.model.Approve_Publish;

/**
 * Created by WhoAmI on 28/04/2018.
 */

public class ApprovePublishRequest {
    @SerializedName("iduser")
    @Expose
    private String iduser;
    @SerializedName("approve_publish")
    @Expose
    private Approve_Publish approve_publish;

    public ApprovePublishRequest(String iduser, Approve_Publish approve_publish) {
        this.iduser = iduser;
        this.approve_publish = approve_publish;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public Approve_Publish getApprove_publish() {
        return approve_publish;
    }

    public void setApprove_publish(Approve_Publish approve_publish) {
        this.approve_publish = approve_publish;
    }
}
