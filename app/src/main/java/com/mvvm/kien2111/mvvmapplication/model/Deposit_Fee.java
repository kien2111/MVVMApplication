package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by WhoAmI on 27/04/2018.
 */

public class Deposit_Fee {
    @SerializedName("iddeposit_fee")
    @Expose
    private Integer iddeposit;

    @SerializedName("fee")
    @Expose
    private Double fee;

    @SerializedName("created_at")
    @Expose
    private Timestamp created_at;

    @SerializedName("updated_at")
    @Expose
    private Timestamp updated_at;

    @SerializedName("apply")
    private Apply apply;

    public Integer getIddeposit() {
        return iddeposit;
    }

    public void setIddeposit(Integer iddeposit) {
        this.iddeposit = iddeposit;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    public enum Apply{
        APPLY_NOW(1),NOT_APPLY(0);
        private final int type;
        Apply(int type){
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static Apply mapApply(int type){
            for(Apply apply : values()){
                if(apply.getType()==type){
                    return apply;
                }
            }
            return NOT_APPLY;
        }
    }
}
