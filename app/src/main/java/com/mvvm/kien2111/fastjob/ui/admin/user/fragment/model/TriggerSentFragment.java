package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.model;

import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.model.User;

import java.util.List;

/**
 * Created by donki on 4/27/2018.
 */

public class TriggerSentFragment extends BaseMessage{
    List<User> list ;
    public  TriggerSentFragment(List<User> list){
        this.list=list;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
