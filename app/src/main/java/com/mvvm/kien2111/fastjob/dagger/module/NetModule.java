package com.mvvm.kien2111.fastjob.dagger.module;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mvvm.kien2111.fastjob.BuildConfig;
import com.mvvm.kien2111.fastjob.MyApplication;
import com.mvvm.kien2111.fastjob.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.fastjob.data.remote.AdminService;
import com.mvvm.kien2111.fastjob.data.remote.AppointmentService;
import com.mvvm.kien2111.fastjob.data.remote.ProfileService;
import com.mvvm.kien2111.fastjob.data.remote.RateService;
import com.mvvm.kien2111.fastjob.data.remote.UserLoggedInService;
import com.mvvm.kien2111.fastjob.data.remote.UserService;
import com.mvvm.kien2111.fastjob.retrofit.EnumTypeAdapterFactory;
import com.mvvm.kien2111.fastjob.retrofit.EnvelopeConverterFactory;
import com.mvvm.kien2111.fastjob.retrofit.RxErrorHandlingCallAdapterFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
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
                .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    AccountManager provideAccountManager(MyApplication myApplication){
        return AccountManager.get(myApplication);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingService(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Provides
    @Named("protected_OkHttp")
    OkHttpClient provideProtectedOkHttpClient(MyApplication myApplication,Cache cache,
                                              HttpLoggingInterceptor httpLoggingInterceptor,
                                              AccountManager accountManager){
        /*if(!NetworkUtil.isNetworkConnected(myApplication.getApplicationContext())){
            throw new NetworkErrorException("No network exception");
        }*/
        final String[] auth_token = new String[2];
        accountManager.getAuthTokenByFeatures(AccountAuthenticator.ACCOUNT_TYPE,
                AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER, null, null, null, null,future -> {
                    try {
                        auth_token[0] = future.getResult().getString(AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER);
                        auth_token[1] = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                    } catch (OperationCanceledException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AuthenticatorException e) {
                        e.printStackTrace();
                    }
                },null);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                /*.connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.READ_TIMEOUT,TimeUnit.MILLISECONDS)
                .writeTimeout(BuildConfig.WRITE_TIMEOUT,TimeUnit.MILLISECONDS)*/
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException,SocketTimeoutException {
                        Request request = chain.request();
                        request = request.newBuilder()
                                .addHeader("authorization",auth_token[0]+" "+auth_token[1])
                                .build();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    @Named("no_protected_OkHttp")
    OkHttpClient provideOkHttpClient(MyApplication myApplication,Cache cache,HttpLoggingInterceptor httpLoggingInterceptor) {
        /*if(!NetworkUtil.isNetworkConnected(myApplication.getApplicationContext())){
            throw new NetworkErrorException("No network exception");
        }*/
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.READ_TIMEOUT,TimeUnit.MILLISECONDS)
                .writeTimeout(BuildConfig.WRITE_TIMEOUT,TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();
    }


    @Provides
    @Singleton
    UserService provideUserService(@Named("non_protected_api")Retrofit retrofit){
        return retrofit.create(UserService.class);
    }


    @Provides
    @Singleton
    @Named("non_protected_api")
    Retrofit provideRetrofit(Gson gson,@Named("no_protected_OkHttp")OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(new EnvelopeConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Named("protected_api")
    Retrofit provideProtectedRetrofit(Gson gson,@Named("protected_OkHttp") OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
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
    AdminService provideAdminService(@Named("protected_api") Retrofit retrofit){
        return retrofit.create(AdminService.class);
    }
    @Provides
    @Singleton
    ProfileService provideProfileService(@Named("non_protected_api")Retrofit retrofit){
        return retrofit.create(ProfileService.class);
    }
    @Provides
    @Singleton
    RateService provideRateService(@Named("protected_api")Retrofit retrofit){
        return retrofit.create(RateService.class);
    }
    @Provides
    @Singleton
    AppointmentService provideAppointmentService(@Named("protected_api")Retrofit retrofit){
        return retrofit.create(AppointmentService.class);
    }
    @Provides
    @Singleton
    UserLoggedInService provideUserLoggedInService(@Named("protected_api")Retrofit retrofit){
        return retrofit.create(UserLoggedInService.class);
    }
}
