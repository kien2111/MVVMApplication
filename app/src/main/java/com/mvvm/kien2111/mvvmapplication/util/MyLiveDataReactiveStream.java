package com.mvvm.kien2111.mvvmapplication.util;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
/**
 * Created by WhoAmI on 10/04/2018.
 */

public class MyLiveDataReactiveStream {
    private MyLiveDataReactiveStream() {
    }

    /**
     * Adapts the given {@link LiveData} stream to a ReactiveStreams {@link Publisher}.
     *
     * <p>
     * By using a good publisher implementation such as RxJava 2.x Flowables, most consumers will
     * be able to let the library deal with backpressure using operators and not need to worry about
     * ever manually calling {@link Subscription#request}.
     *
     * <p>
     * On subscription to the publisher, the observer will attach to the given {@link LiveData}.
     * Once {@link Subscription#request) is called on the subscription object, an observer will be
     * connected to the data stream. Calling request(Long.MAX_VALUE) is equivalent to creating an
     * unbounded stream with no backpressure. If request with a finite count reaches 0, the observer
     * will buffer the latest item and emit it to the subscriber when data is again requested. Any
     * other items emitted during the time there was no backpressure requested will be dropped.
     */
    @NonNull
    public static <T> Publisher<T> toPublisher(
            @NonNull LifecycleOwner lifecycle, @NonNull LiveData<T> liveData) {

        return new MyLiveDataReactiveStream.LiveDataPublisher<>(lifecycle, liveData);
    }

    private static final class LiveDataPublisher<T> implements Publisher<T> {
        final LifecycleOwner mLifecycle;
        final LiveData<T> mLiveData;

        LiveDataPublisher(LifecycleOwner lifecycle, LiveData<T> liveData) {
            this.mLifecycle = lifecycle;
            this.mLiveData = liveData;
        }

        @Override
        public void subscribe(Subscriber<? super T> subscriber) {
            subscriber.onSubscribe(new MyLiveDataReactiveStream.LiveDataPublisher.LiveDataSubscription<T>(subscriber, mLifecycle, mLiveData));
        }

        static final class LiveDataSubscription<T> implements Subscription, Observer<T> {
            final Subscriber<? super T> mSubscriber;
            final LifecycleOwner mLifecycle;
            final LiveData<T> mLiveData;

            volatile boolean mCanceled;
            // used on main thread only
            boolean mObserving;
            long mRequested;
            // used on main thread only
            @Nullable
            T mLatest;

            LiveDataSubscription(final Subscriber<? super T> subscriber,
                                 final LifecycleOwner lifecycle, final LiveData<T> liveData) {
                this.mSubscriber = subscriber;
                this.mLifecycle = lifecycle;
                this.mLiveData = liveData;
            }

            @Override
            public void onChanged(@Nullable T t) {
                if (mCanceled) {
                    return;
                }
                if (mRequested > 0) {
                    mLatest = null;
                    mSubscriber.onNext(t);
                    if (mRequested != Long.MAX_VALUE) {
                        mRequested--;
                    }
                } else {
                    mLatest = t;
                }
            }

            @Override
            public void request(final long n) {
                if (mCanceled) {
                    return;
                }
                ArchTaskExecutor.getInstance().executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mCanceled) {
                            return;
                        }
                        if (n <= 0L) {
                            mCanceled = true;
                            if (mObserving) {
                                mLiveData.removeObserver(MyLiveDataReactiveStream.LiveDataPublisher.LiveDataSubscription.this);
                                mObserving = false;
                            }
                            mLatest = null;
                            mSubscriber.onError(
                                    new IllegalArgumentException("Non-positive request"));
                            return;
                        }

