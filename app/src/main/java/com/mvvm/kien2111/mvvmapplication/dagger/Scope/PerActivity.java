package com.mvvm.kien2111.mvvmapplication.dagger.Scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by WhoAmI on 11/02/2018.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Scope
public @interface PerActivity {
}
