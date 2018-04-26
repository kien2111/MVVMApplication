package com.mvvm.kien2111.mvvmapplication.retrofit;

import com.mvvm.kien2111.mvvmapplication.exception.ApiException;
import com.mvvm.kien2111.mvvmapplication.model.Resource;

import org.reactivestreams.Publisher;

import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by WhoAmI on 09/04/2018.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory
{
    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory()
    {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create()
    {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public CallAdapter get(Type returnType, Annotation[] annotations, Retrofit retrofit)
    {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter
    {
        private final Retrofit retrofit;
        private final CallAdapter wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter wrapped)
        {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType()
        {
            return wrapped.responseType();
        }

        @SuppressWarnings({"unchecked", "NullableProblems"})
        @Override
        public Object adapt(Call call)
        {
            Object adaptedCall = wrapped.adapt(call);

            if (adaptedCall instanceof Completable) {
                return ((Completable) adaptedCall).onErrorResumeNext(
                        throwable -> Completable.error(asRetrofitException(throwable)));
            }

            if (adaptedCall instanceof Single) {
                return ((Single) adaptedCall)
                        .onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource apply(Throwable throwable) throws Exception {
                        return Single.error(asRetrofitException(throwable));
                    }
                });
            }

            if (adaptedCall instanceof Observable) {
                return ((Observable) adaptedCall).onErrorResumeNext(
                        (Function<? super Throwable, ? extends ObservableSource>)
                                throwable -> Observable.error(asRetrofitException(throwable)));
            }
            if(adaptedCall instanceof Flowable){
                return ((Flowable)adaptedCall).onErrorResumeNext(new Function<Throwable, Publisher>() {
                    @Override
                    public Publisher apply(Throwable throwable) throws Exception {
                        return Flowable.error(asRetrofitException(throwable));
                    }
                });
            }

            throw new RuntimeException("Observable Type not supported");
        }

        private ApiException asRetrofitException(Throwable throwable)
        {
            // We had non-200 http error
            if (throwable instanceof HttpException)
            {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                try {
                    return ApiException.httpError(response.raw().request().url().toString(),
                            response, retrofit);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // A network error happened
            if (throwable instanceof IOException)
            {
                return ApiException.networkError((IOException) throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return ApiException.unExpectedError(throwable);
        }
    }
}