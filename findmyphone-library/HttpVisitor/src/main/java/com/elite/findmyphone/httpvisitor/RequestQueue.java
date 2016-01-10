package com.elite.findmyphone.httpvisitor;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by wjc133
 * Date: 2016/1/7
 * Time: 11:27
 * 请求队列的作用主要是一个用于盛放请求的容器，它包含多个NetworkDispatcher同时工作，负责取出请求，发送请求和
 * 返回响应。
 */
public class RequestQueue {
    /**
     * 当所有网络请求均已完成的时候使用的回调接口
     *
     * @param <T>
     */
    public interface RequestFinishedListener<T> {
        void onRequestFinished(Request<T> request);
    }

    private final List<RequestFinishedListener> mFinishedListeners = new ArrayList<>();

    /**
     * 序列号生成器，为每一个进入队列的请求安排一个序号，以便排队
     **/
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    /**
     * 参与网络请求的请求队列
     **/
    private PriorityBlockingQueue<Request<?>> mNetworkQueue = new PriorityBlockingQueue<>();
    /**
     * NetWorker，网络请求的执行者
     **/
    private NetWorker mNetWorker;
    /**
     * 网络请求分发员，将网络请求分发给具体的工人去做，这里每一个分发对象就是一条线程
     **/
    private NetworkDispatcher[] mDispatchers;

    /**
     * 请求分发员
     */
    private final ResponseDelivery mDelivery;
    /**
     * 当前已经加入到RequestQueue对象中的请求集合，可能还未分配到网络请求队列中
     **/
    private final Set<Request<?>> mCurrentRequests = new HashSet<>();

    /**
     * 默认的网络请求线程池大小
     **/
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;

    public RequestQueue(NetWorker worker, int threadPoolSize,ResponseDelivery delivery) {
        this.mNetWorker = worker;
        this.mDispatchers = new NetworkDispatcher[threadPoolSize];
        this.mDelivery = delivery;
    }

    public RequestQueue(NetWorker worker) {
        this(worker, DEFAULT_NETWORK_THREAD_POOL_SIZE, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    public <T> Request<T> add(Request<T> request) {
        request.setRequestQueue(this);
        synchronized (mCurrentRequests) {
            mCurrentRequests.add(request);
        }
        //给request编序号
        request.setSequence(getSquenceNumber());
        mNetworkQueue.add(request);
        return request;
    }

    public void start() {
        //有没停止的任务先全都停掉
        stop();
        for (int i = 0; i < mDispatchers.length; i++) {
            NetworkDispatcher networkDispatcher = new NetworkDispatcher(mNetworkQueue, mNetWorker, mDelivery);
            mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    public void stop() {
        for (NetworkDispatcher mDispatcher : mDispatchers) {
            if (mDispatcher != null) {
                mDispatcher.quit();
            }
        }
    }

    public <T> void finish(Request<T> request) {
        synchronized (mCurrentRequests) {
            mCurrentRequests.remove(request);
        }
        synchronized (mFinishedListeners) {
            for (RequestFinishedListener<T> listener : mFinishedListeners) {
                listener.onRequestFinished(request);
            }
        }
    }

    /**
     * 这个接口允许用户自己实现，以判断什么样的Request是需要拦截的
     **/
    public interface RequestFilter {
        boolean apply(Request<?> request);
    }

    /**
     * 通过Request拦截器取消请求
     **/
    public void cancelAll(RequestFilter filter) {
        synchronized (mCurrentRequests) {
            for (Request<?> request : mCurrentRequests) {
                if (filter.apply(request)) {
                    request.cancel();
                }
            }
        }
    }

    /**
     * 用Request tag来批量取消请求
     *
     * @param tag 在Request中设置的标签
     */
    public void cancelAll(final Object tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Cannot cancelAll with a null tag");
        }
        cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request.getTag() == tag;
            }
        });
    }

    /**
     * 为请求生成一个序列号
     *
     * @return 序列号
     */
    private int getSquenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    public  <T> void addRequestFinishedListener(RequestFinishedListener<T> listener) {
        synchronized (mFinishedListeners) {
            mFinishedListeners.add(listener);
        }
    }

    /**
     * Remove a RequestFinishedListener. Has no effect if listener was not previously added.
     */
    public  <T> void removeRequestFinishedListener(RequestFinishedListener<T> listener) {
        synchronized (mFinishedListeners) {
            mFinishedListeners.remove(listener);
        }
    }
}
