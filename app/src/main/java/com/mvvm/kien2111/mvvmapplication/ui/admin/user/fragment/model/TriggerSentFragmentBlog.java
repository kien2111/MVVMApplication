package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.model;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.List;

/**
 * Created by donki on 5/3/2018.
 */

public class TriggerSentFragmentBlog extends BaseMessage {
    List<User> list ;
    public  TriggerSentFragmentBlog(List<User> list){
        this.list=list;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
