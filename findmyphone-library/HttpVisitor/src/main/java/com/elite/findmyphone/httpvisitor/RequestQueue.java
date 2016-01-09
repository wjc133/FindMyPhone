package com.elite.findmyphone.httpvisitor;

import com.android.volley.NetworkDispatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by wjc133
 * Date: 2016/1/7
 * Time: 11:27
 */
public class RequestQueue {
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private PriorityBlockingQueue<Request<?>> mNetworkQueue = new PriorityBlockingQueue<>();
    private NetWorker mNetWorker;
    private NetworkDispatcher[] mDispathers;
    // TODO: 2016/1/7 最后要把请求换成自己的
    private final Set<Request<?>> mCurrentRequests = new HashSet<>();

    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;

    public RequestQueue(NetWorker worker, int threadPoolSize) {
        this.mNetWorker = worker;
        this.mDispathers = new NetworkDispatcher[threadPoolSize];
    }

    public RequestQueue(NetWorker worker) {
        this(worker, DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    public <T> void add(Request<T> request) {
//        request.setRequestQueue(this);
        synchronized (mCurrentRequests) {
            mCurrentRequests.add(request);
        }

        //给request编序号
        request.setSequence(getSquenceNumber());
    }

    public void start() {
        //有没停止的任务先全都停掉
        stop();
        for (int i = 0; i < mDispathers.length; i++) {
            NetworkDispatcher networkDispatcher = new NetworkDispatcher(mNetworkQueue, mNetWorker, delivery);
            mDispathers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    public void stop() {
        for (int i = 0; i < mDispathers.length; i++) {
            if (mDispathers[i] != null) {
                mDispathers[i].quit();
            }
        }
    }

    public void finish(Request request) {
        // TODO: 2016/1/9 something
    }

    /**
     * 为请求生成一个序列号
     *
     * @return 序列号
     */
    private int getSquenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }
}
