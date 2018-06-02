package com.mvvm.kien2111.mvvmapplication.model;

/**
 * Created by donki on 5/17/2018.
 */

public class UserUpdate  {
    private User user;
    private int idrole;

    public UserUpdate(User user, int idrole) {
        this.user = user;
        this.idrole = idrole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdrole() {
        return idrole;
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }
}
