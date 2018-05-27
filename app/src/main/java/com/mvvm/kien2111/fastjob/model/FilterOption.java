package com.mvvm.kien2111.fastjob.model;

import java.util.List;

/**
 * Created by WhoAmI on 19/04/2018.
 */

public interface FilterOption<T> {
    List<T> getFilter(List<T> listfullitemneedfiltered);
}