                        // Prevent overflowage.
                        mRequested = mRequested + n >= mRequested
                                ? mRequested + n : Long.MAX_VALUE;
                        if (!mObserving) {
                            mObserving = true;
                            mLiveData.observe(mLifecycle, MyLiveDataReactiveStream.LiveDataPublisher.LiveDataSubscription.this);
                        } else if (mLatest != null) {
                            onChanged(mLatest);
                            mLatest = null;
                        }
                    }
                });
            }

            @Override
            public void cancel() {
                if (mCanceled) {
                    return;
                }
                mCanceled = true;
                ArchTaskExecutor.getInstance().executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mObserving) {
                            mLiveData.removeObserver(MyLiveDataReactiveStream.LiveDataPublisher.LiveDataSubscription.this);
                            mObserving = false;
                        }
                        mLatest = null;
                    }
                });
            }
        }
    }

    /**
     * Creates an Observable {@link LiveData} stream from a ReactiveStreams publisher.
     *
     * <p>
     * When the LiveData becomes active, it subscribes to the emissions from the Publisher.
     *
     * <p>
     * When the LiveData becomes inactive, the subscription is cleared.
     * LiveData holds the last value emitted by the Publisher when the LiveData was active.
     * <p>
     * Therefore, in the case of a hot RxJava Observable, when a new LiveData {@link Observer} is
     * added, it will automatically notify with the last value held in LiveData,
     * which might not be the last value emitted by the Publisher.
     * <p>
     * Note that LiveData does NOT handle errors and it expects that errors are treated as states
     * in the data that's held. In case of an error being emitted by the publisher, an error will
     * be propagated to the main thread and the app will crash.
     *
     * @param <T> The type of data hold by this instance.
     */
    @NonNull
    public static <T> LiveData<Resource<T>> fromPublisher(@NonNull Publisher<T> publisher) {
        return new MyLiveDataReactiveStream.PublisherLiveData<>(publisher);
    }

    /**
     * Defines a {@link LiveData} object that wraps a {@link Publisher}.
     *
     * <p>
     * When the LiveData becomes active, it subscribes to the emissions from the Publisher.
     *
     * <p>
     * When the LiveData becomes inactive, the subscription is cleared.
     * LiveData holds the last value emitted by the Publisher when the LiveData was active.
     * <p>
     * Therefore, in the case of a hot RxJava Observable, when a new LiveData {@link Observer} is
     * added, it will automatically notify with the last value held in LiveData,
     * which might not be the last value emitted by the Publisher.
     *
     * <p>
     * Note that LiveData does NOT handle errors and it expects that errors are treated as states
     * in the data that's held. In case of an error being emitted by the publisher, an error will
     * be propagated to the main thread and the app will crash.
     *
     * @param <T> The type of data hold by this instance.
     */
    private static class PublisherLiveData<T> extends LiveData<Resource<T>> {
        private final Publisher<T> mPublisher;
        final AtomicReference<MyLiveDataReactiveStream.PublisherLiveData.LiveDataSubscriber> mSubscriber;

        PublisherLiveData(@NonNull Publisher<T> publisher) {
            mPublisher = publisher;
            mSubscriber = new AtomicReference<>();
        }

        @Override
        protected void onActive() {
            super.onActive();
            MyLiveDataReactiveStream.PublisherLiveData.LiveDataSubscriber s = new MyLiveDataReactiveStream.PublisherLiveData.LiveDataSubscriber();
            mSubscriber.set(s);
            mPublisher.subscribe(s);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            MyLiveDataReactiveStream.PublisherLiveData.LiveDataSubscriber s = mSubscriber.getAndSet(null);
            if (s != null) {
                s.cancelSubscription();
            }
        }

        final class LiveDataSubscriber extends AtomicReference<Subscription>
                implements Subscriber<T> {

            @Override
            public void onSubscribe(Subscription s) {
                if (compareAndSet(null, s)) {
                    s.request(Long.MAX_VALUE);
                } else {
                    s.cancel();
                }
            }

            @Override
            public void onNext(T item) {
                postValue(Resource.success(item));
            }

            @Override
            public void onError(final Throwable ex) {
                postValue(Resource.error(null,ex));
                mSubscriber.compareAndSet(this,null);
                /*ArchTaskExecutor.getInstance().executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        // Errors should be handled upstream, so propagate as a crash.
                        throw new RuntimeException("LiveData does not handle errors. Errors from "
                                + "publishers should be handled upstream and propagated as "
                                + "state", ex);
                    }
                });*/
            }

            @Override
            public void onComplete() {
                mSubscriber.compareAndSet(this, null);
            }

            public void cancelSubscription() {
                Subscription s = get();
                if (s != null) {
                    s.cancel();
                }
            }
        }
    }
}
abstract class TaskExecutor {
    /**
     * Executes the given task in the disk IO thread pool.
     *
     * @param runnable The runnable to run in the disk IO thread pool.
     */
    public abstract void executeOnDiskIO(@NonNull Runnable runnable);

    /**
     * Posts the given task to the main thread.
     *
     * @param runnable The runnable to run on the main thread.
     */
    public abstract void postToMainThread(@NonNull Runnable runnable);

    /**
     * Executes the given task on the main thread.
     * <p>
     * If the current thread is a main thread, immediately runs the given runnable.
     *
     * @param runnable The runnable to run on the main thread.
     */
    public void executeOnMainThread(@NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            postToMainThread(runnable);
        }
    }

    /**
     * Returns true if the current thread is the main thread, false otherwise.
     *
     * @return true if we are on the main thread, false otherwise.
     */
    public abstract boolean isMainThread();
}
class ArchTaskExecutor extends TaskExecutor {
    private static volatile ArchTaskExecutor sInstance;

    @NonNull
    private TaskExecutor mDelegate;

    @NonNull
    private TaskExecutor mDefaultTaskExecutor;

    @NonNull
    private static final Executor sMainThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().postToMainThread(command);
        }
    };

    @NonNull
    private static final Executor sIOThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().executeOnDiskIO(command);
        }
    };

    private ArchTaskExecutor() {
        mDefaultTaskExecutor = new DefaultTaskExecutor();
        mDelegate = mDefaultTaskExecutor;
    }

    /**
     * Returns an instance of the task executor.
     *
     * @return The singleton ArchTaskExecutor.
     */
    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new ArchTaskExecutor();
            }
        }
        return sInstance;
    }

    /**
     * Sets a delegate to handle task execution requests.
     * <p>
     * If you have a common executor, you can set it as the delegate and App Toolkit components will
     * use your executors. You may also want to use this for your tests.
     * <p>
     * Calling this method with {@code null} sets it to the default TaskExecutor.
     *
     * @param taskExecutor The task executor to handle task requests.
     */
    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        mDelegate = taskExecutor == null ? mDefaultTaskExecutor : taskExecutor;
    }

    @Override
    public void executeOnDiskIO(Runnable runnable) {
        mDelegate.executeOnDiskIO(runnable);
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        mDelegate.postToMainThread(runnable);
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    @Override
    public boolean isMainThread() {
        return mDelegate.isMainThread();
    }
}
class DefaultTaskExecutor extends TaskExecutor {
    private final Object mLock = new Object();
    private ExecutorService mDiskIO = Executors.newFixedThreadPool(2);

    @Nullable
    private volatile Handler mMainHandler;

    @Override
    public void executeOnDiskIO(Runnable runnable) {
        mDiskIO.execute(runnable);
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        if (mMainHandler == null) {
            synchronized (mLock) {
                if (mMainHandler == null) {
                    mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        //noinspection ConstantConditions
        mMainHandler.post(runnable);
    }

    @Override
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
