package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 28/04/2018.
 */

public class TableExchangeModel {
    @SerializedName("nominal_value")
    @Expose
    private Double nominal_value;

    @SerializedName("amount_of_coin")
    @Expose
    private Double amount_of_coin;

    public TableExchangeModel(Double nominal_value, Double amount_of_coin) {
        this.nominal_value = nominal_value;
        this.amount_of_coin = amount_of_coin;
    }

    public Double getNominal_value() {
        return nominal_value;
    }

    public void setNominal_value(Double nominal_value) {
        this.nominal_value = nominal_value;
    }

    public Double getAmount_of_coin() {
        return amount_of_coin;
    }

    public void setAmount_of_coin(Double amount_of_coin) {
        this.amount_of_coin = amount_of_coin;
    }
}
