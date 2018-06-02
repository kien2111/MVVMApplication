package com.mvvm.kien2111.fastjob;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by WhoAmI on 04/02/2018.
 */
@Singleton
public class AppExecutors {


    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public MainThreadExecutor getMainThread() {
        return mainThread;
    }

    public AppExecutors(Executor diskIO, Executor networkIO, MainThreadExecutor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    @Inject
    public AppExecutors(){
        this(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainThreadExecutor());
    }

    private final Executor diskIO;
    private final Executor networkIO;
    private final MainThreadExecutor mainThread;
    public static class MainThreadExecutor implements Executor{
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }
}
