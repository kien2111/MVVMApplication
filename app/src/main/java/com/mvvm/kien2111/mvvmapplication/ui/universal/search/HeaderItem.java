package com.mvvm.kien2111.mvvmapplication.ui.universal.search;

/**
 * Created by WhoAmI on 02/04/2018.
 */

public class HeaderItem implements ExpandableChildItem {
    public HeaderItem(String nameheaderitem){
        this.nameheaderitem = nameheaderitem;
    }
    public String getNameheaderitem() {
        return nameheaderitem;
    }

    public void setNameheaderitem(String nameheaderitem) {
        this.nameheaderitem = nameheaderitem;
    }

    private String nameheaderitem;
}
