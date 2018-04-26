package com.mvvm.kien2111.mvvmapplication.model;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by WhoAmI on 19/04/2018.
 */

public interface FilterOption<T> {
    List<T> getFilter(List<T> listfullitemneedfiltered);
}
