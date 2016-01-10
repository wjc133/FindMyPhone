package com.elite.findmyphone.httpvisitor;

import android.os.*;
import android.os.Process;

import com.elite.findmyphone.httpvisitor.exception.HttpVisitorError;

import java.util.concurrent.BlockingQueue;

/**
 * Created by wjc133.
 * Date: 2016/1/10
 * Time: 23:37
 * Description:
 */
public class NetworkDispatcher extends Thread {
    private final BlockingQueue<Request<?>> mQueue;
    private final NetWorker mNetWorker;
    private final ResponseDelivery mDelivery;
    private volatile boolean mQuit = false;

    public NetworkDispatcher(BlockingQueue<Request<?>> queue, NetWorker worker,
                             ResponseDelivery delivery) {
        mQueue = queue;
        mNetWorker = worker;
        mDelivery = delivery;
    }

    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Request<?> request;
        while (true) {
            long startTimeMs = SystemClock.elapsedRealtime();
            request = null;
            try {
                request = mQueue.take();
            } catch (InterruptedException e) {
                if (mQuit) {
                    return;
                }
                continue;
            }

            try {
                if (request.isCanceled()) {
                    request.finish("network-discard-cancelled");
                    continue;
                }
                NetworkResponse networkResponse = mNetWorker.performRequest(request);
                if (networkResponse.notModified && request.hasHadResponseDeliverd()) {
                    request.finish("not-modified");
                    continue;
                }

                Response<?> response = request.parseNetworkResponse(networkResponse);
                request.markDeliverd();
                mDelivery.postResponse(request, response);
            } catch (HttpVisitorError error) {
                error.setNetworkTimeMs(SystemClock.elapsedRealtime() - startTimeMs);
                parseAndDeliverNetworkError(request, error);
            } catch (Exception e) {
                HttpVisitorError error = new HttpVisitorError(e);
                error.setNetworkTimeMs(SystemClock.elapsedRealtime() - startTimeMs);
                mDelivery.postError(request, error);
            }
        }
    }

    private void parseAndDeliverNetworkError(Request<?> request, HttpVisitorError error) {
        //这里需要做一步解析网络错误的步骤，如果是捕捉到Exception转换成HttpVisitorError就没有必要
        //再进行解析这个步骤了.
        error = request.parseNetworkError(error);
        mDelivery.postError(request, error);
    }
}
