package com.mvvm.kien2111.mvvmapplication.dagger.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mvvm.kien2111.mvvmapplication.BuildConfig;
import com.mvvm.kien2111.mvvmapplication.MyApplication;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceHelper;
import com.mvvm.kien2111.mvvmapplication.data.remote.AdminService;
import com.mvvm.kien2111.mvvmapplication.data.remote.UserService;
import com.mvvm.kien2111.mvvmapplication.model.Gender;
import com.mvvm.kien2111.mvvmapplication.retrofit.EnvelopeConverterFactory;
import com.mvvm.kien2111.mvvmapplication.retrofit.serialize.EnumTypeDeserialize;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WhoAmI on 21/01/2018.
 */
@Module()
public class NetModule {


    // Constructor needs one parameter to instantiate.


    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(MyApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(MyApplication application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Gender.class,new EnumTypeDeserialize())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, PreferenceHelper preferenceHelper) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.READ_TIMEOUT,TimeUnit.MILLISECONDS)
                .writeTimeout(BuildConfig.WRITE_TIMEOUT,TimeUnit.MILLISECONDS)
                .authenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return null;
                    }
                })
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    UserService provideUserService(Retrofit retrofit){
        return retrofit.create(UserService.class);
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new EnvelopeConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    private final static int NUM_THREADS = 2;

    @Provides
    @Singleton
    EventBus provideEventBus(){
        return EventBus.builder()
                .logger(new Logger.AndroidLogger("EventBus::logger: "))
                .executorService(Executors.newFixedThreadPool(NUM_THREADS))
                .eventInheritance(true)
                .build();
    }
    @Provides
    @Singleton
    AdminService provideAdminService(Retrofit retrofit){
        return retrofit.create(AdminService.class);
    }
}
