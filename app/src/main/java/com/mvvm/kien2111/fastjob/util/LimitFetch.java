package com.mvvm.kien2111.fastjob.util;

import android.os.SystemClock;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.TimeUnit;

/**
 * Created by WhoAmI on 06/02/2018.
 */
//Should create this object once in repository
public final class LimitFetch<Key> {

    ArrayMap<Key,Long> timestamp = new ArrayMap<>();
    private final long timeout;
    public LimitFetch(int timeout, TimeUnit timeUnit){
        this.timeout = timeout;
    }
    public synchronized boolean shouldFetch(Key key){
        //each instance of this object allow one thread to access this method
        Long lastestFetched = timestamp.get(key);
        long now = SystemClock.currentThreadTimeMillis();
        if(lastestFetched!=null){
            //accept fetch from network and save it
            timestamp.put(key,now);
            return true;
        }
        return false;
    }
    public synchronized void reset(Key key){
        timestamp.remove(key);
    }
}
