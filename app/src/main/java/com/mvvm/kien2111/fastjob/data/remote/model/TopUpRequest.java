package com.mvvm.kien2111.fastjob.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 29/04/2018.
 */

public class TopUpRequest {
    @SerializedName("serial_number")
    @Expose
    private String serial_number;

    @SerializedName("pincode")
    @Expose
    private String pincode;

    @SerializedName("iduser")
    @Expose
    private String iduser;

    public TopUpRequest(String iduser,String serial_number, String pincode) {
        this.iduser = iduser;
        this.serial_number = serial_number;
        this.pincode = pincode;

    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
