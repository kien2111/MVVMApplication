package com.mvvm.kien2111.mvvmapplication.ui;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static org.junit.Assert.*;

/**
 * Created by WhoAmI on 28/03/2018.
 */
public class SplashActivityUnitTest {
    @Test
    public void testObservable(){
        Observable.empty()
                .delay(15000, TimeUnit.MILLISECONDS)
                .test()
                .awaitDone(15000, TimeUnit.MILLISECONDS)
                .assertComplete();
    }

}