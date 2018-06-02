package com.mvvm.kien2111.mvvmapplication.ui.admin.statistical;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.model.Income;

import java.util.List;

/**
 * Created by donki on 5/24/2018.
 */

public class TriggerDataStatisfy extends BaseMessage {
    private List<Income> listIncome;

    public TriggerDataStatisfy(List<Income> listIncome) {
        this.listIncome = listIncome;
    }

    public List<Income> getListIncome() {
        return listIncome;
    }

    public void setListIncome(List<Income> listIncome) {
        this.listIncome = listIncome;
    }
}
