package com.mvvm.kien2111.fastjob.interfaces;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public interface ValidateRule<T> {
    void execute(T dataNeedToValidate);
}
