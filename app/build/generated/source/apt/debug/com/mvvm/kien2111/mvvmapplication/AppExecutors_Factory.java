package com.mvvm.kien2111.mvvmapplication;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppExecutors_Factory implements Factory<AppExecutors> {
  private static final AppExecutors_Factory INSTANCE = new AppExecutors_Factory();

  @Override
  public AppExecutors get() {
    return new AppExecutors();
  }

  public static Factory<AppExecutors> create() {
    return INSTANCE;
  }
}
